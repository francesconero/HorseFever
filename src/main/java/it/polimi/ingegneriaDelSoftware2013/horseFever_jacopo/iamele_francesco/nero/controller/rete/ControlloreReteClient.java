package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenteSingolo;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.ConnessioneServerFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.DisconnessioneAnomalaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Parte del controllore che funge da adapter sul client, per la comunicazione in rete.
 * <p>
 * Si aspetta di interfacciarsi con {@link ControlloreReteServer}.
 * <p>
 * Estende {@link ControlloreRete}
 * 
 * @author Francesco
 *
 */
public class ControlloreReteClient implements ControlloreUtenteSingolo{

	private final int portaServer;
	private final int portaHeartbeat;
	private final int tentativiConnessioneMax;
	private final int timeoutSocket;
	private final String proprioNome;

	private Socket serverSocket;	
	private Socket heartbeatSocket;
	private InetAddress indirizzoServer;

	/**
	 * Inizializza un controllore per la rete a lato client, con un proprio nome che sarà visualizzato dagli altri giocatori, e l'indirizzo ip
	 * del server HorseFever al quale ci si vuole collegare
	 * 
	 * @param proprioNome	il nostro nome preferito
	 * @param nomeHost	il nome del server a cui vogliamo collegarci (può anche essere una semplice rappresentazione stringa di un indirizzo ip)
	 * @throws UnknownHostException	se non si riesce a risolvere il nome host in un indirizzo ip
	 */
	public ControlloreReteClient(String proprioNome, String nomeHost) throws UnknownHostException{
		indirizzoServer = InetAddress.getByName(nomeHost);
		portaServer = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("portaServer"));
		portaHeartbeat = Integer.parseInt(Configurazioni.getInstance().getNetProperties().getProperty("portaHeartbeat"));
		tentativiConnessioneMax = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("tentativiConnessione"));
		timeoutSocket = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("timeout"));
		this.proprioNome = proprioNome;
	}

	/**
	 * Connette questo client a un server HorseFever, inviandogli anche un nome giocatore.
	 */
	public void collegaGioco(){		
		stabilisciConnessione();
		if(!ControlloreRete.inviaOggettoConRisposta(proprioNome, serverSocket)){
			System.out.println("Connessione fallita!");
			throw new ConnessioneServerFallitaException("Fallito invio nome");
		}
		System.out.println("Connesso!");
	}

	public boolean scommetti(Scommessa scommessaDaFare) {
		return ControlloreRete.inviaOggettoConRisposta(scommessaDaFare, serverSocket);
	}

	public boolean posizionaCarta(PosizionaCarta posizionaCarta) {
		return ControlloreRete.inviaOggettoConRisposta(posizionaCarta, serverSocket);
	}

	public StatoDelGiocoView riceviStatoDelGioco() {
		Object possibileStatoDelGioco = null;
		try{
			possibileStatoDelGioco = ControlloreRete.riceviOggetto(serverSocket);
		} catch (RicezioneFallitaException e){
			if(isServerUp()){
			riceviStatoDelGioco();
			} else {
				throw new DisconnessioneAnomalaException(serverSocket);
			}
		}
		
		if(possibileStatoDelGioco != null){
			if(possibileStatoDelGioco instanceof StatoDelGiocoView){
				ControlloreRete.rispondiPositivamente(serverSocket);
				return (StatoDelGiocoView) possibileStatoDelGioco;
			} else {
				ControlloreRete.rispondiNegativamente(serverSocket);
				throw new RicezioneFallitaException("Fallita ricezione del nuovo stato del gioco");
			}
		} else {
			ControlloreRete.rispondiNegativamente(serverSocket);
			throw new RicezioneFallitaException("Fallita ricezione del nuovo stato del gioco");
		}
		
	}

	private void stabilisciConnessione() throws ConnessioneServerFallitaException {
		int tentativi = 0;
	
		do{
			tentativi++;
			System.out.println("Tentativo connessione "+tentativi+"...");
			try {
				serverSocket = new Socket(indirizzoServer, portaServer);
				heartbeatSocket = new Socket(indirizzoServer, portaHeartbeat);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} while(tentativi<tentativiConnessioneMax && serverSocket == null);
	
		if(serverSocket==null){
			throw new ConnessioneServerFallitaException("Fallita la connessione al server");
		}else{
			try {
				serverSocket.setSoTimeout(timeoutSocket);
			} catch (SocketException e) {
				throw new ConnessioneServerFallitaException("Impossibile settare il timeout sul socket", e);				
			}			
		}
	}

	private boolean isServerUp() {
		return ControlloreRete.inviaOggettoConRisposta("Sei ancora vivo?", heartbeatSocket);
	}

	/*
	 * Piccolo main per testare le funzionalità più macro. Non ho trovato un buon modo per gestire questo test con junit.
	 * TODO
	 */
	public static void main(String[] args){
		Thread client1Thread = new Thread(){
			public void run(){
				ControlloreReteClient client = null;
				try {
					client = new ControlloreReteClient("Client1", "127.0.0.1");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				try {
					client.collegaGioco();
					client.riceviStatoDelGioco();
					try {
						Thread.sleep(50000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ConnessioneServerFallitaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		client1Thread.start();
	}
}

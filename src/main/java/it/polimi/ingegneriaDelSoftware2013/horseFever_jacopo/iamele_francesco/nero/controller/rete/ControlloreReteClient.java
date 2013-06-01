package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.ConnessioneServerFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
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
public class ControlloreReteClient extends ControlloreRete{
	
	private final int portaServer;
	private final int tentativiConnessioneMax;
	private final int tentativiInvioNomeMax;
	private final int timeoutSocket;
	private final String proprioNome;

	private Socket serverSocket;	
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
		tentativiConnessioneMax = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("tentativiConnessione"));
		tentativiInvioNomeMax = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("tentativiInvioNome"));
		timeoutSocket = Integer.parseInt(Configurazioni.getInstance().getClientProperties().getProperty("timeout"));
		this.proprioNome = proprioNome;
	}

	/**
	 * Connette questo client a un server HorseFever, inviandogli anche un nome giocatore.
	 * 
	 * @throws ConnessioneServerFallitaException	se per qualsiasi motivo una di queste cose dovesse fallire
	 */
	public void connetti() throws ConnessioneServerFallitaException{		
		stabilisciConnessione();
		try {
			inviaOggettoConRisposta(proprioNome, serverSocket, tentativiInvioNomeMax);
		} catch (InvioFallitoException e) {
			throw new ConnessioneServerFallitaException("Fallito invio nome", e);
		}
		System.out.println("Connesso!");
	}

	private void stabilisciConnessione() throws ConnessioneServerFallitaException {
		int tentativi = 0;

		do{
			tentativi++;
			System.out.println("Tentativo connessione "+tentativi+"...");
			try {
				serverSocket = new Socket(indirizzoServer, portaServer);
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
					client.connetti();
				} catch (ConnessioneServerFallitaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		client1Thread.start();
	}
}

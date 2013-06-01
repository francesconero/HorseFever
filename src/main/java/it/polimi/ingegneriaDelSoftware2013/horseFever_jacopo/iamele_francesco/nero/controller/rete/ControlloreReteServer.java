package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaClientsFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Parte del controllore che funge da adapter sul server, per la comunicazione in rete.
 * <p>
 * Si aspetta di interfacciarsi con {@link ControlloreReteClient}.
 * <p>
 * Estende {@link ControlloreRete}
 * 
 * @author Francesco
 *
 */
public class ControlloreReteServer extends ControlloreRete {

	private final Map<Giocatore, Socket> clients = new HashMap<Giocatore, Socket>();
	private final Map<Giocatore, String> nomiClients = new HashMap<Giocatore, String>();

	private final int numPorta;
	private final int tentativiRichiestaNomeMax;
	private final int serverTimeout;
	private final int clientTimeout;

	/**
	 * Inizializza un controllore network lato server, caricando varie impostazioni da file. NON mette in ascolto il server.
	 * Per quello vedi {@link #accettaClients(List) accettaClients}.
	 */
	public ControlloreReteServer() {
		numPorta = Integer.parseInt(Configurazioni.getInstance().getServerProperties().getProperty("porta"));
		tentativiRichiestaNomeMax = Integer.parseInt(Configurazioni.getInstance().getServerProperties().getProperty("tentativiRichiestaNome"));
		serverTimeout = Integer.parseInt(Configurazioni.getInstance().getServerProperties().getProperty("ascoltoTimeout"));
		clientTimeout = Integer.parseInt(Configurazioni.getInstance().getServerProperties().getProperty("clientTimeout"));		 
	}

	/**
	 * Accetta tanti client da network, tanti quanti giocatori sono passati nella lista come parametro.
	 * E' il primo metodo da chiamare se si vuole interagire con gli utenti.
	 * @param giocatori	List<{@link Giocatore}> la lista di giocatori ai quali si vuole associare un utente network
	 * @throws AttesaClientsFallitaException	se per qualche motivo non si riescono ad accettare tutti gli utenti richiesti
	 */
	public void accettaClients(List<Giocatore> giocatori) throws AttesaClientsFallitaException{
		System.out.println("Attendo "+giocatori.size()+" giocatori...");
		List<Socket> sockets = apriSockets(giocatori.size());		
		try{
			assegnaGiocatori(sockets, giocatori);
		} catch (AttesaClientsFallitaException e){
			try {
				chiudiSockets(sockets);
			} catch (IOException e1) {
				throw new AttesaClientsFallitaException("Chiusura socket fallita", e1);
			}
			throw new AttesaClientsFallitaException("Impossibile assegnare i giocatori", e);
		}
		System.out.println("Si sono collegati i seguenti giocatori: ");
		for(String nome: nomiClients.values()){
			System.out.println(nome);
		}
	}

	private void assegnaGiocatori(List<Socket> sockets, List<Giocatore> giocatori) throws AttesaClientsFallitaException {
		for(Socket s : sockets){
			String nome = null;

			try {
				nome = ricavaNome(s);
			} catch (RicezioneFallitaException e) {
				throw new AttesaClientsFallitaException("Fallita la ricezione del nome di un client", e);
			}

			if(giocatori.size()>0){
				System.out.println("Aggiungo giocatore "+nome);
				Giocatore nuovoGiocatore = giocatori.remove(0);
				clients.put(nuovoGiocatore, s);
				nomiClients.put(nuovoGiocatore, nome);
			} else {
				throw new AttesaClientsFallitaException("Si sono collegati piu' client del previsto");
			}
		}
	}

	private String ricavaNome(Socket s) throws RicezioneFallitaException {
		String nome = null;
		Object nomePossibile = null;
		nomePossibile = riceviOggetto(s, tentativiRichiestaNomeMax);
		if(nomePossibile instanceof String){
			nome = (String) nomePossibile;
			try {
				rispondiPositivamente(s);
			} catch (InvioFallitoException e) {
				throw new RicezioneFallitaException("Fallito l'invio della conferma ricezione", e);
			}
		}else{
			try {
				rispondiNegativamente(s);
			} catch (InvioFallitoException e) {
				throw new RicezioneFallitaException("Fallito l'invio della notifica della mancata ricezione", e);
			}
		}
		return nome;
	}

	private List<Socket> apriSockets(int numGiocatori) throws AttesaClientsFallitaException {
		ServerSocket accettore = null;

		try {			
			accettore = new ServerSocket(numPorta);
			accettore.setSoTimeout(serverTimeout);
		} catch (IOException e) {
			throw new AttesaClientsFallitaException("Fallita la creazione del socket server", e);			
		}

		List<Socket> listaTemporanea = new LinkedList<Socket>();

		for(int i = 0; i<numGiocatori; i++){
			try {
				Socket accettato = accettore.accept();
				try {
					accettato.setSoTimeout(clientTimeout);
				} catch (SocketException e) {
					e.printStackTrace();
				}
				listaTemporanea.add(accettato);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

		try {
			accettore.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(listaTemporanea.size() < numGiocatori){
			throw new AttesaClientsFallitaException("Si sono collegati meno client del previsto");
		}else{
			if(listaTemporanea.size() > numGiocatori){
				throw new AttesaClientsFallitaException("Si sono collegati piu' client del previsto");
			}
			return listaTemporanea;
		}
	}

	/*
	 * Piccolo main per testare le funzionalità più macro. Non ho trovato un buon modo per gestire questo test con junit.
	 * TODO
	 */
	public static void main(String[] args){
		ControlloreReteServer server = new ControlloreReteServer();
		List<Giocatore> giocatori = new LinkedList<Giocatore>(); 
		giocatori.add(new Giocatore());
		try {
			server.accettaClients(giocatori);
		} catch (AttesaClientsFallitaException e) {
			e.printStackTrace();
		}
	}

}

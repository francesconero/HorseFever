package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenti;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ServerConsole;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AggiornamentoUtentiFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.DisconnessioneAnomalaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

/**
 * Implementazione network del {@link ControlloreUtenti} Parte del controllore
 * che funge da adapter sul server, per la comunicazione in rete.
 * <p>
 * Si aspetta di interfacciarsi con {@link ControlloreReteClient}.
 * <p>
 * Estende {@link ControlloreRete}
 * 
 * @author Francesco
 * 
 */
public class ControlloreReteServer implements ControlloreUtenti {

	private final Map<Giocatore, Socket> clients = new LinkedHashMap<Giocatore, Socket>();
	private final Map<Giocatore, Socket> clientsHeartbeat = new LinkedHashMap<Giocatore, Socket>();
	private final Map<Giocatore, String> nomiClients = new LinkedHashMap<Giocatore, String>();

	private final int portaServer;
	private final int portaHeartbeat;
	private final int serverTimeout;
	private final int clientTimeout;
	private final int heartbeatTimeout;

	private final HeartbeatThread heartbeatThread = new HeartbeatThread();

	/**
	 * Inizializza un controllore network lato server, caricando varie
	 * impostazioni da file. NON mette in ascolto il server. Per quello vedi
	 * {@link #accettaUtenti(List) accettaClients}.
	 */
	public ControlloreReteServer() {
		portaServer = Integer.parseInt(Configurazioni.getInstance()
				.getServerProperties().getProperty("porta"));
		portaHeartbeat = Integer.parseInt(Configurazioni.getInstance()
				.getNetProperties().getProperty("portaHeartbeat"));
		serverTimeout = Integer.parseInt(Configurazioni.getInstance()
				.getServerProperties().getProperty("attesaClientTimeout"));
		clientTimeout = Integer.parseInt(Configurazioni.getInstance()
				.getServerProperties().getProperty("clientTimeout"));
		heartbeatTimeout = Integer.parseInt(Configurazioni.getInstance()
				.getNetProperties().getProperty("heartbeatTimeout"));
	}

	/**
	 * Accetta tanti client da network, tanti quanti giocatori sono passati
	 * nella lista come parametro. E' il primo metodo da chiamare se si vuole
	 * interagire con gli utenti.
	 * 
	 * @param giocatori
	 *            List<{@link Giocatore}> la lista di giocatori ai quali si
	 *            vuole associare un utente network
	 */
	public void accettaUtenti(List<Giocatore> giocatori) {
		ServerConsole.getInstance().write("Attendo " + giocatori.size() + " giocatori...");
		for (Giocatore g : giocatori) {
			SocketHolder sockets = apriSocket();
			assegnaGiocatore(sockets, g);
		}
		
		heartbeatThread.start();

		ServerConsole.getInstance().write("Si sono collegati i seguenti giocatori: ");
		for (String nome : nomiClients.values()) {
			ServerConsole.getInstance().write(nome);
		}
	}

	private void assegnaGiocatore(SocketHolder sockets, Giocatore g) {
		Socket s = sockets.mainSocket;
		Socket heartbeatSocket = sockets.heartbeatSocket;

		String nome = ricavaNome(s);

		ServerConsole.getInstance().write("Aggiungo giocatore " + nome);
		clients.put(g, s);
		clientsHeartbeat.put(g, heartbeatSocket);
		nomiClients.put(g, nome);
	}

	private SocketHolder apriSocket() {
		ServerSocket accettore = null;
		ServerSocket heartbeatAccettore = null;

		try {
			accettore = new ServerSocket(portaServer);
			heartbeatAccettore = new ServerSocket(portaHeartbeat);
			accettore.setSoTimeout(serverTimeout);
			heartbeatAccettore.setSoTimeout(serverTimeout);
		} catch (IOException e) {
			throw new AttesaUtentiFallitaException(
					"Fallita la creazione del socket server", e);
		}

		SocketHolder holder = new SocketHolder();

		try {
			Socket accettato = accettore.accept();
			Socket heartbeatAccettato = heartbeatAccettore.accept();
			try {
				accettato.setSoTimeout(clientTimeout);
				heartbeatAccettato.setSoTimeout(heartbeatTimeout);
			} catch (SocketException e) {
				throw new AttesaUtentiFallitaException(
						"Impossibile settare il timeout sul socket", e);
			}
			holder.mainSocket = accettato;
			holder.heartbeatSocket = heartbeatAccettato;
		} catch (IOException e) {
			throw new AttesaUtentiFallitaException(
					"Errore durante l'attesa dei client", e);
		} finally {
			try {
				accettore.close();
				heartbeatAccettore.close();
			} catch (IOException e) {
				throw new AttesaUtentiFallitaException(
						"Errore durante la chiusura dei socket di accettazione",
						e);
			}
		}

		if (holder.mainSocket == null
				|| holder.heartbeatSocket == null) {
			throw new AttesaUtentiFallitaException(
					"Si sono collegati meno client del previsto");
		} else {
			return holder;
		}
	}

	private String ricavaNome(Socket s) throws RicezioneFallitaException {
		String nome = null;
		Object nomePossibile = null;
		nomePossibile = ControlloreRete.riceviOggetto(s);
		if (nomePossibile instanceof String) {
			nome = (String) nomePossibile;
			ControlloreRete.rispondiPositivamente(s);
		} else {
			ControlloreRete.rispondiNegativamente(s);
		}
		return nome;
	}

	public void cleanUp() {
		heartbeatThread.fermaHeartbeat();
		try {
			heartbeatThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			ControlloreRete.chiudiSockets(clients.values());
			ControlloreRete.chiudiSockets(clientsHeartbeat.values());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerConsole.getInstance().write("Server closed");
	}

	public void aggiornaUtenti(StatoDelGioco statoDelGioco) {

		if (!heartbeatThread.isAlive()) {
			DisconnessioneAnomalaException disconnesso = heartbeatThread.eccezione;

			if (disconnesso != null) {
				throw new AggiornamentoUtentiFallitoException(disconnesso);
			}
		}

		for (Giocatore g : clients.keySet()) {
			StatoDelGiocoView daInviare = new StatoDelGiocoView(statoDelGioco,
					g);
			Socket temp = clients.get(g);
			ControlloreRete.inviaOggettoConRisposta(daInviare, temp);
		}
	}

	public Scommessa riceviScommessa(Giocatore giocatore) {
		Socket temp = clients.get(giocatore);
		if (temp == null) {
			throw new IllegalArgumentException(
					"Giocatore non associato ad alcun socket");
		} else {
			Object scommessaPossibile = ControlloreRete.riceviOggetto(temp);
			if (scommessaPossibile instanceof Scommessa) {
				return (Scommessa) scommessaPossibile;
			} else {
				throw new RicezioneFallitaException();
			}
		}
	}

	public PosizionaCarta riceviPosizionaCarta(Giocatore giocatore) {
		Socket temp = clients.get(giocatore);
		if (temp == null) {
			throw new IllegalArgumentException(
					"Giocatore non associato ad alcun socket");
		} else {
			Object posizionaCartaPossibile = ControlloreRete
					.riceviOggetto(temp);
			if (posizionaCartaPossibile instanceof PosizionaCarta) {
				return (PosizionaCarta) posizionaCartaPossibile;
			} else {
				throw new RicezioneFallitaException();
			}
		}
	}

	public void conferma(Giocatore giocatore) {
		Socket temp = clients.get(giocatore);
		if (temp == null) {
			throw new IllegalArgumentException(
					"Giocatore non associato ad alcun socket");
		} else {
			ControlloreRete.rispondiPositivamente(temp);
		}
	}

	public void nega(Giocatore giocatore) {
		Socket temp = clients.get(giocatore);
		if (temp == null) {
			throw new IllegalArgumentException(
					"Giocatore non associato ad alcun socket");
		} else {
			ControlloreRete.rispondiNegativamente(temp);
		}
	}

	private class SocketHolder {
		private Socket mainSocket;
		private Socket heartbeatSocket;
	}

	private class HeartbeatThread extends Thread {
		private AtomicBoolean esegui = new AtomicBoolean(true);
		private DisconnessioneAnomalaException eccezione = null;

		public HeartbeatThread() {
			super("Heartbeat");
		}

		public void fermaHeartbeat() {
			esegui.set(false);
		}

		@Override
		public void run() {
			while (esegui.get()) {
				for (Socket heartbeatSocket : clientsHeartbeat.values()) {
					Object temp = null;
					try {
						temp = ControlloreRete.riceviOggetto(heartbeatSocket);
					} catch (RicezioneFallitaException e) {
						if (e.getCause() instanceof IOException) {
							eccezione = new DisconnessioneAnomalaException(
									"Possibile disconnessione del client ", e,
									heartbeatSocket);
							fermaHeartbeat();
						}
					}
					if (temp != null) {
						ControlloreRete.rispondiPositivamente(heartbeatSocket);
					}
				}
			}
		}
	}

	/*
	 * Piccolo main per testare le funzionalità più macro. Non ho trovato un
	 * buon modo per gestire questo test con junit. TODO
	 */
	public static void main(String[] args) {
		ControlloreReteServer server = new ControlloreReteServer();
		List<Giocatore> giocatori = new LinkedList<Giocatore>();
		server.accettaUtenti(giocatori);
		server.cleanUp();
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenti;
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
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.GestoreEccezioni;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

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
	private final Map<Giocatore, Long> IDClients = new LinkedHashMap<Giocatore, Long>();

	private final int portaServer;
	private final int portaHeartbeat;
	private final int serverTimeout;
	private final int heartbeatTimeout;

	private long currentID = 0;

	private final HeartbeatThread heartbeatThread = new HeartbeatThread();

	/**
	 * Inizializza un controllore network lato server, caricando varie
	 * impostazioni da file. NON mette in ascolto il server. Per quello vedi
	 * {@link #accettaUtenti(List) accettaClients}.
	 */
	public ControlloreReteServer() {
		
		heartbeatThread.setUncaughtExceptionHandler(GestoreEccezioni.getInstance());
		
		portaServer = Integer.parseInt(Configurazioni.getInstance()
				.getServerProperties().getProperty("porta"));
		portaHeartbeat = Integer.parseInt(Configurazioni.getInstance()
				.getNetProperties().getProperty("portaHeartbeat"));
		serverTimeout = Integer.parseInt(Configurazioni.getInstance()
				.getServerProperties().getProperty("attesaClientTimeout"));
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
		System.out.println("Attendo " + giocatori.size() + " giocatori...");
		for (Giocatore g : giocatori) {
			SocketHolder sockets = apriSocket();
			assegnaGiocatore(sockets, g);
		}

		heartbeatThread.start();

		System.out.println("Si sono collegati i seguenti giocatori: ");
		for (String nome : nomiClients.values()) {
			System.out.println(nome);
		}
	}

	private void assegnaGiocatore(SocketHolder sockets, Giocatore g) {
		Socket s = sockets.mainSocket;
		Socket heartbeatSocket = sockets.heartbeatSocket;

		String nome = ricavaNome(s);
		long ID = getNewID();

		System.out.println("Aggiungo giocatore " + nome);
		clients.put(g, s);
		clientsHeartbeat.put(g, heartbeatSocket);
		nomiClients.put(g, nome);
		IDClients.put(g, ID);
		ControlloreRete.inviaOggettoConRisposta(ID, s); // assegna ID al client
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

		if (holder.mainSocket == null || holder.heartbeatSocket == null) {
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

	public void aggiornaUtenti(StatoDelGioco statoDelGioco) {

		if (!heartbeatThread.isAlive()) {
			DisconnessioneAnomalaException disconnesso = heartbeatThread.eccezione;

			if (disconnesso != null) {
				throw new AggiornamentoUtentiFallitoException(disconnesso);
			}
		}

		for (Giocatore g : clients.keySet()) {
			StatoDelGiocoView daInviare = new StatoDelGiocoView(statoDelGioco,
					g, nomiClients, IDClients);
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

	private long getNewID() {
		return currentID++;
	}

	private class SocketHolder {
		private Socket mainSocket;
		private Socket heartbeatSocket;
	}

	private class HeartbeatThread extends Thread {
		private AtomicBoolean esegui = new AtomicBoolean(true);
		private DisconnessioneAnomalaException eccezione = null;
		private ExecutorService clientsExecutor = Executors
				.newCachedThreadPool();
		private LinkedList<Callable<String>> heartBeatThreads = new LinkedList<Callable<String>>();

		private class SingleHeartBeat implements Callable<String> {
			private Socket socket;

			public SingleHeartBeat(Socket socket) {
				this.socket = socket;
			}

			public String call() throws Exception {
				try {
					String out = (String) ControlloreRete.riceviOggetto(socket);
					ControlloreRete.rispondiPositivamente(socket);
					return out;
				} catch (RicezioneFallitaException e) {
					throw new DisconnessioneAnomalaException(e,socket);
				}
			}

		}

		public void fermaHeartbeat() {
			esegui.set(false);
		}

		@Override
		public void run() {

			for (Socket heartbeatSocket : clientsHeartbeat.values()) {				
				heartBeatThreads.add(new SingleHeartBeat(heartbeatSocket));
			}

			while (esegui.get()) {
				List<Future<String>> results;
				try {
					results = clientsExecutor.invokeAll(heartBeatThreads);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}

				for (Future<String> result : results) {
					try {
						result.get();
					} catch (InterruptedException e) {
						clientsExecutor.shutdownNow();
						throw new RuntimeException(e);
					} catch (ExecutionException e) {
						clientsExecutor.shutdownNow();
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

}

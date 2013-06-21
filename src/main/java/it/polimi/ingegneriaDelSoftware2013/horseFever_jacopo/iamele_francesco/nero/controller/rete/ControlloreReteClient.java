package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.Utente;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.ConnessioneServerFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.DisconnessioneAnomalaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Parte del controllore che funge da adapter sul client, per la comunicazione
 * in rete.
 * <p>
 * Si aspetta di interfacciarsi con {@link ControlloreReteServer}.
 * <p>
 * Estende {@link ControlloreRete}
 * 
 * @author Francesco
 * 
 */
public class ControlloreReteClient implements Utente {

	private final int portaServer;
	private final int portaHeartbeat;
	private final int tentativiConnessioneMax;
	private final int heartbeatTimeout;
	private final int timeoutRispostaHeartbeat;
	private final String proprioNome;

	public String getProprioNome() {
		return proprioNome;
	}

	private Long ID;
	private boolean IDSet = false;

	private Socket serverSocket;
	private Socket heartbeatSocket;
	private InetAddress indirizzoServer;
	private HeartbeatThread heartbeatThread = new HeartbeatThread();

	/**
	 * Inizializza un controllore per la rete a lato client, con un proprio nome
	 * che sara' visualizzato dagli altri giocatori, e l'indirizzo ip del server
	 * HorseFever al quale ci si vuole collegare
	 * 
	 * @param proprioNome
	 *            il nostro nome preferito
	 * @param nomeHost
	 *            il nome del server a cui vogliamo collegarci (può anche essere
	 *            una semplice rappresentazione stringa di un indirizzo ip)
	 * @throws UnknownHostException
	 *             se non si riesce a risolvere il nome host in un indirizzo ip
	 */
	public ControlloreReteClient(String proprioNome, String nomeHost)
			throws UnknownHostException {

		indirizzoServer = InetAddress.getByName(nomeHost);
		portaServer = Integer.parseInt(Configurazioni.getInstance()
				.getClientProperties().getProperty("portaServer"));
		portaHeartbeat = Integer.parseInt(Configurazioni.getInstance()
				.getNetProperties().getProperty("portaHeartbeat"));
		tentativiConnessioneMax = Integer.parseInt(Configurazioni.getInstance()
				.getClientProperties().getProperty("tentativiConnessione"));
		heartbeatTimeout = Integer.parseInt(Configurazioni.getInstance()
				.getNetProperties().getProperty("heartbeatTimeout")) / 2; //assicura che inviamo ad una frequenza maggiore di quanto si aspetti il server
		timeoutRispostaHeartbeat = Integer.parseInt(Configurazioni.getInstance()
				.getClientProperties().getProperty("timeoutRispostaHeartbeat"));
		this.proprioNome = proprioNome;
	}

	/**
	 * Connette questo client a un server HorseFever, inviandogli anche un nome
	 * giocatore.
	 */
	public void collegaGioco() {
		stabilisciConnessione();
		if (!ControlloreRete.inviaOggettoConRisposta(proprioNome, serverSocket)) {
			System.out.println("Connessione fallita!");
			throw new ConnessioneServerFallitaException("Fallito invio nome");
		} else {
			riceviID();
		}
		System.out.println("Connesso!");
	}

	public boolean scommetti(Scommessa scommessaDaFare) {
		return ControlloreRete.inviaOggettoConRisposta(scommessaDaFare,
				serverSocket);
	}

	public boolean posizionaCarta(PosizionaCarta posizionaCarta) {
		return ControlloreRete.inviaOggettoConRisposta(posizionaCarta,
				serverSocket);
	}

	public StatoDelGiocoView riceviStatoDelGioco() {

		Object possibileStatoDelGioco = null;
		possibileStatoDelGioco = ControlloreRete.riceviOggetto(serverSocket);

		if (possibileStatoDelGioco != null) {
			if (possibileStatoDelGioco instanceof StatoDelGiocoView) {
				ControlloreRete.rispondiPositivamente(serverSocket);
				return (StatoDelGiocoView) possibileStatoDelGioco;
			} else {
				ControlloreRete.rispondiNegativamente(serverSocket);
				throw new RicezioneFallitaException(
						"Fallita ricezione del nuovo stato del gioco");
			}
		} else {
			ControlloreRete.rispondiNegativamente(serverSocket);
			throw new RicezioneFallitaException(
					"Fallita ricezione del nuovo stato del gioco");
		}

	}

	public void riceviID() {
		Long ID = (Long) ControlloreRete.riceviOggetto(serverSocket);
		if (ID == null) {
			ControlloreRete.rispondiNegativamente(serverSocket);
		} else {
			setID(ID);
			ControlloreRete.rispondiPositivamente(serverSocket);
		}
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		if (!IDSet) {
			ID = iD;
			IDSet = true;
		} else {
			throw new IllegalStateException("L'ID è gia' stato settato");
		}
	}

	@Override
	public String toString() {
		return proprioNome;
	}

	private void stabilisciConnessione()
			throws ConnessioneServerFallitaException {
		int tentativi = 0;

		do {
			tentativi++;
			System.out.println(proprioNome + ": tentativo connessione "
					+ tentativi);
			try {
				serverSocket = new Socket(indirizzoServer, portaServer);
				heartbeatSocket = new Socket(indirizzoServer, portaHeartbeat);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (tentativi < tentativiConnessioneMax && serverSocket == null);

		if (serverSocket == null || heartbeatSocket == null) {
			throw new ConnessioneServerFallitaException(
					"Fallita la connessione al server");
		} else {
			try {
				heartbeatSocket.setSoTimeout(timeoutRispostaHeartbeat);
				heartbeatThread.start();
			} catch (SocketException e) {
				throw new ConnessioneServerFallitaException(
						"Impossibile settare il timeout sul socket", e);
			}
		}
	}

	private class HeartbeatThread extends Thread {
		private AtomicBoolean esegui = new AtomicBoolean(true);
		private boolean forzato = false;

		public void ferma() {
			esegui.set(false);
			try {
				heartbeatThread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		public void run() {
			while (esegui.get()) {
				try{
					Map.Entry<Long, String> daInviare = new AbstractMap.SimpleEntry<Long, String>(ID, "Ci sono ancora!");
					if (!ControlloreRete.inviaOggettoConRisposta(daInviare,
							heartbeatSocket)) {
						throw new DisconnessioneAnomalaException(serverSocket);
					} else {
						try {
							Thread.sleep(heartbeatTimeout);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
				} catch (RicezioneFallitaException e){
					throw new DisconnessioneAnomalaException(e, serverSocket);
				}
			}
			if(forzato){
				System.out.println("Chiusura heartbeat forzata...");
				Map.Entry<Long, String> daInviare = new AbstractMap.SimpleEntry<Long, String>(ID, "ADIOS!");
				ControlloreRete.inviaOggettoConRisposta(daInviare, heartbeatSocket);
			} else {
				System.out.println("Chiusura heartbeat...");
				Map.Entry<Long, String> daInviare = new AbstractMap.SimpleEntry<Long, String>(ID, "BYE!");
				ControlloreRete.inviaOggettoConRisposta(daInviare, heartbeatSocket);
			}
			try {
				heartbeatSocket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public void setForzoso(boolean forza) {
			this.forzato  = forza;
		}
	}

	public boolean risolviConflitto(List<Colore> conflittoRisolto) {
		return ControlloreRete.inviaOggettoConRisposta(conflittoRisolto,
				serverSocket);
	}

	public void scollegaGioco(boolean forza) {
		if(heartbeatThread.esegui.compareAndSet(true, false)){
			if(serverSocket!=null){
				try {
					serverSocket.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			heartbeatThread.setForzoso(forza);
			if(!Thread.currentThread().equals(heartbeatThread)){
				try {
					System.out.println("Attendo che termini heartbeat");
					heartbeatThread.join();
					System.out.println("Heartbeat terminato");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			} else {
				System.out.println("Heartbeat thread ha chiuso il client");
			}
		}
	}

	public String riceviAvvertimento() {
		Object possibileAvvertimento = null;
		possibileAvvertimento = ControlloreRete.riceviOggetto(serverSocket);

		if (possibileAvvertimento != null) {
			if (possibileAvvertimento instanceof String) {
				ControlloreRete.rispondiPositivamente(serverSocket);
				return (String) possibileAvvertimento;
			} else {
				ControlloreRete.rispondiNegativamente(serverSocket);
				throw new RicezioneFallitaException(
						"Fallita ricezione dell'avvertimento");
			}
		} else {
			ControlloreRete.rispondiNegativamente(serverSocket);
			throw new RicezioneFallitaException(
					"Fallita ricezione dell'avvertimento");
		}
	}

}

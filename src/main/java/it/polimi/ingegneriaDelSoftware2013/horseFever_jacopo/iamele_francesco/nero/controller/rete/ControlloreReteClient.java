package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenteSingolo;
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
import java.util.List;
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
public class ControlloreReteClient implements ControlloreUtenteSingolo {

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
	 * che sarà visualizzato dagli altri giocatori, e l'indirizzo ip del server
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
			throw new IllegalStateException("L'ID è già stato settato");
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

		public void ferma() {
			esegui.set(false);
		}
		
		public void run() {
			while (esegui.get()) {
				try{
					if (!ControlloreRete.inviaOggettoConRisposta("Ci sono ancora!",
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
		}
	}

	public void fine() {
		heartbeatThread.ferma();
	}

	public boolean risolviConflitto(List<Colore> conflittoRisolto) {
		return ControlloreRete.inviaOggettoConRisposta(conflittoRisolto,
				serverSocket);
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Metodici statici che implementano le funzionalita' più a basso livello richieste per la comunicazione in rete.
 * Usata da {@link ControlloreReteClient} e {@link ControlloreReteServer}
 * @author Francesco
 *
 */
final class ControlloreRete {

	private enum Risposta {
		POSITIVA,
		NEGATIVA
	}

	private static void inviaOggetto(Object obj, Socket socket) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(obj);
		out.flush();
	}

	static Object riceviOggetto(Socket socket){
		do{
			try {
				return new ObjectInputStream(socket.getInputStream()).readObject();
			} catch (IOException e) {
				throw new RicezioneFallitaException(e);
			} catch (ClassNotFoundException e) {
				throw new RicezioneFallitaException(e);
			}
		} while (true);
	}

	/**
	 * Invia un oggetto tramite un socket, assicurandosi che l'oggetto inviato fosse del tipo richiesto
	 * @param obj l'oggetto da inviare
	 * @param socket il socket tramite cui inviarlo
	 * @return true se l'oggetto era del tipo richiesto, false se l'altro capo ha ritenuto l'oggetto del tipo non appropriato
	 * @throws InvioFallitoException se ci sono stati problemi gravi nell'invio dell'oggetto (ad esempio socket chiusi etc)
	 */
	static boolean inviaOggettoConRisposta(Object obj, Socket socket) {
		try {
			inviaOggetto(obj, socket);
		} catch (IOException e) {				
			throw new InvioFallitoException(e);
		}

		Object risultato = null;

		risultato = riceviOggetto(socket);

		if(risultato instanceof Risposta){
			Risposta risposta = (Risposta) risultato;
			if(risposta==Risposta.POSITIVA){
				return true;
			} else {
				return false;
			}
		}else{
			throw new InvioFallitoException("Ricevuta classe diversa da Risposta: "+risultato.getClass().getName());
		}
		
	}

	static void rispondiPositivamente(Socket s) {
		try {
			inviaOggetto(Risposta.POSITIVA, s);
		} catch (IOException e) {
			throw new InvioFallitoException("Errore nell'invio della conferma",e);
		}
	}

	static void rispondiNegativamente(Socket s) {
		try {
			inviaOggetto(Risposta.NEGATIVA, s);
		} catch (IOException e) {
			throw new InvioFallitoException("Errore nell'invio della risposta negativa",e);
		}
	}

}

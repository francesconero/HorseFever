package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

/**
 * Metodici statici che implementano le funzionalità più a basso livello richieste per la comunicazione in rete.
 * Usata da {@link ControlloreReteClient} e {@link ControlloreReteServer}
 * @author Francesco
 *
 */
final class ControlloreRete {
	
	private final static int tentativiMax =  Integer.parseInt(Configurazioni.getInstance().getNetProperties().getProperty("tentativiOggetto"));

	private enum Risposta {
		POSITIVA,
		NEGATIVA
	}

	private static void inviaOggetto(Object obj, Socket socket) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(obj);
		out.flush();
	}
	
	static Object riceviOggetto(Socket socket) {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			return in.readObject();
		} catch (IOException e) {
			throw new RicezioneFallitaException(e);
		} catch (ClassNotFoundException e) {
			throw new RicezioneFallitaException(e);
		}		
	}
	
	/**
	 * Invia un oggetto tramite un socket, assicurandosi che l'oggetto inviato fosse del tipo richiesto
	 * @param obj l'oggetto da inviare
	 * @param socket il socket tramite cui inviarlo
	 * @return true se l'oggetto era del tipo richiesto, false se l'altro capo ha ritenuto l'oggetto del tipo non appropriato
	 * @throws InvioFallitoException se ci sono stati problemi gravi nell'invio dell'oggetto (ad esempio socket chiusi etc)
	 */
	static boolean inviaOggettoConRisposta(Object obj, Socket socket) {
		int tentativi = 0;
		boolean invioOK = false;
		boolean arrivato = false;
		do{
			tentativi++;
			try {
				inviaOggetto(obj, socket);
			} catch (IOException e) {				
				throw new InvioFallitoException(e);
			}
			
			Object risultato = null;
			
			try{
			risultato = riceviOggetto(socket);
			} catch (RicezioneFallitaException e){
				e.printStackTrace();
				continue;
			}
			
			if(risultato instanceof Risposta){
				arrivato = true;
				Risposta risposta = (Risposta) risultato;
				if(risposta==Risposta.POSITIVA){
					invioOK = true;
				} else {
					invioOK = false;
				}
			}else{
				throw new InvioFallitoException("Ricevuta classe diversa da Risposta: "+risultato.getClass().getName());
			}
		} while(tentativi<tentativiMax && !arrivato);
		
		if(!arrivato){
			throw new InvioFallitoException("Fallito invio oggetto " + obj);
		}
		return invioOK;
	}


	static void chiudiSockets(Collection<Socket> collection) throws IOException {
		for(Socket socket : collection){
			socket.close();
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

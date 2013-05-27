package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.InvioFallitoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Classe astratta che implementa le funzionalità più a basso livello richieste per la comunicazione in rete.
 * Estesa da {@link ControlloreReteClient} e {@link ControlloreReteServer}
 * @author Francesco
 *
 */
public abstract class ControlloreRete {
	
	private final int tentativiMax;

	protected ControlloreRete(){
		tentativiMax = Integer.parseInt(Configurazioni.getInstance().getNetProperties().getProperty("tentativiOggetto"));
	}

	private enum Risposta {
		POSITIVA,
		NEGATIVA
	}

	private void inviaOggetto(Object obj, Socket socket) throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(obj);
		out.flush();
	}
	
	private void inviaOggetto(Object obj, Socket socket, int tentativiMax) throws InvioFallitoException{
		int tentativi = 0;
		boolean invioOK = false;
		do{
			tentativi++;
			try {
				inviaOggetto(obj, socket);
				invioOK = true;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		} while(tentativi<tentativiMax && !invioOK);
		
		if(!invioOK){
			throw new InvioFallitoException("Fallito invio oggetto" + obj);
		}
	}
	
	private Object riceviOggetto(Socket socket) throws ClassNotFoundException, IOException{
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		return in.readObject();
	}
	
	protected Object riceviOggetto(Socket s, int tentativiMax) throws RicezioneFallitaException{
		int tentativi = 0;
		Object out = null;
		do{			
			try {
				out = riceviOggetto(s);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			tentativi++;
		}while(tentativi<tentativiMax && out==null);
		if(out == null){
			throw new RicezioneFallitaException("Fallita la ricezione dell'oggetto per " + tentativiMax + " volte");
		}
		return out;
	}
	
	protected void inviaOggettoConRisposta(Object obj, Socket socket, int tentativiMax) throws InvioFallitoException{
		int tentativi = 0;
		boolean invioOK = false;
		do{
			tentativi++;
			try {
				inviaOggetto(obj, socket);
			} catch (IOException e) {				
				e.printStackTrace();
				continue;
			}
			
			Object risultato = null;
			
			try {
				risultato = riceviOggetto(socket);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			if(risultato instanceof Risposta){
				Risposta risposta = (Risposta) risultato;
				if(risposta==Risposta.POSITIVA){
					invioOK = true;
				}
			}
		} while(tentativi<tentativiMax && !invioOK);
		
		if(!invioOK){
			throw new InvioFallitoException("Fallito invio oggetto " + obj);
		}
	}


	protected static void chiudiSockets(List<Socket> sockets) throws IOException {
		for(Socket socket : sockets){
			socket.close();
		}
	}
	
	protected void rispondiPositivamente(Socket s) throws InvioFallitoException {
		inviaOggetto(Risposta.POSITIVA, s, tentativiMax);
	}
	
	protected void rispondiNegativamente(Socket s) throws InvioFallitoException {
		inviaOggetto(Risposta.NEGATIVA, s, tentativiMax);
	}
	
}

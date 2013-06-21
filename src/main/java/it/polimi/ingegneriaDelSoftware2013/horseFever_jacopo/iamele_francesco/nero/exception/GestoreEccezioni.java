package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicBoolean;

public class GestoreEccezioni implements UncaughtExceptionHandler {
	protected static GestoreEccezioni instance;
	protected AtomicBoolean chiusuraUtente = new AtomicBoolean(false);
	
	protected GestoreEccezioni(){
	}
	
	public synchronized void uncaughtException(Thread arg0, Throwable arg1) {
		arg1.printStackTrace();
		if(!chiusuraUtente.get()){
			System.out.println("L'utente non ha chiuso il programma, termino");
			System.exit(-1);
		} else {
			System.out.println("L'utente ha chiuso il programma");
		}
	}

	public synchronized static UncaughtExceptionHandler getInstance() {
		if(instance == null){
			instance = new GestoreEccezioni();
		}
		return instance;
	}

	public void setChiusuraUtente() {
		chiusuraUtente.set(true);
	}
	
}

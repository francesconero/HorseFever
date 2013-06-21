package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class GestoreEccezioni implements UncaughtExceptionHandler {
	protected static GestoreEccezioni instance;
	protected boolean chiusuraUtente = false;
	
	protected GestoreEccezioni(){
	}
	
	public synchronized void uncaughtException(Thread arg0, Throwable arg1) {
		arg1.printStackTrace();
		if(!chiusuraUtente){
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

	public void setChiusuraUtente(boolean chiusuraUtente) {
		this.chiusuraUtente = chiusuraUtente;
	}
	
}

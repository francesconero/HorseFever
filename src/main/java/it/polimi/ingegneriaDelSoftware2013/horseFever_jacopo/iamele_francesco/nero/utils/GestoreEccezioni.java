package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.lang.Thread.UncaughtExceptionHandler;

public class GestoreEccezioni implements UncaughtExceptionHandler {
	public static GestoreEccezioni instance;
	
	public void uncaughtException(Thread arg0, Throwable arg1) {
		arg1.printStackTrace();
		System.exit(-1);
	}

	public static UncaughtExceptionHandler getInstance() {
		return instance==null ? new GestoreEccezioni() : instance;
	}
	
}

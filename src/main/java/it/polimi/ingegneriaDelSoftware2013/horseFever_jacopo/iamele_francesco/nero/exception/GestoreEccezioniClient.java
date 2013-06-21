package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica.ControlloreGrafica;

import java.lang.Thread.UncaughtExceptionHandler;

public class GestoreEccezioniClient implements UncaughtExceptionHandler {
	protected static GestoreEccezioniClient instance;
	private ControlloreGrafica controlloreGrafica;
	
	protected GestoreEccezioniClient(){
	}
	
	public void uncaughtException(Thread arg0, Throwable arg1) {
		System.err.println("[GESTORE ECCEZIONI CLIENT]");
		arg1.printStackTrace();
		controlloreGrafica.finePartitaForzata(arg1);
	}

	public synchronized static GestoreEccezioniClient getInstance() {
		if(instance == null){
			instance = new GestoreEccezioniClient();
		}
		return instance;
	}

	public void setClient(ControlloreGrafica controlloreGrafica) {
		this.controlloreGrafica = controlloreGrafica;
	}
	
}

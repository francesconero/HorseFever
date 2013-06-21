package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;

import java.lang.Thread.UncaughtExceptionHandler;

public class GestoreEccezioniServer implements UncaughtExceptionHandler {
	protected static GestoreEccezioniServer instance;
	private ControlloreFasiGioco server;
	
	protected GestoreEccezioniServer(){
	}
	
	public void uncaughtException(Thread arg0, Throwable arg1) {
		System.err.println("[GESTORE ECCEZIONI SERVER]");
		arg1.printStackTrace();
		if(server.isChiuso().compareAndSet(false, true)){
			server.chiudi();
			System.out.println("Server chiuso");
		}
	}

	public synchronized static GestoreEccezioniServer getInstance() {
		if(instance == null){
			instance = new GestoreEccezioniServer();
		}
		return instance;
	}

	public void setServer(ControlloreFasiGioco controlloreGioco) {
		this.server = controlloreGioco;
	}
	
}

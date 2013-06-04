package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executors;

public class ControlloreConsoleIniziale extends ControlloreConsole {

	public ControlloreConsoleIniziale() {
		try {
			this.numeroGiocatori = view.chiediNumeroGiocatori();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		 executor = Executors.newFixedThreadPool(numeroGiocatori);
	}	
	
	public void controlla(){
		List<String> nomi;
		try {
			nomi = chiediNomi();
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		
		new Thread("Server"){
			public void run() {
				ServerMain.main(new String[]{Integer.toString(numeroGiocatori)});
			};
		}.start();
		
		for(String nome: nomi){
			try {
				utenti.add(new ControlloreReteClient(nome, "127.0.0.1"));
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			}
		}
		
		for(ControlloreReteClient utente: utenti){
			utente.collegaGioco();
		}
		
		aggiornaViste();
		
		ControlloreConsole next = new ControlloreConsoleScommessa(this);
		next.controlla();
		
	}

	private List<String> chiediNomi() throws IOException {
		List<String> nomi = view.chiediNomi(numeroGiocatori);
		if(nomi.size()!=numeroGiocatori){
			throw new RuntimeException("Numero giocatori errato");
		}
		return nomi;
	}
	
	public static void main(String[] args){
		
		try {
			ControlloreConsoleIniziale cc;
			cc = new ControlloreConsoleIniziale();
			cc.controlla();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}



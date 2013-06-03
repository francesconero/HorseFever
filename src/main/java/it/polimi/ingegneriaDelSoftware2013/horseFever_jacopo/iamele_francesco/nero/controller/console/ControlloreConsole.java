package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.ConsoleView;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ControlloreConsole {
	
	private List<ControlloreReteClient> utenti = new ArrayList<ControlloreReteClient>();
	private ConsoleView view = new ConsoleView();
	private final int numeroGiocatori;
	
	public ControlloreConsole() throws IOException{
		this.numeroGiocatori = view.chiediNumeroGiocatori();
	}
	
	public void inizia() throws IOException{
		List<String> nomi = chiediNomi();
		
		new Thread("Server"){
			public void run() {
				ServerMain.main(new String[]{Integer.toString(numeroGiocatori)});
			};
		}.start();
		
		for(String nome: nomi){
			utenti.add(new ControlloreReteClient(nome, "127.0.0.1"));
		}
		
		for(ControlloreReteClient utente: utenti){
			utente.collegaGioco();
		}
		
		for(ControlloreReteClient utente: utenti){
			utente.riceviStatoDelGioco();
		}
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
			ControlloreConsole cc;
			cc = new ControlloreConsole();
			cc.inizia();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}



package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

public class ControlloreConsoleIniziale extends ControlloreConsole {

	public ControlloreConsoleIniziale() {
		try {
			this.numeroGiocatori = view.chiediNumeroGiocatori();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		 executor = Executors.newCachedThreadPool();
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
//				try {
//					JavaProcess.exec(ServerMain.class, new String[]{Integer.toString(numeroGiocatori)});
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				} catch (InterruptedException e) {
//					throw new RuntimeException(e);
//				}
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
		
		aspetta(2);
		
		aggiornaViste();
		
		controllaEliminati();
		
		aspetta(2);
		
		aggiornaViste();
		
		ControlloreConsole next = new ControlloreConsoleScommessa(this);
		next.controlla();
		
	}
	
	private void controllaEliminati() {
		List<GiocatoreView> daControllare = getViewGenerica().getGiocatori();
		List<ControlloreReteClient> daEliminare = new LinkedList<ControlloreReteClient>();
		
		for(ControlloreReteClient client : utenti){
			boolean presente = false;
			for(GiocatoreView g : daControllare){
				if(client.getID()==g.getID()){
					presente = true;
				}
			}
			if(!presente){
				System.out.println("Attenzione bisogna eliminare: "+client);
				daEliminare.add(client);
				view.eliminaGiocatore(client.getProprioNome(), client.getID());
			}
		}
		
		for(ControlloreReteClient client : daEliminare){
			utenti.remove(client);
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
			cc = new ControlloreConsoleIniziale();
			cc.controlla();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}


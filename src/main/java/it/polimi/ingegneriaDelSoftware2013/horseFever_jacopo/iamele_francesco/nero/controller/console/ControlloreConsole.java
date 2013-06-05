package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.ConsoleView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class ControlloreConsole {

	protected List<ControlloreReteClient> utenti = new LinkedList<ControlloreReteClient>();
	protected Map<ControlloreReteClient, StatoDelGiocoView> visteGiocatori = new HashMap<ControlloreReteClient, StatoDelGiocoView>();
	protected ConsoleView view = new ConsoleView();
	protected int numeroGiocatori;
	protected ExecutorService executor;
	
	public ControlloreConsole(){
		
	}
	
	public ControlloreConsole(ControlloreConsole controllore) {
		this.numeroGiocatori = controllore.numeroGiocatori;
		view = controllore.view;
		utenti = controllore.utenti;
		executor = controllore.executor;
		visteGiocatori = controllore.visteGiocatori;
	}
	
	protected abstract void controlla();
	
	protected final void aggiornaViste(){
		List<Future<ClientViewPair>> tempList = new LinkedList<Future<ClientViewPair>>();
		
		for(ControlloreReteClient utente: utenti){
			ControlloreConsole.RicevitoreGioco worker = new RicevitoreGioco(utente);			
			tempList.add(executor.submit(worker));
		}
		
		for(Future<ClientViewPair> future : tempList){
			try {
				ClientViewPair temp = future.get();
				visteGiocatori.put(temp.utente, temp.view);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		
		view.mostraSituazioneGenerale(visteGiocatori.values().iterator().next());
		
	}
	
	protected final ControlloreReteClient getClientDiTurno(){
		
		StatoDelGiocoView viewTemp = getViewGenerica();
		ControlloreReteClient out = null;
		Long toCheck = viewTemp.getGiocatoreDiTurno().getID();
		
		for(ControlloreReteClient temp : utenti){
			if(temp.getID().equals(toCheck)){
				out = temp;
			}
		}
		if(out==null){
			throw new NullPointerException("Non esiste un giocatore di turno?");
		}
		return out;
	}
	
	protected void aspetta(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}

	protected StatoDelGiocoView getViewGenerica() {
		return visteGiocatori.values().iterator().next();
	}

	private class ClientViewPair {
		final ControlloreReteClient utente;
		final StatoDelGiocoView view;
		
		public ClientViewPair(ControlloreReteClient utente, StatoDelGiocoView view){
			this.utente = utente;
			this.view = view;
		}
		
	}

	protected final class RicevitoreGioco implements Callable<ClientViewPair> {

		private ControlloreReteClient utente;

		public RicevitoreGioco(ControlloreReteClient utente) {
			this.utente = utente;
		}
		
		public ClientViewPair call() throws Exception {
			System.out.println("attendo stato del gioco");
			StatoDelGiocoView sGV = utente.riceviStatoDelGioco();			
			ClientViewPair out = new ClientViewPair(utente, sGV);
			System.out.println("ricevuto stato del gioco");
			return out;
		}

	}

}
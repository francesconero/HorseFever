package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.ConsoleView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

public class ControlloreConsoleIniziale extends ControlloreConsole {

	public ControlloreConsoleIniziale(View view) {
		super(view);
		this.numeroGiocatori = view.chiediNumeroGiocatori();
		executor = Executors.newCachedThreadPool();
	}

	public void controlla() {
		List<String> nomi;
		try {
			nomi = chiediNomi();
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}	

		for (String nome : nomi) {
			try {
				utenti.add(new ControlloreReteClient(nome, "127.0.0.1"));
			} catch (UnknownHostException e) {
				throw new RuntimeException(e);
			}
		}

		for (ControlloreReteClient utente : utenti) {
			utente.collegaGioco();
		}
		
		aggiornaViste();
		
		for (int i = 0; i < getViewGenerica().getNumTurniTotali(); i++) {
			aggiornaViste(); // dopo distribuzione carte
			view.scrivi("Sta iniziando il turno: "+getViewGenerica().getNumTurno()+"!");
			view.mostraSituazioneGenerale(visteGiocatori.values().iterator()
					.next());

			aggiornaViste(); // dopo eliminazione giocatore

			controllaEliminati();

			aggiornaViste(); // attesa prima scomessa

			ControlloreConsole next = new ControlloreConsoleScommessa(this);
			next.controlla();
			next = new ControlloreConsoleAlterazione(next);
			next.controlla();
			next = new ControlloreConsoleScommessa(next);
			next.controlla();
			//next = new ControlloreConsoleCorsa(next);
			next.controlla();
			next = new ControlloreConsoleFineTurno(next);
			next.controlla();
		}

		view.scrivi("GIOCO TERMINATO!");

		System.exit(0);

		for (ControlloreReteClient utente : utenti) {
			utente.scollegaGioco();
		}
	}

	private void controllaEliminati() {
		List<GiocatoreView> daControllare = getViewGenerica().getGiocatori();
		List<ControlloreReteClient> daEliminare = new LinkedList<ControlloreReteClient>();

		for (ControlloreReteClient client : utenti) {
			boolean presente = false;
			for (GiocatoreView g : daControllare) {
				if (client.getID() == g.getID()) {
					presente = true;
				}
			}
			if (!presente) {
				view.scrivi("Attenzione bisogna eliminare: " + client);
				daEliminare.add(client);
				view.eliminaGiocatore(client.getProprioNome(), client.getID());
			}
		}

		for (ControlloreReteClient client : daEliminare) {
			utenti.remove(client);
		}
	}

	private List<String> chiediNomi() throws IOException {
		List<String> nomi = view.chiediNomi(numeroGiocatori);
		if (nomi.size() != numeroGiocatori) {
			throw new RuntimeException("Numero giocatori errato");
		}
		return nomi;
	}

	public static void main(String[] args) {
		final ControlloreConsole cc;
		cc = new ControlloreConsoleIniziale(new ConsoleView(System.in, new PrintWriter(System.out)));
		new Thread("Server") {
			public void run() {
				ServerMain.main(new String[] { Integer
						.toString(cc.getNumeroGiocatori()) });
				// try {
				// JavaProcess.exec(ServerMain.class, new
				// String[]{Integer.toString(numeroGiocatori)});
				// } catch (IOException e) {
				// throw new RuntimeException(e);
				// } catch (InterruptedException e) {
				// throw new RuntimeException(e);
				// }
			};
		}.start();
		
		try {			
			cc.controlla();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

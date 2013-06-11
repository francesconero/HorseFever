package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;

import java.util.List;

public class ControlloreConsoleCorsa extends ControlloreConsole implements MossaCorsaVisitor{

	private ControlloreReteClient primoGiocatore;
	
	public ControlloreConsoleCorsa(ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);		
	}

	@Override
	protected void controlla() {
		primoGiocatore = getClientPrimoGiocatore();
		List<MossaCorsa> lista = getViewGenerica().getMosseCorsa();
		
		for(MossaCorsa mossaCorsa : lista){
			mossaCorsa.accept(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}

	public void visita(MossaCorsa movimento) {
		view.scrivi(movimento.getCommento());
		view.mostraCorsa(movimento.getPosizioni());
	}

	public void visita(Sprint sprint) {
		view.scrivi(sprint.getCommento());
		view.mostraCorsa(sprint.getPosizioni());
	}

	public void visita(Photofinish photofinish) {
		view.scrivi(photofinish.getCommento());
		view.mostraCorsa(photofinish.getPosizioni());
	}

	public void visita(Conflitto conflitto) {
		view.scrivi(conflitto.getCommento());
		List<Colore> conflittoRisolto = view.risolviConflitto(conflitto.getScuderieInConflitto(), primoGiocatore.getProprioNome());
		primoGiocatore.risolviConflitto(conflittoRisolto);
		aggiornaViste();
		ControlloreConsoleCorsa cCC = new ControlloreConsoleCorsa(this);
		cCC.controlla();
	}

	public void visita(Classifica classifica) {
		view.scrivi(classifica.getCommento());
		view.mostraClassifica(classifica.getClassifica());
	}

	public void visita(FineGara fineGara) {
		view.scrivi(fineGara.getCommento());
		view.mostraCorsa(fineGara.getPosizioni());
		aggiornaViste();
		ControlloreConsoleFineTurno cCFT = new ControlloreConsoleFineTurno(this);
		cCFT.controlla();		
	}

	public void visita(Partenza partenza) {
		view.scrivi(partenza.getCommento());
		view.mostraCorsa(getViewGenerica().getCorsie());
	}
	
}

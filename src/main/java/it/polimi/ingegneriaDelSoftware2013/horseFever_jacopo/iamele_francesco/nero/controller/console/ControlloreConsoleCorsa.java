package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Movimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.View;

import java.util.Map;

public class ControlloreConsoleCorsa implements MossaCorsaVisitor {
	
	private final View view;
	private final Map<Giocatore, String> nomi;
	private final StatoDelGioco statoDelGioco;

	public ControlloreConsoleCorsa(StatoDelGioco statoDelGioco, View view, Map<Giocatore, String> nomi) {
		this.statoDelGioco = statoDelGioco;
		this.view = view;
		this.nomi = nomi;
	}

	public void visita(Movimento movimento) {
		view.scrivi(movimento.getNuovePosizioni().toString());
		view.scrivi(movimento.getCommento());
		view.mostraCorsa(movimento.getNuovePosizioni());
	}

	public void visita(Sprint sprint) {
		if(sprint.getNuovePosizioni().size()!=0){
			view.scrivi(sprint.getCommento());
			view.mostraCorsa(sprint.getNuovePosizioni());
		} else {
			view.scrivi("Nessun cavallo ha sprintato!");
		}
	}

	public void visita(Photofinish photofinish) {
		view.scrivi(photofinish.getCommento());
	}

	public void visita(Conflitto conflitto) {
		view.scrivi(conflitto.getCommento());
	}

	public void visita(Classifica classifica) {
		view.scrivi(classifica.getCommento());
		view.mostraClassifica(classifica.getClassifica());
	}

	public void visita(FineGara fineGara) {
		view.scrivi(fineGara.getCommento());
	}

	public void visita(Partenza partenza) {
		view.scrivi(partenza.getNuovePosizioni().toString());
		view.scrivi(partenza.getCommento());
		view.mostraCorsa(partenza.getNuovePosizioni());
	}

}

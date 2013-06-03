package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;

public interface FamilyView {

	public void aggiorna(StatoDelGiocoView view);
	
	public void aggiungiObserver(FamilyViewObserver observer);
	
}

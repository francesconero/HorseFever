package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;

public class ControlloreGioco {
	private StatoDelGioco statoDelGioco;
	private Mazziere mazziere;
	private ControlloreReteServer controlloreRete;

	public ControlloreGioco(int numGiocatori) {
		mazziere= new Mazziere();
		controlloreRete= new ControlloreReteServer(numGiocatori);
	
	}

	public void inizia() {
		
		
	}

}

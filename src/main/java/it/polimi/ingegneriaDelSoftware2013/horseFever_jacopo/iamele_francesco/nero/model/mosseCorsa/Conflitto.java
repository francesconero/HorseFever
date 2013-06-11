package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Conflitto extends MossaCorsa {

	private final List<Colore> scuderieInConflitto;
	
	public Conflitto(String commento, List<Scuderia> scuderieInConflitto, Map<Scuderia, Integer> nuovePosizioniScuderie){
		super(commento, nuovePosizioniScuderie);
		this.scuderieInConflitto = ricavaColori(scuderieInConflitto);
	}
	
	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

	public List<Colore> getScuderieInConflitto() {
		return Collections.unmodifiableList(scuderieInConflitto);
	}

}

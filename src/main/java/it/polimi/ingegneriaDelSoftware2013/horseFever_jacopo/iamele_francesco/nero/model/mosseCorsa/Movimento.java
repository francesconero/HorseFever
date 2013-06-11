package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Map;

public class Movimento extends MossaCorsa{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4311618539969686465L;
	public Movimento(String commento, Map<Scuderia, Integer> nuovePosizioniScuderie){
		super(commento, nuovePosizioniScuderie);		
	}
	
	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

}

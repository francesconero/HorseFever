package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Map;


public class Sprint extends MossaCorsa {
	
	private final Map<Colore, Integer> nuovePosizioniScuderie;

	public Sprint(String commento, Map<Scuderia, Integer> nuovePosizioniScuderie) {
		super(commento);
		this.nuovePosizioniScuderie = ricavaColori(nuovePosizioniScuderie);
	}

	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

	public Map<Colore, Integer> getPosizioni() {
		return nuovePosizioniScuderie;
	}

}

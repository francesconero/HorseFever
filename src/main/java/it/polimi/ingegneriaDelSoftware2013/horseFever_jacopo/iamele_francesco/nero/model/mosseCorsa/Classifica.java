package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Collections;
import java.util.List;

public class Classifica extends MossaCorsa {

	private final List<Colore> classifica;
	
	public Classifica(String commento, List<Scuderia> classifica){
		super(commento);
		this.classifica = ricavaColori(classifica);
	}
	
	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

	public List<Colore> getClassifica() {
		return Collections.unmodifiableList(classifica);
	}

}

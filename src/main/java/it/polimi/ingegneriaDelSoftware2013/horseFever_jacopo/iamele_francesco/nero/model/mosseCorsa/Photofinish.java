package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Collections;
import java.util.List;


public class Photofinish extends MossaCorsa {

	private final List<Colore> scuderieParimerito;
	private final int posizioneConflittuale;
	
	public Photofinish(String commento, List<Scuderia> scuderieParimerito) {
		super(commento);
		this.scuderieParimerito = ricavaColori(scuderieParimerito);
		this.posizioneConflittuale = scuderieParimerito.get(0).getPosizione();
	}

	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

	public List<Colore> getScuderieParimerito() {
		return Collections.unmodifiableList(scuderieParimerito);
	}

	public int getPosizioneConflittuale() {
		return posizioneConflittuale;
	}

}

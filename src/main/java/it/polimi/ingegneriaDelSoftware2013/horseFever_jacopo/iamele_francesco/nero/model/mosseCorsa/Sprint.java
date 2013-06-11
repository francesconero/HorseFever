package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.util.Collections;
import java.util.Map;


public class Sprint extends MossaCorsa {
	
	private final Map<Colore, Integer> relazioneScuderiaNumeroDiSprint;

	public Sprint(String commento, Map<Scuderia, Integer> relazioneScuderiaNumeroDiSprint,  Map<Scuderia, Integer> nuovePosizioniScuderie) {
		super(commento, nuovePosizioniScuderie);
		this.relazioneScuderiaNumeroDiSprint = ricavaColori(relazioneScuderiaNumeroDiSprint);
	}

	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

	public Map<Colore, Integer> getRelazioneScuderiaNumeroDiSprint() {
		return Collections.unmodifiableMap(relazioneScuderiaNumeroDiSprint);
	}

}

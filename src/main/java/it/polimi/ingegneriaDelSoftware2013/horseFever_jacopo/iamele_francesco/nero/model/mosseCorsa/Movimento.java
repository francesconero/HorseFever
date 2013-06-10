package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.util.Collections;
import java.util.List;

public class Movimento extends MossaCorsa{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4311618539969686465L;
	private static final int NUMERO_SCUDERIE = Integer.parseInt(Configurazioni.getInstance().getGiocoProperties().getProperty("numeroScuderie"));
	private final List<Integer> nuovePosizioniScuderie;
	
	public Movimento(String commento, List<Integer> nuovePosizioniScuderie){
		super(commento);
		if(nuovePosizioniScuderie.size() != NUMERO_SCUDERIE){
			throw new IllegalArgumentException("Numero di scuderie errato");
		}else{
			this.nuovePosizioniScuderie = nuovePosizioniScuderie;
		}
	}
	
	public List<Integer> getMovimenti(){
		return Collections.unmodifiableList(nuovePosizioniScuderie);
	}

	@Override
	public void accept(MossaCorsaVisitor mossaCorsaVisitor) {
		mossaCorsaVisitor.visita(this);
	}

}

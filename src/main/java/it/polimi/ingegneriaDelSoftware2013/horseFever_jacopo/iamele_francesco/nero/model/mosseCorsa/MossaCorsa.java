package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class MossaCorsa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7875944118880415553L;
	private String commento;
	
	public MossaCorsa(String commento){
		this.commento = commento;
	}
	
	public abstract void  accept(MossaCorsaVisitor mossaCorsaVisitor);
	
	public String getCommento() {
		return commento;
	}
	
	public void setCommento(String commento) {
		this.commento = commento;
	}

	protected List<Colore> ricavaColori(List<Scuderia> listaScuderie) {
		List<Colore> out = new LinkedList<Colore>();
		for(Scuderia scuderia : listaScuderie){
			out.add(scuderia.getColore());
		}
		return Collections.unmodifiableList(out);
	}

	protected <T extends Object> Map<Colore, T> ricavaColori(Map<Scuderia, T> relazioniScuderia) {
		Map<Colore, T> out = new LinkedHashMap<Colore, T>();
		for(Scuderia scuderia : relazioniScuderia.keySet()){
			out.put(scuderia.getColore(), relazioniScuderia.get(scuderia));
		}
		return Collections.unmodifiableMap(out);
	}
	
}

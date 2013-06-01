package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.util.*;
public class CartaMovimento {
	private Map<Integer, Integer> movimenti;
	
	public CartaMovimento(int quotazioneAssociata, int movimento){
        movimenti=new HashMap<Integer, Integer>();
		this.movimenti.put(quotazioneAssociata, movimento);
	}
	
	
	public Map<Integer, Integer> getCartaMovimento(){
		return movimenti;
	}
	

}

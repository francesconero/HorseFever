package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.Configurazioni;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartaMovimento implements Carta {
	private Map<Integer, Integer> movimenti = new HashMap<Integer, Integer>();	
	
	public CartaMovimento(List<Integer> movimenti) {
		int numeroMovimenti = Integer.parseInt(Configurazioni.getInstance().getGiocoProperties().getProperty("numeroScuderie"));
		if(movimenti.size()!=numeroMovimenti){
			throw new IllegalArgumentException("Mi aspettavo "+numeroMovimenti+" movimenti. Arrivati: "+movimenti.size());
		} else {
			for(int i = 0; i<movimenti.size(); i++){
				this.movimenti.put(i+2, movimenti.get(i));
			}
		}
	}


	public int getMovimento(int key){
		return movimenti.get(key); 
	}
	
	@Override
	public String toString() {
		String out = "Movimenti: ";
		out += "\n";
		out += Arrays.toString(movimenti.values().toArray());
		return out;
	}
	

}

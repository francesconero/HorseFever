package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.util.Arrays;

public class Movimento {
	
	private static final int numeroMovimenti = 6;
	private final int[] movimenti;
	
	public Movimento(int[] movimenti){
		if(movimenti.length != numeroMovimenti){
			throw new IllegalArgumentException("Numero di movimenti errato");
		}else{
			this.movimenti = Arrays.copyOf(movimenti, numeroMovimenti);
		}
	}

}

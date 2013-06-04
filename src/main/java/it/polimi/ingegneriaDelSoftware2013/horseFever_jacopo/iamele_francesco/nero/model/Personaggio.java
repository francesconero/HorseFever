package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.io.Serializable;

public class Personaggio implements Serializable{
	private final String nome;
	private final int danari;
	private final int quotazioneAssociata;
	
	public Personaggio(String nome, int danari, int quotazioneAssciata) {
		this.nome = nome;
		this.danari  = danari;
		this.quotazioneAssociata  = quotazioneAssciata;
	}

	public String getNome() {
		return nome;
	}

	public int getDanari() {
		return danari;
	}

	public int getScuderiaAssociata() {
		return quotazioneAssociata;
	}
	
	@Override
	public String toString() {
		String out = "Personaggio: "+nome;
		out+="\n";
		out+="danari: "+danari;
		out+="\n";
		out+="quotazione associata: "+quotazioneAssociata;
		return out;
	}

}

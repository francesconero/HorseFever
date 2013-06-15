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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + danari;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + quotazioneAssociata;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personaggio other = (Personaggio) obj;
		if (danari != other.danari)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (quotazioneAssociata != other.quotazioneAssociata)
			return false;
		return true;
	}
	
	

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.io.Serializable;

public class Scommessa implements Serializable{
	private TipoScommessa tipoScommessa;
	private int danariScommessi;
	private Colore scuderia;

	public Scommessa(TipoScommessa tipoScommessa, int danariScommessi,
			Colore scuderia) {
		this.tipoScommessa = tipoScommessa;
		this.danariScommessi = danariScommessi;
		this.scuderia = scuderia;
	}

	public TipoScommessa getTipoScommessa() {
		return tipoScommessa;
	}

	public void setTipoScommessa(TipoScommessa tipoScommessa) {
		this.tipoScommessa = tipoScommessa;
	}

	public int getDanariScommessi() {
		return danariScommessi;
	}

	public void setDanariScommessi(int danariScommessi) {
		this.danariScommessi = danariScommessi;
	}

	public Colore getScuderia() {
		return scuderia;
	}

	public void setScuderia(Colore scuderia) {
		this.scuderia = scuderia;
	}

	@Override
	public String toString() {
		return "Scuderia: " + scuderia + " Tipo scommessa: " + tipoScommessa
				+ " " + " Danari scommessi: " + danariScommessi;
	}
}

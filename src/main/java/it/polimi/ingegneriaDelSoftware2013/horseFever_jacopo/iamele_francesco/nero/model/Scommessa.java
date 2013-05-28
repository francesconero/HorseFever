package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

public class Scommessa {
	private TipoScommessa tipoScommessa;
	private int danariScommessi;
	private Giocatore giocatore;
	private Scuderia scuderia;
	
	
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
	public Giocatore getGiocatore() {
		return giocatore;
	}
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}
	public Scuderia getScuderia() {
		return scuderia;
	}
	public void setScuderia(Scuderia scuderia) {
		this.scuderia = scuderia;
	}
	

}

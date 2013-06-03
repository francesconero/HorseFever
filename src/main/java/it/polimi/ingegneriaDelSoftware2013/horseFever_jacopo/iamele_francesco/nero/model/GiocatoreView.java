package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.*;

public class GiocatoreView {
	private int danari;
	private int puntiVittoria;
	private List<Scuderia> scuderie;
	private Personaggio personaggio;
	private boolean primoGiocatore=false;
	private List<CartaAzione> carteAzioniCoperte;
	private String nomeUtente;
	
	
	public GiocatoreView(Giocatore giocatoreDaOscurare, String nomeUtente){
		this.danari=giocatoreDaOscurare.getDanari();
		this.puntiVittoria=giocatoreDaOscurare.getPuntiVittoria();
		this.personaggio=giocatoreDaOscurare.getPersonaggio();
		this.primoGiocatore=giocatoreDaOscurare.isPrimoGiocatore();
		this.scuderie=giocatoreDaOscurare.getScuderie();
		carteAzioniCoperte= new ArrayList<CartaAzione>();
		for (int i=0; i<giocatoreDaOscurare.getCarteAzione().size();i++){
			carteAzioniCoperte.add(new CartaAzione());//CartaAzione coperta la prendiamo da file	
		}
		this.nomeUtente = nomeUtente;
	}
	public int getDanari() {
		return danari;
	}

	public void setDanari(int danari) {
		this.danari = danari;
	}

	public int getPuntiVittoria() {
		return puntiVittoria;
	}

	public void setPuntiVittoria(int puntiVittoria) {
		this.puntiVittoria = puntiVittoria;
	}

	public List<Scuderia> getScuderie() {
		return scuderie;
	}

	public void setScuderie(List<Scuderia> scuderie) {
		this.scuderie = scuderie;
	}

	public Personaggio getPersonaggio() {
		return personaggio;
	}

	public void setPersonaggio(Personaggio personaggio) {
		this.personaggio = personaggio;
	}

	public boolean isPrimoGiocatore() {
		return primoGiocatore;
	}

	public void setPrimoGiocatore(boolean primoGiocatore) {
		this.primoGiocatore = primoGiocatore;
	}

	public List<CartaAzione> getCarteAzioniCoperte() {
		return carteAzioniCoperte;
	}

	public void setCarteAzioniCoperte(List<CartaAzione> carteAzioniCoperte) {
		this.carteAzioniCoperte = carteAzioniCoperte;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}

		
	

}

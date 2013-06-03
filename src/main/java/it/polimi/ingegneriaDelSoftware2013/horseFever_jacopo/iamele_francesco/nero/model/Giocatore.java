package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.*;

public class Giocatore {
	private int danari;
	private int puntiVittoria;
	private List<Scuderia> scuderie;
	private Personaggio personaggio;
	private List<CartaAzione> carteAzione;
	private boolean primoGiocatore=false;

	public Giocatore(int danari, int puntiVittoria, List<Scuderia> scuderia,Personaggio personaggio) {
		
		this.danari = danari;
		this.puntiVittoria = puntiVittoria;
		this.scuderie=scuderia;
		this.personaggio = personaggio;
		
		
	}

	public void setPrimoGiocatore(boolean primoGiocatore){
		this.primoGiocatore=primoGiocatore;
	}
	
	public boolean isPrimoGiocatore(){
		return primoGiocatore;
	}
	
	public void setCarteAzione(CartaAzione cartaDaAggiungere){
		carteAzione.add(cartaDaAggiungere);
	}
	
	public List<CartaAzione> getCarteAzione(){
		return carteAzione;
	}
	
	public void setScuderia(Scuderia scuderiaDaAssegnare){
		scuderie.add(scuderiaDaAssegnare);
	}
	
	public List<Scuderia> getScuderie(){
		return scuderie;
	}
	
	public void addPuntiVittoria(int puntiVittoriaDaAggiungere){
		puntiVittoria+=puntiVittoriaDaAggiungere;
	}
	
	public void removePuntiVittoria(int puntiVittoriaDaTogliere){
		puntiVittoria-=puntiVittoriaDaTogliere;
	}
	
	public int getPuntiVittoria(){
		return puntiVittoria;
	}
	
	public void setPersonaggio(Personaggio personaggioDaPassare){
		personaggio=personaggioDaPassare;
	}

	public Personaggio getPersonaggio(){
		return personaggio;
	}
	
	public void setDanari(int nuoviDanari){
		danari=nuoviDanari;
	}
	
	public int getDanari(){
		return danari;
	}
}


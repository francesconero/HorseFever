package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Giocatore implements Serializable {
	private int danari;
	private int puntiVittoria;
	private List<Scuderia> scuderie=new ArrayList<Scuderia>();
	private Personaggio personaggio;
	private List<CartaAzione> carteAzione=new ArrayList<CartaAzione>();
	private boolean primoGiocatore=false;
	private List<Scommessa> scommesseEffettuate=new ArrayList<Scommessa>();

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
	public void addDanari(int danariDaAggiungere){
		this.danari=this.danari+danariDaAggiungere;
	}
	public void removeDanari(int danariDaRimuovere){
		this.danari=this.danari-danariDaRimuovere;
	}

	public List<Scommessa> getScommesseEffettuate() {
		return scommesseEffettuate;
	}

	public void setScommesseEffettuate(List<Scommessa> scommesseEffettuate) {
		this.scommesseEffettuate = scommesseEffettuate;
	}
	
	public void addScommessa(Scommessa scommessaDaAggiungere){
		scommesseEffettuate.add(scommessaDaAggiungere);
	}
}


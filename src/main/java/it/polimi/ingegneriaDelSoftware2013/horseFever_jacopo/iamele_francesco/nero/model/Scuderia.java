package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/***
 * Questa � la classe Scuderia
 * Oltre agli attributi "banali" salva anche  
 * le scommesse effettuate, le carteAzione giocate e le stesse carteAzione coperte
 */

public class Scuderia implements Serializable{
	private int posizione=0;
	private Colore colore;
	private int scommesseDisponibili;
	private final int scommesseTotaliDisponibili;
	private boolean arrivato;
	private List<CartaAzione> carteAzione=new ArrayList<CartaAzione>();
	private int quotazione;
	private int numCicliArrivato=0; //rappresenta da quanto tempo la scuderia � arrivata 
	
	
	public Scuderia(Colore colore,int scommesseDisponibili){
		this.colore=colore;
		this.scommesseDisponibili=scommesseDisponibili;
		this.scommesseTotaliDisponibili=scommesseDisponibili;
	}
	
	
	public int getPosizione() {
		return posizione;
	}
	public void setPosizione(int posizioneModificata) {
		this.posizione=posizioneModificata;
		
	}
	public void addPosizione(int valoreDaAggiungere){
		this.posizione= posizione+valoreDaAggiungere;
	}
	
	public void resetPosizione(){
		this.posizione=0;
	}

	
	public Colore getColore() {
		return colore;
	}
	public void setColore(Colore colore) {
		this.colore = colore;
	}

	
	public int getScommesseDisponibili() {
		return scommesseDisponibili;
	}
	public void setScommesseDisponibili(int scommesseDisponibili) {
		this.scommesseDisponibili = scommesseDisponibili;
	}
	
	public void removeScommesseDisponibili(int quantitaDaRimuovere){
		this.scommesseDisponibili=this.scommesseDisponibili-quantitaDaRimuovere;
	}
	public int getScommesseTotaliDisponibili(){
		return scommesseTotaliDisponibili;
	}
	public void resetScommesseDisponibili(){
		this.scommesseDisponibili=scommesseTotaliDisponibili;
	}
	
	
	public boolean isArrivato() {
		return arrivato;
	}
	public void setArrivato(boolean arrivato) {
		this.arrivato = arrivato;
	}

	
	public List<CartaAzione> getCarteAzione() {
		return carteAzione;
	}
	public void setCarteAzione(List<CartaAzione> carteAzione) {
		this.carteAzione = carteAzione;
		
	}
	public void addCartaAzione(CartaAzione cartaDaAggiungere){
		this.carteAzione.add(cartaDaAggiungere);
	}
	
	public void assegnaQuotazione(int quotazione){
		this.quotazione=quotazione;
	}
	public int getQuotazione(){
		return quotazione;
	}
	
	
	public void addNumCicliArrivato(int valoreDaAggiungere){
		this.numCicliArrivato=this.numCicliArrivato+valoreDaAggiungere;
	}
	public int getNumCicliArrivato(){
		return numCicliArrivato;
	}

	/***
	 * Questo metodo, oltre ad aggiungere un valore alla quotazione,
	 * controlla che la quotazione sia sempre compresa tra 2 e 7
	 * @param valoreDaAggiungere
	 */
	public void addQuotazione(int valoreDaAggiungere) {
		while((this.quotazione+valoreDaAggiungere>7)||(this.quotazione+valoreDaAggiungere<2)){
		if(valoreDaAggiungere<0)valoreDaAggiungere++;	
		else valoreDaAggiungere--;
		}
		this.quotazione=this.quotazione+valoreDaAggiungere;
	}


	public void resetNumCicliArrivato() {
		this.numCicliArrivato = 0;
	}




	
	
	
	
	
	
	

	
	


}

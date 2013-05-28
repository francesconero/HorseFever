package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;
/***
 * Questa è la classe Scuderia
 * Oltre agli attributi "banali" salva anche  
 * le scommesse effettuate e le carteAzione giocate
 */
import java.util.*;

public class Scuderia {
	private int posizione;
	private Colore colore;
	private int scommesseDisponibili;
	private boolean arrivato;
	private List<CartaAzione> carteAzione;
	private List<Scommessa> scommesse;
	
	public Scuderia(Colore colore,int scommesseDisponibili){
		this.colore=colore;
		this.scommesseDisponibili=scommesseDisponibili;
		
	}
	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
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

	public List<Scommessa> getScommesse() {
		return scommesse;
	}

	public void setScommesse(List<Scommessa> scommesse) {
		this.scommesse = scommesse;
	}


}

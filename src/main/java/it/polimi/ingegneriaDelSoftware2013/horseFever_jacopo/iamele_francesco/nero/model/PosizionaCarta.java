package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.Carta;

import java.io.Serializable;

/***
 * La classe PosizionaCarta contiene come attributi il tipo di carta
 * e la scuderia da associare
 * il tipo di cartaDaPosizionare � un Object generico in quanto il giocatore
 * potrebbe decidere di posizionare un oggetto diverso da cartaAzione
 * 
 * 
 *
 * @author Jacopo
 *
 */
public class PosizionaCarta implements Serializable{
	private Carta cartaDaPosizionare;
	private Scuderia scuderiaAssociata;
	
	public PosizionaCarta(Carta cartaDaPosizionare, Scuderia scuderiaDaAssociare ){
		this.cartaDaPosizionare=cartaDaPosizionare;
		this.scuderiaAssociata=scuderiaDaAssociare;
		
	}
	public Carta getCartaDaPosizionare() {
		return cartaDaPosizionare;
	}
	public Scuderia getScuderiaAssociata() {
		return scuderiaAssociata;
	}
	

}

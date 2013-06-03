package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.Carta;

/***
 * La classe PosizionaCarta contiene come attributi il tipo di carta
 * e la scuderia da associare
 * il tipo di cartaDaPosizionare è un Object generico in quanto il giocatore
 * potrebbe decidere di posizionare un oggetto diverso da cartaAzione
 * 
 * 
 *
 * @author Jacopo
 *
 */
public class PosizionaCarta {
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

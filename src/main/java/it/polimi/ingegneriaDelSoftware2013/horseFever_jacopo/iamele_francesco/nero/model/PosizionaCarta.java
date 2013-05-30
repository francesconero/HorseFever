package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

/***
 * La classe PosizionaCarta contiene come attributi il tipo di carta
 * e la scuderia da associare
 * il tipo di cartaDaPosizionare è un Object generico in quanto il giocatore
 * potrebbe decidere di posizionare un oggetto diverso da cartaAzione
 * (forse sarebbe meglio creare un' interfaccia di tipo Carta...)
 * 
 *
 * @author Jacopo
 *
 */
public class PosizionaCarta {
	private Object cartaDaPosizionare;
	private Scuderia scuderiaAssociata;
	private Giocatore giocatoreChiamante;
	
	public Giocatore getGiocatoreChiamante() {
		return giocatoreChiamante;
	}
	
	public PosizionaCarta(Object cartaDaPosizionare, Scuderia scuderiaDaAssociare, Giocatore giocatoreChiamante ){
		this.cartaDaPosizionare=cartaDaPosizionare;
		this.scuderiaAssociata=scuderiaDaAssociare;
		this.giocatoreChiamante=giocatoreChiamante;
	}
	public Object getCartaDaPosizionare() {
		return cartaDaPosizionare;
	}
	public Scuderia getScuderiaAssociata() {
		return scuderiaAssociata;
	}
	

}

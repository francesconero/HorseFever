package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;

import java.util.List;

/**
 * Interfaccia che espone i principali metodi del gioco HorseFever, così come visti da un utente del gioco.
 * 
 * @author Francesco
 *
 */
public interface Utente {

	/**
	 * Inizia a giocare
	 */
	void collegaGioco();

	/**
	 * Fai una scommessa
	 * @param scommessaDaFare la scommessa che si vuol fare
	 * @return boolean true, se la scommessa è stato accettata, false altrimenti
	 */
	boolean scommetti(Scommessa scommessaDaFare);


	/**
	 * Posiziona una carta (ad esempio una carta azione) su di una scuderia
	 * @param posizionaCarta l'oggetto di tipo PosizionaCarta costruito in modo da rappresentare il posizionamento voluto
	 * @return boolean true, se il posizionamento era valido, false altrimenti
	 */
	boolean posizionaCarta(PosizionaCarta posizionaCarta);

	/**
	 * Ricevi un update con il nuovo stato del gioco
	 * @return StatoDelGiocoView il nuvo stato del gioco
	 */
	StatoDelGiocoView riceviStatoDelGioco();

	String riceviAvvertimento();
	
	boolean risolviConflitto(List<Colore> conflittoRisolto);

	void scollegaGioco(boolean forza);

}

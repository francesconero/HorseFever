package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;

import java.util.List;

/**
 * Interfaccia che espone al controllore del gioco, i metodi necessari a interagire con i giocatori.
 * @author Francesco
 *
 */
public interface ControlloreUtenti {

	/**
	 * Accetta tanti utenti, tanti quanti giocatori sono passati nella lista
	 * come parametro. E' il primo metodo da chiamare se si vuole interagire con
	 * gli utenti.
	 * 
	 * @param giocatori
	 *            List<{@link Giocatore}> la lista di giocatori ai quali si
	 *            vuole associare un utente
	 */
	void accettaUtenti(List<Giocatore> giocatori);

	/**
	 * Ricevi una scommessa dall'utente associato al giocatore passato come
	 * parametro
	 * 
	 * @param giocatore
	 *            Giocatore il giocatore associato all'utente
	 * @return Scommessa la scomessa ricevuta dall'utente
	 */
	Scommessa riceviScommessa(Giocatore giocatore);

	/**
	 * Ricevi la soluzione di una posizione di indecisione nell'arrivo dei cavalli, da un giocatore
	 * (solitamente il primo giocatore)
	 * 
	 * @param giocatore
	 *            Giocatore il giocatore associato all'utente
	 * @return List la lista di colori scuderie, nell'ordine di arrivo deciso dal giocatore
	 */
	List<Colore> riceviSoluzioneConflitto(Giocatore giocatore, List<Scuderia> scuderieInConflitto);
	
	/**
	 * Ricevi un posizionamento carta su di una scudera (ad esempio una carta
	 * azione) dall'utente associato al giocatore passato come parametro
	 * 
	 * @param giocatore
	 *            Giocatore il giocatore associato all'utente
	 * @return PosizionaCarta oggetto con all'interno la scuderia e la carta
	 *         giocata dall'utente
	 */
	PosizionaCarta riceviPosizionaCarta(Giocatore giocatore);

	/**
	 * Aggiorna tutti gli utenti con il nuovo stato del gioco
	 * 
	 * @param statoDelGioco
	 *            il nuovo stato del gioco
	 */
	void aggiornaUtenti(StatoDelGioco statoDelGioco);
	
	/**
	 * Aggiorna tutti gli utenti con il nuovo stato del gioco e la storia della corsa
	 * 
	 * @param statoDelGioco
	 *            il nuovo stato del gioco
	 * @param mosseCorsa
	 * 				gli avvenimenti della corsa
	 */
	void aggiornaUtenti(StatoDelGioco statoDelGioco, List<MossaCorsa> mosseCorsa);
	
	/**
	 * Conferma un'azione eseguita da un giocatore (ad esempio una scommessa esatta)
	 * @param giocatore il giocatore associato all'utente che ha eseguito una mossa corretta
	 */
	void conferma(Giocatore giocatore);
	
	/**
	 * Avverti l'utente che egli ha eseguito una mossa non valida (una scommessa di un valore non valido ad esempio)
	 * @param giocatore il giocatore associato all'utente che ha eseguito una mossa non valida
	 */
	void nega(Giocatore giocatore);
	
	void giocatoreEliminato(Giocatore giocatore);

	void avverti(Giocatore giocatore, String string);
	
	void fine();

}

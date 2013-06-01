package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.RicezioneFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;

import java.util.List;

public interface ControlloreUtentiServer {

	/**
	 * Accetta tanti utenti, tanti quanti giocatori sono passati nella lista
	 * come parametro. E' il primo metodo da chiamare se si vuole interagire con
	 * gli utenti.
	 * 
	 * @param giocatori
	 *            List<{@link Giocatore}> la lista di giocatori ai quali si
	 *            vuole associare un utente
	 * @throws AttesaUtentiFallitaException
	 *             se per qualche motivo non si riescono ad accettare tutti gli
	 *             utenti richiesti
	 */
	public void accettaUtenti(List<Giocatore> giocatori);

	/**
	 * Ricevi una scommessa dall'utente associato al giocatore passato come
	 * parametro
	 * 
	 * @param giocatore
	 *            Giocatore il giocatore associato all'utente
	 * @return Scommessa la scomessa ricevuta dall'utente
	 * @throws RicezioneFallitaException
	 *             se la ricezione dell'oggetto fallisca per qualsiasi motivo
	 *             (raro se non implementato da qualche classe che utilizza io)
	 */
	public Scommessa riceviScommessa(Giocatore giocatore);

	/**
	 * Ricevi un posizionamento carta su di una scudera (ad esempio una carta
	 * azione) dall'utente associato al giocatore passato come parametro
	 * 
	 * @param giocatore
	 *            Giocatore il giocatore associato all'utente
	 * @return PosizionaCarta oggetto con all'interno la scuderia e la carta
	 *         giocata dall'utente
	 * @throws RicezioneFallitaException
	 *             se la ricezione dell'oggetto fallisca per qualsiasi motivo
	 *             (raro se non implementato da qualche classe che utilizza io)
	 */
	public PosizionaCarta riceviPosizionaCarta(Giocatore giocatore);

	/**
	 * Aggiorna tutti gli utenti con il nuovo stato del gioco
	 * 
	 * @param statoDelGioco
	 *            il nuovo stato del gioco
	 */
	public void aggiornaUtenti(StatoDelGioco statoDelGioco);

}
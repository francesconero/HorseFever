package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
/**
 * Questa classe possiede la logica applicativa di HorseFever
 * Possiede metodi privati che rappresentano le fasi del gioco
 * Il costruttore inizializza il Mazziere, il ControlloreReteServer,
 * decide il numero di turni di una partita e le scommesse disponibili.
 * @author Jacopo
 *
 */

public class ControlloreGioco {
	private StatoDelGioco statoDelGioco;
	private Mazziere mazziere;
	private ControlloreReteServer controlloreRete;

	public ControlloreGioco(int numGiocatori) throws NumErratoGiocatoriException {
		mazziere= new Mazziere();
		controlloreRete= new ControlloreReteServer(numGiocatori);
		int segnaliniScommesse;
		int numTurni;
		switch (numGiocatori){
		case 2: {numTurni=6; segnaliniScommesse=1;break;}
		case 3: {numTurni=6; segnaliniScommesse=2;break;}
		case 4: {numTurni=4; segnaliniScommesse=3;break;}
		case 5: {numTurni=5; segnaliniScommesse=4;break;}
		case 6: {numTurni=6; segnaliniScommesse=4;break;}
		default : {throw new NumErratoGiocatoriException();}
		}
	
	}

	public void inizia() {
		
		
	}
	
	
	

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import java.util.*;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Quotazione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
/**
 * Questa classe possiede la logica applicativa di HorseFever
 * Possiede metodi privati che rappresentano le fasi del gioco
 * Il costruttore inizializza il Mazziere, il ControlloreReteServer,
 * decide il numero di turni di una partita e le scommesse disponibili e
 * definisce le 6 scuderie.
 * @author Jacopo
 *
 */

public class ControlloreGioco {
	private StatoDelGioco statoDelGioco;
	private Mazziere mazziere;
	private ControlloreReteServer controlloreRete;
	private List<Scuderia> scuderie;
	private List<Quotazione> quotazioni;
	private List<Giocatore> giocatori;
	private List<Scommessa> scommessePrimaFase;
	private List<Scommessa> scommesseSecondaFase=null; 
	private final int numTurniTotali;

	public ControlloreGioco(int numGiocatori) throws NumErratoGiocatoriException{
		mazziere= new Mazziere();
		controlloreRete= new ControlloreReteServer();
		int segnaliniScommesse;
		switch (numGiocatori){
		case 2: {numTurniTotali=6; segnaliniScommesse=1;break;}
		case 3: {numTurniTotali=6; segnaliniScommesse=2;break;}
		case 4: {numTurniTotali=4; segnaliniScommesse=3;break;}
		case 5: {numTurniTotali=5; segnaliniScommesse=4;break;}
		case 6: {numTurniTotali=6; segnaliniScommesse=4;break;}
		default : {throw new NumErratoGiocatoriException();}
		}
		scuderie.add(new Scuderia(Colore.NERO, segnaliniScommesse));
		scuderie.add(new Scuderia(Colore.BLU, segnaliniScommesse));
		scuderie.add(new Scuderia(Colore.VERDE, segnaliniScommesse));
		scuderie.add(new Scuderia(Colore.ROSSO, segnaliniScommesse));
		scuderie.add(new Scuderia(Colore.GIALLO, segnaliniScommesse));
		scuderie.add(new Scuderia(Colore.BIANCO, segnaliniScommesse));
		for (int i=0; i<numGiocatori;i++){
			giocatori.add(new Giocatore());
		}
		
		
		
		
		
		
	
	}

	private void preparazione(){
		List<Scuderia> scuderieTemp=scuderie;
		Collections.shuffle(scuderieTemp);
		for(int i=scuderie.size(); i>0;i--){
			quotazioni.add(new Quotazione(scuderieTemp.remove(0), i+1));
		}
		mazziere.mischiaPersonaggi();
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		giocatori=assegnaPrimoGiocatore(giocatori);
	}
	
	private List<Giocatore> assegnaPrimoGiocatore(List<Giocatore> giocatori){
		Random r= new Random();
		int s=r.nextInt(giocatori.size());
		giocatori.get(s).setPrimoGiocatore(true);
		giocatori=MetodiDiSupporto.creaListaOrdinata(giocatori, giocatori.get(s));
		return giocatori;
	}
	
	private List<Giocatore> aggiornaPrimoGiocatore(List<Giocatore> giocatori){
		giocatori.get(0).setPrimoGiocatore(false);
		giocatori.get(1).setPrimoGiocatore(true);
		giocatori=MetodiDiSupporto.creaListaOrdinata(giocatori, giocatori.get(1));
		return giocatori;
	}
	
	private void faseDistribuzioneCarte() throws CarteFiniteException{
		for (Giocatore g: giocatori){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		for (Giocatore g: giocatori){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
	}
	
	private void primaFaseScommesse(){
		for (Giocatore g: giocatori){
			//chiedi scommessa al controlloreserver object deve essere istanceof Scommessa 
			Scommessa scommessa=null;
			while (g.getPuntiVittoria()<scommessa.getDanariScommessi()*100);//chiedi di nuovo e riprova;
			scommessePrimaFase.add(scommessa);
		}
		//aggiorna stato del gioco
	}
	
	private void FaseTruccaCorsa(){
		for (Giocatore g: giocatori){
			
			
		}
		
	}
	
	public void inizia() throws CarteFiniteException {
		preparazione();
		faseDistribuzioneCarte();
		primaFaseScommesse();
		
		
		
	}
	
	public int getTurniTotali(){
		return numTurniTotali;
	}
	

}

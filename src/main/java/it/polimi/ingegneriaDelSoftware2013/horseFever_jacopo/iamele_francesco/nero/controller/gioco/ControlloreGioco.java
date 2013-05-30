package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import java.util.*;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreRete;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaClientsFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Quotazione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
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
	private final int numTurniTotali;

	public ControlloreGioco(int numGiocatori) throws NumErratoGiocatoriException, CarteFiniteException{
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
		statoDelGioco=new StatoDelGioco(numTurniTotali);
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.NERO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.BLU, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.VERDE, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.ROSSO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.GIALLO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.BIANCO, segnaliniScommesse));
		for (int i=0; i<numGiocatori;i++){
			statoDelGioco.aggiungiGiocatore(new Giocatore());
			
		}
	}
	
	private void aggiornaIClient(){
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			StatoDelGiocoView statoDelGiocoView = new StatoDelGiocoView(statoDelGioco, statoDelGioco.getGiocatori().get(i));
			//aggiornaClient(statoDelGiocoView,statoDelGioco.getGiocatori().get(i));
		}
		
	}
	

	private void preparazione() throws CarteFiniteException{
		List<Scuderia> scuderieTemp=statoDelGioco.getCorsie();
		Collections.shuffle(scuderieTemp);
		for(int i=statoDelGioco.getCorsie().size(); i>0;i--){
			statoDelGioco.aggiungiQuotazioni(new Quotazione(scuderieTemp.remove(0), i+1));
		}
		mazziere.mischiaPersonaggi();
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		statoDelGioco.assegnaCasualmentePrimoGiocatore();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
		statoDelGioco.getGiocatori().get(i).setPersonaggio(mazziere.popPersonaggio());
		}
	}
	
	
	
	
	
	
	
	
	private void faseDistribuzioneCarte() throws CarteFiniteException{
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
	}
	
	private void primaFaseScommesse(){
		for (Giocatore g: statoDelGioco.getGiocatori()){
			  //chiedi scommessa al controlloreserver object deve essere istanceof Scommessa 
			Scommessa scommessa=null;
			while (g.getPuntiVittoria()<scommessa.getDanariScommessi()*100);//chiedi di nuovo e riprova;
			statoDelGioco.aggiungiScommesseFattePrimaFase(scommessa);
			int i=0;
			while(statoDelGioco.getScommesseFattePrimaFase().get(i).getScuderia()!=scommessa.getScuderia()){
				i++;
			}
			
		}
		//aggiorna StatoDelGiocoView
	}
	
	private void truccaCorsa(){
		for (int i=0; i<statoDelGioco.getGiocatori().size();i++){
			//chiedi carta azione e la scuderiaAssociata al server
		}
	}
	
	
		
	
	
	public void inizia() throws CarteFiniteException, AttesaClientsFallitaException {
		controlloreRete.accettaClients(statoDelGioco.getGiocatori());
		preparazione();
		aggiornaIClient();
		faseDistribuzioneCarte();
		primaFaseScommesse();
		
		
		
	}
	
	public int getTurniTotali(){
		return numTurniTotali;
	}
	

}

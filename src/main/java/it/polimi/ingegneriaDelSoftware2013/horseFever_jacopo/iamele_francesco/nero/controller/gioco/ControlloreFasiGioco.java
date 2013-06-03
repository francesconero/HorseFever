package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.UnGiocatoreRimastoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import java.io.IOException;
import java.util.*;
/**
 * Questa classe possiede la logica applicativa di HorseFever
 * Possiede metodi privati che rappresentano le fasi del gioco
 * Il costruttore inizializza il Mazziere, il ControlloreReteServer,
 * decide il numero di turni di una partita e le scommesse disponibili e
 * definisce le 6 scuderie.
 * @author Jacopo
 *
 */

public class ControlloreFasiGioco {
	private StatoDelGioco statoDelGioco;
	private Mazziere mazziere;
	private ControlloreReteServer controlloreRete;
	
	public ControlloreReteServer getControlloreRete() {
		return controlloreRete;
	}

	private final int numTurniTotali;

	public ControlloreFasiGioco(int numGiocatori) throws NumErratoGiocatoriException, CarteFiniteException, IOException{
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
		/*
		for (int i=0; i<numGiocatori;i++){
			statoDelGioco.aggiungiGiocatore(new Giocatore());
			
		}
		*/
	}
	
	private void aggiornaIClient(){
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			
			//ControlloreUtenti.aggiornaUtenti(StatoDelGioco);
		}
		
	}
	

	private void preparazione() throws CarteFiniteException{
		statoDelGioco.setInizio(true);
		List<Scuderia> scuderieTemp=statoDelGioco.getCorsie();
		Collections.shuffle(scuderieTemp);
		for(int i=scuderieTemp.size()-1; i>=0;i--){
			scuderieTemp.get(i).assegnaQuotazione(i);
		}
		statoDelGioco.setCorsie(scuderieTemp);
		mazziere.mischiaPersonaggi();
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		statoDelGioco.assegnaCasualmentePrimoGiocatore();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
		statoDelGioco.getGiocatori().get(i).setPersonaggio(mazziere.popPersonaggio());
		}
	}
	
	
	
	
	
	
	
	
	private void faseDistribuzioneCarte() throws CarteFiniteException{
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.DISTRIBUZIONE_CARTE);
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
			aggiornaIClient();
		}
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
			aggiornaIClient();
		}
	}
	
	private void primaFaseScommesse() throws UnGiocatoreRimastoException {  //I GIOCATORI POSSONO PERDERE DURANTE QUESTA FASE
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ORARIA);
			aggiornaIClient();
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			Scommessa scommessa=null;  
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			
			
			//chiedi scommessa al controlloreserver object deve essere istanceof Scommessa 
			while (statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<scommessa.getDanariScommessi()*100);//chiedi di nuovo e riprova;
			statoDelGioco.aggiungiScommesseFattePrimaFase(scommessa);
			aggiornaIClient();
			
			
			
		}
		
	}
	
	private void truccaCorsa(){
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA);
		aggiornaIClient();
		for (int i=0; i<statoDelGioco.getGiocatori().size();i++){
			PosizionaCarta posizionaCarta=null;
			//chiedi carta azione e la scuderiaAssociata al server
			statoDelGioco=ControlloreOperativo.posizionaCartaAzione(statoDelGioco, posizionaCarta);
			aggiornaIClient();
		}
	}
	
	private void secondaFaseScommesse(){ 
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ANTIORARIA);
		aggiornaIClient();
		for (int i=statoDelGioco.getGiocatori().size()-1;i>=0;i--){
			Scommessa scommessa=null;
			//chiedi scommessa al controlloreserver object deve essere istanceof Scommessa 
			if (scommessa==null) continue;
			while (statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<scommessa.getDanariScommessi()*100);//chiedi di nuovo e riprova;
			statoDelGioco.aggiungiScommesseFatteSecondaFase(scommessa);
			aggiornaIClient();
		}
		
	}
	
	public void faseCorsa() throws CarteFiniteException{
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_SCOPRICARTAAZIONE);
		statoDelGioco=ControlloreOperativo.eliminaCarte(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGioco);
		aggiornaIClient();
		statoDelGioco=ControlloreOperativo.applicaEffettiQUOTAZIONEPreCorsa(statoDelGioco);
		aggiornaIClient();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_CORSA);
		aggiornaIClient();
		statoDelGioco=ControlloreOperativo.partenza(statoDelGioco, mazziere);
		aggiornaIClient();
		statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere);
		aggiornaIClient();
		while (statoDelGioco.getClassifica().size()!=statoDelGioco.getCorsie().size()){
			statoDelGioco=ControlloreOperativo.movimento(statoDelGioco, mazziere);
			aggiornaIClient();
			statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere);
			aggiornaIClient();
		}
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_PAGAMENTI_NUOVE_QUOTAZIONI);//I GIOCATORI POSSONO PERDERE DURANTE QUESTA FASE
		
		
	}
	
	
		
	
	
	public void inizia() throws CarteFiniteException, AttesaUtentiFallitaException, UnGiocatoreRimastoException {
		controlloreRete.accettaUtenti(statoDelGioco.getGiocatori());
		preparazione();
		statoDelGioco.addNumTurno();
		aggiornaIClient();
		while(statoDelGioco.getNumTurno()!=statoDelGioco.getNumTurniTotali()){
			faseDistribuzioneCarte();
			primaFaseScommesse();
			truccaCorsa();
			secondaFaseScommesse();
			faseCorsa();
		
		
		statoDelGioco.addNumTurno();
		aggiornaIClient();
		}
		
	}
	
	public int getTurniTotali(){
		return numTurniTotali;
	}
	

}

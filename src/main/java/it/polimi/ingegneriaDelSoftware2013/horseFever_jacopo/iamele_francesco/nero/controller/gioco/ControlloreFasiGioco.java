package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.UnGiocatoreRimastoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		List<Scuderia> scuderieTemp;
		scuderieTemp = statoDelGioco.getCorsie();
		Collections.shuffle(scuderieTemp);
		for(int i=scuderieTemp.size()-1; i>=0;i--){
			scuderieTemp.get(i).assegnaQuotazione(i+2);
		}
		statoDelGioco.setCorsie(scuderieTemp);
		mazziere.mischiaPersonaggi();
		for (int i=0; i<numGiocatori;i++){
			Personaggio cartaPersonaggio=mazziere.popPersonaggio();
			int count=0;
			while (statoDelGioco.getCorsie().get(count).getQuotazione()!=cartaPersonaggio.getScuderiaAssociata()){
				count++;
				}
			List<Scuderia> scuderiaDaAssociare=new ArrayList<Scuderia>();
			scuderiaDaAssociare.add(statoDelGioco.getCorsie().get(count));
			statoDelGioco.aggiungiGiocatore(new Giocatore(cartaPersonaggio.getDanari(),1,scuderiaDaAssociare,cartaPersonaggio));
			
		}
		
	}
	
	private void aggiornaTuttiIClient(){
		controlloreRete.aggiornaUtenti(statoDelGioco);
	}
	
	
	

	private void preparazione() throws CarteFiniteException{
		statoDelGioco.setInizio(true);
		
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		statoDelGioco.assegnaCasualmentePrimoGiocatore();
		
	}
	
	
	
	
	
	private void faseEliminazioneGiocatore(){
		List<Giocatore>giocatoriDaEliminare=new ArrayList<Giocatore>();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			if(statoDelGioco.getGiocatori().get(i).getDanari()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100){
				do{
					statoDelGioco.getGiocatori().get(i).removePuntiVittoria(2);
				}while ((statoDelGioco.getGiocatori().get(i).getDanari()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100)&&(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()>0));
				if(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<=0){
					giocatoriDaEliminare.add(statoDelGioco.getGiocatori().get(i));
				}
			}
		}
		statoDelGioco.getGiocatori().removeAll(giocatoriDaEliminare);
		for(int i=0;i<giocatoriDaEliminare.size();i++){
			if(giocatoriDaEliminare.get(i).isPrimoGiocatore()){
				statoDelGioco.assegnaCasualmentePrimoGiocatore();
			}
		}
		aggiornaTuttiIClient();
	}
	
	
	
	private void faseDistribuzioneCarte() throws CarteFiniteException{
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.DISTRIBUZIONE_CARTE);
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		aggiornaTuttiIClient();
	}
	
	private void primaFaseScommesse() throws UnGiocatoreRimastoException {  //I GIOCATORI POSSONO PERDERE DURANTE QUESTA FASE
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ORARIA);			
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			Scommessa scommessa;
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			aggiornaTuttiIClient();
			scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
			int count=0;
			boolean segnaliniScommessaFiniti=false;
			while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore())count++;
			if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0)
				segnaliniScommessaFiniti=true;
			else 
				statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
			while ((scommessa.getDanariScommessi()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100)||(segnaliniScommessaFiniti));{
				controlloreRete.nega(statoDelGioco.getGiocatoreDiTurno());
				scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
				count=0;
				segnaliniScommessaFiniti=false;
				while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore())count++;
				if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0)
					segnaliniScommessaFiniti=true;
				else 
					statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
			}
			controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
			statoDelGioco.aggiungiScommesseFattePrimaFase(scommessa);
			statoDelGioco.getGiocatori().get(i).addScommessa(scommessa);
			aggiornaTuttiIClient();
			
			
			
		}
		
	}
	
	private void truccaCorsa(){
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA);
		aggiornaTuttiIClient();
		for (int i=0; i<statoDelGioco.getGiocatori().size();i++){
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			PosizionaCarta posizionaCarta=null;
			posizionaCarta=controlloreRete.riceviPosizionaCarta(statoDelGioco.getGiocatoreDiTurno());
			controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
				statoDelGioco=ControlloreOperativo.posizionaCartaAzione(statoDelGioco, posizionaCarta);
			aggiornaTuttiIClient();
		}
	}
	
	private void secondaFaseScommesse(){ 
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ANTIORARIA);
		aggiornaTuttiIClient();
		for (int i=statoDelGioco.getGiocatori().size()-1;i>=0;i--){
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			Scommessa scommessa=null;
			scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
			if (scommessa.getDanariScommessi()==0) continue;
			else{
				int count=0;
				boolean segnaliniScommessaFiniti=false;
				boolean PiazzatoEVincente=false;
				while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore())count++;
				if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0)
					segnaliniScommessaFiniti=true;
				else 
					statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
				while ((scommessa.getDanariScommessi()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100)||(segnaliniScommessaFiniti)||(PiazzatoEVincente));{
					controlloreRete.nega(statoDelGioco.getGiocatoreDiTurno());
					scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
					count=0;
					segnaliniScommessaFiniti=false;
					while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore())count++;
					if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0)
						segnaliniScommessaFiniti=true;
					else if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getScuderia()==scommessa.getScuderia()){
						if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getTipoScommessa()==scommessa.getTipoScommessa()){
							PiazzatoEVincente=true;
						}
						else 
							statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
					}
					else 
						statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
				}
				controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
			}
			statoDelGioco.aggiungiScommesseFatteSecondaFase(scommessa);
			statoDelGioco.getGiocatori().get(i).addScommessa(scommessa);
			aggiornaTuttiIClient();
		}
		
	}
	
	public void faseCorsa() throws CarteFiniteException{
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_SCOPRICARTAAZIONE);
		statoDelGioco=ControlloreOperativo.eliminaCarte(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGioco);
		aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.applicaEffettiQUOTAZIONEPreCorsa(statoDelGioco);
		aggiornaTuttiIClient();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_CORSA);
		aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.partenza(statoDelGioco, mazziere);
		aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere);
		aggiornaTuttiIClient();
		while (statoDelGioco.getClassifica().size()!=statoDelGioco.getCorsie().size()){
			statoDelGioco=ControlloreOperativo.movimento(statoDelGioco, mazziere);
			aggiornaTuttiIClient();
			statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere);
			aggiornaTuttiIClient();
		}
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_PAGAMENTI_NUOVE_QUOTAZIONI);
		
		
		
	}
	
	
		
	
	
	public void inizia() throws CarteFiniteException, AttesaUtentiFallitaException, UnGiocatoreRimastoException {
		controlloreRete.accettaUtenti(statoDelGioco.getGiocatori());
		preparazione();
		statoDelGioco.addNumTurno();
		
		while(statoDelGioco.getNumTurno()!=statoDelGioco.getNumTurniTotali()){
			faseDistribuzioneCarte();
			faseEliminazioneGiocatore();
			primaFaseScommesse();
			truccaCorsa();
			secondaFaseScommesse();
			faseCorsa();
		
		
		statoDelGioco.addNumTurno();
		aggiornaTuttiIClient();
		}
		
	}
	
	public int getTurniTotali(){
		return numTurniTotali;
	}
	

}

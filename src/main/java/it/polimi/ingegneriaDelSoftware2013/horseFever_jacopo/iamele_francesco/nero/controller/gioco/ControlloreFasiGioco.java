package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
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
	private final int numTurniTotali;
	
	public ControlloreReteServer getControlloreRete() {
		return controlloreRete;
	}

	/**
	 * Questo � il construttore di ControlloreFasiGioco;
	 * Vengono inizializzati il mazziere e il controlloreRete,
	 * Viene assegnato il valore numTurniTotali e segnalini scommesse
	 * in base al numero di giocatori,
	 * Vengono aggiunte le corsie(scuderie),
	 * Vengono assegnate casualmente le quotazioni,
	 * Vengono creati i giocatori con associato una carta personaggio.
	 * @param numGiocatori
	 * @throws NumErratoGiocatoriException viene lanciata se numGiocatori vale o 1 o piu' di 6
	 * @throws CarteFiniteException
	 * @throws IOException
	 */
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
	
	/**
	 * questo metodo elimina 2 punti vittoria al giocatore che non pu� scommettere il valore minimo
	 * fino a quando non pu� farlo.
	 * Se i suoi punti vittoria finiscono, viene eliminato.
	 */
	private void faseEliminazioneGiocatore(){
		List<Giocatore>giocatoriDaEliminare=new ArrayList<Giocatore>();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			if(statoDelGioco.getGiocatori().get(i).getDanari()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100){
				do{
					statoDelGioco.getGiocatori().get(i).removePuntiVittoria(2);
					if(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<=0){
						break;
					}
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
		aggiornaTuttiIClient(); //secondo aggiornamento
	}
	
	
	private void faseDistribuzioneCarte() throws CarteFiniteException{
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.DISTRIBUZIONE_CARTE);
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		aggiornaTuttiIClient(); //primo aggiornamento
	}
	
	/**
	 * Questo metodo rappresenta la prima fase scommesse.
	 * Dato che i giocatori che non possono fare la scommessa minima sono gi� stati
	 * eliminati so che tutti i rimanenti la possano fare.
	 * Se mi arriva una scommessa con valore pi� basso della scommessa minima la richiedo
	 * finch� l'utente interessato "sbaglia" valore.
	 * 
	 */
	private void primaFaseScommesse() { 
			statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ORARIA);
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			Scommessa scommessa;
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			aggiornaTuttiIClient();
			scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
			int count=0;
			boolean segnaliniScommessaFiniti=false;
			while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
				count++;
			}
			if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0){
				segnaliniScommessaFiniti=true;
				}
			else{
				statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
			}
			while ((scommessa.getDanariScommessi()<(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100))||(segnaliniScommessaFiniti)){
				controlloreRete.nega(statoDelGioco.getGiocatoreDiTurno());
				scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
				count=0;
				segnaliniScommessaFiniti=false;
				while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
					count++;
				}
				if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0){
					segnaliniScommessaFiniti=true;
				}
				else{
					statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
			}	}
			controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
			statoDelGioco.aggiungiScommesseFattePrimaFase(scommessa);
			statoDelGioco.getGiocatori().get(i).addScommessa(scommessa);
		}	
	}
	
	/**
	 * In questo metodo viene chiesto a tutti gli utenti di posizionare una propria carta azione sulla scuderia
	 * che vogliono influenzare fino a quando non le hanno posizionate tutte
	 */
	private void truccaCorsa(){
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA);
		for (int i=0; i<statoDelGioco.getGiocatori().size();i++){
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			aggiornaTuttiIClient();
			PosizionaCarta posizionaCarta=null;
			posizionaCarta=controlloreRete.riceviPosizionaCarta(statoDelGioco.getGiocatoreDiTurno());
			controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
			statoDelGioco=ControlloreOperativo.posizionaCartaAzione(statoDelGioco, posizionaCarta);
		}
	}
	
	/**
	 * Molto simile alla prima fase, questo metodo chiede ai giocatori se vogliono fare la seconda scommessa;
	 * I giocatori possono decidere di saltare questa fase ma se scommettono non possono scommettere sulla stessa scuderia
	 * della scommessa precedente
	 */
	private void secondaFaseScommesse(){ 
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ANTIORARIA);
		for (int i=statoDelGioco.getGiocatori().size()-1;i>=0;i--){
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			System.out.println("Aggiorno client");
			aggiornaTuttiIClient();
			Scommessa scommessa=null;
			System.out.println("Attendo seconde scommesse.");
			scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
			System.out.println("Ricevuta scommessa: "+scommessa);
			if (scommessa.getDanariScommessi()==0){
				System.out.println("Giocatore non vuole scommettere.");
				controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
				continue;
			}
			else{
				int count=0;
				boolean segnaliniScommessaFiniti=false;
				boolean PiazzatoEVincente=false;
				while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
					count++;
				}
				if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0){
					segnaliniScommessaFiniti=true;
				}
				else if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getScuderia()==scommessa.getScuderia()){
					if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getTipoScommessa()==scommessa.getTipoScommessa()){
						PiazzatoEVincente=true;
					}
					else{ 
						statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
					}
				}
				else{ 
					statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
				}
				while ((scommessa.getDanariScommessi()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100)||(segnaliniScommessaFiniti)||(PiazzatoEVincente)){
					controlloreRete.nega(statoDelGioco.getGiocatoreDiTurno());
					scommessa=controlloreRete.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
					count=0;
					segnaliniScommessaFiniti=false;
					while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
						count++;
					}
					if(statoDelGioco.getCorsie().get(count).getScommesseDisponibili()==0){
						segnaliniScommessaFiniti=true;
					}
					else if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getScuderia()==scommessa.getScuderia()){
						if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(0).getTipoScommessa()==scommessa.getTipoScommessa()){
							PiazzatoEVincente=true;
						}
						else{ 
							statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
						}
					}
					else{ 
						statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
					}
				}
				controlloreRete.conferma(statoDelGioco.getGiocatoreDiTurno());
			}
			statoDelGioco.aggiungiScommesseFatteSecondaFase(scommessa);
			statoDelGioco.getGiocatori().get(i).addScommessa(scommessa);
		}
	}
	
	/**
	 * Questo metodo rappresenta la fase corsa;
	 * Essendo molto complessa e articolata questo metodo chiama metodi di supporto 
	 * definiti nella classe ControlloreOperativo
	 * @throws CarteFiniteException
	 */
	private void faseCorsa() throws CarteFiniteException{
		
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_SCOPRICARTAAZIONE); //il controllore lato client scoprir� tutte le carte
		statoDelGioco=ControlloreOperativo.eliminaCarte(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiQUOTAZIONEPreCorsa(statoDelGioco);
		aggiornaTuttiIClient();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_CORSA);
		//aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.partenza(statoDelGioco, mazziere);
		//aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere,controlloreRete);
		//aggiornaTuttiIClient();
		while (statoDelGioco.getClassifica().size()!=statoDelGioco.getCorsie().size()){
			statoDelGioco=ControlloreOperativo.movimento(statoDelGioco, mazziere,controlloreRete);
			//aggiornaTuttiIClient();
			statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere,controlloreRete);
			//aggiornaTuttiIClient();
		}
		
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_PAGAMENTI_NUOVE_QUOTAZIONI);
		aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.pagamenti(statoDelGioco);
		aggiornaTuttiIClient();
		statoDelGioco=ControlloreOperativo.nuoveQuotazioni(statoDelGioco);
		aggiornaTuttiIClient();
	}
	
	/**
	 * in questo metodo vengono riassegnate al mazziere e mischiate tutte le carte del gioco
	 * (tranne i personaggi)
	 * vengono anche cancellate le liste scommesse e carte azioni in gioco
	 * @throws IOException
	 */
	private void faseFineTurno() throws IOException {
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.FINETURNO);
		mazziere.resetCarteAzione();
		mazziere.resetCarteMovimento();
		mazziere.mischiaCarteAzione();
		mazziere.mischiaCarteMovimento();
		statoDelGioco.aggiornaPrimoGiocatore();
		statoDelGioco.getClassifica().clear();
		statoDelGioco.getScommesseFattePrimaFase().clear();
		statoDelGioco.getScommesseFatteSecondaFase().clear();
		for(int i=0;i<statoDelGioco.getCorsie().size();i++){
			statoDelGioco.getCorsie().get(i).resetScommesseDisponibili();
			statoDelGioco.getCorsie().get(i).resetPosizione();
			statoDelGioco.getCorsie().get(i).getCarteAzione().clear();
		}
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			statoDelGioco.getGiocatori().get(i).getCarteAzione().clear();
			statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().clear();
		}
		aggiornaTuttiIClient();
	}
	
	private void faseFineDelGioco() {
		int danariMassimi=ControlloreOperativo.danariMassimi(statoDelGioco);
		int puntiVittoriaMassimi=ControlloreOperativo.puntiVittoriaMassimi(statoDelGioco);
		List<Giocatore> giocatoriStessiPV =new ArrayList<Giocatore>();
		List<Giocatore> giocatoriAncheStessiDanari=new ArrayList<Giocatore>();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			if(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()==puntiVittoriaMassimi){
				giocatoriStessiPV.add(statoDelGioco.getGiocatori().get(i));
			}else{
				continue;
			}
		}
		if(giocatoriStessiPV.size()==1){
			vittoria(giocatoriStessiPV);
		}else{
			for(int i=0;i<giocatoriStessiPV.size();i++){
				if(giocatoriStessiPV.get(i).getDanari()==danariMassimi){
					giocatoriAncheStessiDanari.add(giocatoriStessiPV.get(i));
				}else{
					continue;
				}
			}
			if(giocatoriAncheStessiDanari.size()==1){
				vittoria(giocatoriAncheStessiDanari);
			}
			else{
				Collections.shuffle(giocatoriAncheStessiDanari);//a questo punto assegno casualmente la vittoria
				vittoria(giocatoriAncheStessiDanari);
			}
		}	
	}
	
	/**
	 * Questo metodo individua il vincitore, setta la fase di gioco in VITTORIA
	 * e lo inserisce in giocatoreDiTurno
	 * @param giocatoriCandidati
	 */
	private void vittoria(List<Giocatore> giocatoriCandidati){
		statoDelGioco.setInizio(false);
		statoDelGioco.setGiocatoreDiTurno(giocatoriCandidati.get(0)); // qui c'� il giocatore vincitore
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.VITTORIA);
		aggiornaTuttiIClient();
		
		
	}

	public void inizia() throws CarteFiniteException, AttesaUtentiFallitaException, IOException {
		controlloreRete.accettaUtenti(statoDelGioco.getGiocatori());
		preparazione();
		statoDelGioco.addNumTurno();
		while(statoDelGioco.getNumTurno()!=statoDelGioco.getNumTurniTotali()){
			aggiornaTuttiIClient();
			faseDistribuzioneCarte();
			faseEliminazioneGiocatore();
			if(statoDelGioco.getGiocatori().size()==1)break;//se � rimasto un solo giocatore salta alla faseFineDelGioco dove gli verr� attribuita la vittoria
			primaFaseScommesse();
			truccaCorsa();
			secondaFaseScommesse();
			faseCorsa();
			faseFineTurno();
			statoDelGioco.addNumTurno();
			aggiornaTuttiIClient();
		}
		faseFineDelGioco();
	}
	
	public int getTurniTotali(){
		return numTurniTotali;
	}
	
	public StatoDelGioco getStatoDelGioco(){
		return statoDelGioco;
	}
	public Mazziere getMazziere(){
		return mazziere;
	}
}
	
	

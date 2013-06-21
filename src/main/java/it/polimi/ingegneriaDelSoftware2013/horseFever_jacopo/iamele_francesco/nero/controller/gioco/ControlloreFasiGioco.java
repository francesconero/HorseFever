package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenti;
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
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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
	private ControlloreUtenti controlloreUtenti;
	private final int numTurniTotali;
	private AtomicBoolean chiuso = new AtomicBoolean(false);

	public ControlloreUtenti getControlloreRete() {
		return controlloreUtenti;
	}

	/**
	 * Questo e' il construttore di ControlloreFasiGioco;
	 * Vengono inizializzati il mazziere e il controlloreRete,
	 * Viene assegnato il valore numTurniTotali e segnalini scommesse
	 * in base al numero di giocatori,
	 * Vengono aggiunte le corsie(scuderie),
	 * Vengono assegnate casualmente le quotazioni,
	 * Vengono creati i giocatori con associato una carta personaggio.
	 * @param numGiocatori
	 * @param controlloreUtenti 
	 * @throws NumErratoGiocatoriException viene lanciata se numGiocatori vale o 1 o piu' di 6
	 */
	public ControlloreFasiGioco(int numGiocatori, Mazziere mazziere, ControlloreUtenti controlloreUtenti) {
		this.mazziere = mazziere;
		this.controlloreUtenti = controlloreUtenti;
		int segnaliniScommesse;
		switch (numGiocatori){
			case 2: {numTurniTotali=6; segnaliniScommesse=1;break;}
			case 3: {numTurniTotali=6; segnaliniScommesse=2;break;}
			case 4: {numTurniTotali=4; segnaliniScommesse=3;break;}
			case 5: {numTurniTotali=5; segnaliniScommesse=4;break;}
			case 6: {numTurniTotali=6; segnaliniScommesse=4;break;}
			default : {throw new NumErratoGiocatoriException();}
		}
		statoDelGioco=new StatoDelGioco(numTurniTotali, mazziere);
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.NERO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.BLU, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.VERDE, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.ROSSO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.GIALLO, segnaliniScommesse));
		statoDelGioco.aggiungiCorsia(new Scuderia(Colore.BIANCO, segnaliniScommesse));
		List<Scuderia> scuderieTemp;
		scuderieTemp = statoDelGioco.getCorsie();
		mazziere.shuffle(scuderieTemp);
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
		controlloreUtenti.aggiornaUtenti(statoDelGioco);
	}

	private void preparazione() {
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.PREPARAZIONE);
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		statoDelGioco.assegnaCasualmentePrimoGiocatore();		
	}

	/**
	 * questo metodo elimina 2 punti vittoria al giocatore che non puo' scommettere il valore minimo
	 * fino a quando non puo' farlo.
	 * Se i suoi punti vittoria finiscono, viene eliminato.
	 */
	private void faseEliminazioneGiocatore() {
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.ELIMINAZIONE_GIOCATORI);
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
					controlloreUtenti.giocatoreEliminato(statoDelGioco.getGiocatori().get(i));
				}
			}
		}
		statoDelGioco.getGiocatori().removeAll(giocatoriDaEliminare);
		if(statoDelGioco.getGiocatori().size()==0){
			return;
		}
		for(int i=0;i<giocatoriDaEliminare.size();i++){
			if(giocatoriDaEliminare.get(i).isPrimoGiocatore()){
				statoDelGioco.assegnaCasualmentePrimoGiocatore();
			}
		}
	}


	private void faseDistribuzioneCarte() {
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.DISTRIBUZIONE_CARTE);
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}
		for (Giocatore g: statoDelGioco.getGiocatori()){
			g.setCarteAzione(mazziere.popCartaAzione());
		}		
	}

	/**
	 * Questo metodo rappresenta la prima fase scommesse.
	 * Dato che i giocatori che non possono fare la scommessa minima sono gia' stati
	 * eliminati so che tutti i rimanenti la possano fare.
	 * Se mi arriva una scommessa con valore piu' basso della scommessa minima la richiedo
	 * finche' l'utente interessato "sbaglia" valore.
	 * 
	 */
	private void primaFaseScommesse() { 
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ORARIA);
		for (int i=0;i<statoDelGioco.getGiocatori().size();i++){
			Scommessa scommessa;
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			aggiornaTuttiIClient();
			scommessa=controlloreUtenti.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
			int count=0;
			while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
				count++;
			}
			boolean valida=false;
			String messaggioErrore = controllaScommessa(scommessa, statoDelGioco.getGiocatoreDiTurno(), true);
			if(messaggioErrore.equals("ok")){
				valida=true;
			}
			while (!valida){
				controlloreUtenti.nega(statoDelGioco.getGiocatoreDiTurno());
				controlloreUtenti.avverti(statoDelGioco.getGiocatoreDiTurno(), messaggioErrore);
				scommessa=controlloreUtenti.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
				messaggioErrore = controllaScommessa(scommessa, statoDelGioco.getGiocatoreDiTurno(), true);
				if(messaggioErrore.equals("ok")){
					valida=true;
				}
				count=0;
				while(scommessa.getScuderia()!=statoDelGioco.getCorsie().get(count).getColore()){
					count++;
				}
			}
			statoDelGioco.getCorsie().get(count).removeScommesseDisponibili(1);
			statoDelGioco.getGiocatori().get(i).removeDanari(scommessa.getDanariScommessi());
			controlloreUtenti.conferma(statoDelGioco.getGiocatoreDiTurno());
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
		while(statoDelGioco.getGiocatoreDiTurno().getCarteAzione().size()!=0){
			for (int i=0; i<statoDelGioco.getGiocatori().size();i++){
				statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
				aggiornaTuttiIClient();
				PosizionaCarta posizionaCarta=null;
				posizionaCarta=controlloreUtenti.riceviPosizionaCarta(statoDelGioco.getGiocatoreDiTurno());
				controlloreUtenti.conferma(statoDelGioco.getGiocatoreDiTurno());
				statoDelGioco=ControlloreOperativo.posizionaCartaAzione(statoDelGioco, posizionaCarta);
				statoDelGioco.getGiocatoreDiTurno().getCarteAzione().remove(posizionaCarta.getCartaDaPosizionare());	
			}
		}
	}

	/**
	 * Molto simile alla prima fase, questo metodo chiede ai giocatori in senso antiorario se vogliono fare la seconda scommessa;
	 * I giocatori possono decidere di saltare questa fase ma se scommettono non possono scommettere sulla stessa scuderia
	 * della scommessa precedente
	 */
	private void secondaFaseScommesse() { 
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ANTIORARIA);
		for (int i=statoDelGioco.getGiocatori().size()-1;i>=0;i--){
			statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(i));
			aggiornaTuttiIClient();
			Scommessa scommessa=null;
			scommessa=controlloreUtenti.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());

			boolean passato = scommessa.getDanariScommessi()==0;
			boolean valida=false;
			String messaggioErrore = controllaScommessa(scommessa, statoDelGioco.getGiocatoreDiTurno(), false);
			if(messaggioErrore.equals("ok")){
				valida=true;
			}
			while(!(valida||passato)){
				controlloreUtenti.nega(statoDelGioco.getGiocatoreDiTurno());
				controlloreUtenti.avverti(statoDelGioco.getGiocatoreDiTurno(), messaggioErrore);
				scommessa=controlloreUtenti.riceviScommessa(statoDelGioco.getGiocatoreDiTurno());
				passato = scommessa.getDanariScommessi()==0;
				messaggioErrore = controllaScommessa(scommessa, statoDelGioco.getGiocatoreDiTurno(), false);
				if(messaggioErrore.equals("ok")){
					valida=true;
				}
			}

			if(passato){
				;
			} else {
				statoDelGioco.getGiocatoreDiTurno().removeDanari(scommessa.getDanariScommessi());
				statoDelGioco.getGiocatoreDiTurno().addScommessa(scommessa);
				statoDelGioco.getScommesseFatteSecondaFase().add(scommessa);
				statoDelGioco.getScuderiaDalColore(scommessa.getScuderia()).removeScommesseDisponibili(1);
			}
			controlloreUtenti.conferma(statoDelGioco.getGiocatoreDiTurno());
		}
	}

	private String controllaScommessa(Scommessa scommessa, Giocatore giocatore, boolean prima){
		if(prima){
			if(scommessa.getDanariScommessi()==0){
				return "Non puoi Scommettere 0 Danari!!!";
			}
		}

		if(scommessa.getDanariScommessi()<giocatore.getPuntiVittoria()*100){
			return ("La tua scommessa non rispetta la scommessa minima:"+giocatore.getPuntiVittoria()*100);
		}
		if(scommessa.getDanariScommessi()>giocatore.getDanari()){
			return ("Non hai tutti quei soldi!!!");
		}
		if(statoDelGioco.getScuderiaDalColore(scommessa.getScuderia()).getScommesseDisponibili()<1){
			return "Spiacente, scommesse esaurite su quella scuderia!";
		}

		if(!prima){
			for(Scommessa sE : giocatore.getScommesseEffettuate()){
				if(sE.getScuderia().equals(scommessa.getScuderia())){
					if(sE.getTipoScommessa().equals(scommessa.getTipoScommessa())){
						return ("Hai gi� scommesso "+scommessa.getTipoScommessa()+" su quella scuderia!");
					}
				}
			}
		}
		return "ok";		
	}

	/**
	 * Questo metodo rappresenta la fase corsa;
	 * Essendo molto complessa e articolata questo metodo chiama metodi di supporto 
	 * definiti nella classe ControlloreOperativo
	 */
	private void faseCorsa() {		
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_SCOPRICARTAAZIONE); //il controllore lato client scoprira' tutte le carte
		statoDelGioco=ControlloreOperativo.eliminaCarte(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGioco);
		statoDelGioco=ControlloreOperativo.applicaEffettiQUOTAZIONEPreCorsa(statoDelGioco);
		aggiornaTuttiIClient();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_CORSA);
		statoDelGioco=ControlloreOperativo.partenza(statoDelGioco, mazziere);
		statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere,controlloreUtenti);
		while (statoDelGioco.getClassifica().size()!=statoDelGioco.getCorsie().size()){
			statoDelGioco=ControlloreOperativo.movimento(statoDelGioco, mazziere,controlloreUtenti);
			statoDelGioco=ControlloreOperativo.sprint(statoDelGioco, mazziere,controlloreUtenti);
		}
		List<MossaCorsa> mosseCorsa =  ControlloreOperativo.getMosseCorsa();
		mosseCorsa.add(new FineGara("E' finita la gara!"));
		controlloreUtenti.aggiornaUtenti(statoDelGioco,mosseCorsa);
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_PAGAMENTI_NUOVE_QUOTAZIONI);
		statoDelGioco=ControlloreOperativo.pagamenti(statoDelGioco);
		statoDelGioco=ControlloreOperativo.nuoveQuotazioni(statoDelGioco);
	}

	/**
	 * in questo metodo vengono riassegnate al mazziere e mischiate tutte le carte del gioco
	 * (tranne i personaggi)
	 * vengono anche cancellate le liste scommesse e carte azioni in gioco
	 * @throws IOException
	 */
	private void faseFineTurno() {
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
			statoDelGioco.getCorsie().get(i).setArrivato(false);

		}
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			statoDelGioco.getGiocatori().get(i).getCarteAzione().clear();
			statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().clear();
		}
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
		statoDelGioco.setGiocatoreDiTurno(giocatoriCandidati.get(0)); // qui c'e' il giocatore vincitore
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.VITTORIA);		
	}

	/**
	 *Questo metodo viene chiamato se TUTTI i giocatori sono stati eliminati;
	 *setta la fase di gioco in VITTORIA
	 * e setta a null il giocatoreDiTurno 
	 */
	private void sconfitta() {
		statoDelGioco.setGiocatoreDiTurno(null);
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.VITTORIA);	

	}
	public void inizia() {
		controlloreUtenti.accettaUtenti(statoDelGioco.getGiocatori());
		preparazione();
		aggiornaTuttiIClient();
		statoDelGioco.addNumTurno();
		while(statoDelGioco.getNumTurno()<=statoDelGioco.getNumTurniTotali()){
			faseEliminazioneGiocatore();
			aggiornaTuttiIClient();
			if(statoDelGioco.getGiocatori().size()==0){
				break;
			}
			if(statoDelGioco.getGiocatori().size()==1){
				break;//se e' rimasto un solo giocatore salta alla faseFineDelGioco dove gli verra'attribuita la vittoria
			}
			faseDistribuzioneCarte();
			aggiornaTuttiIClient();
			primaFaseScommesse();
			truccaCorsa();
			secondaFaseScommesse();
			faseCorsa();
			faseFineTurno();
			aggiornaTuttiIClient();
			statoDelGioco.addNumTurno();
		}
		if(statoDelGioco.getGiocatori().size()==0){
			sconfitta();			
		}else{
			faseFineDelGioco();
		}
		aggiornaTuttiIClient();
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

	public void chiudi() {
			controlloreUtenti.fine();
	}

	public AtomicBoolean isChiuso() {
		return chiuso;
	}
}



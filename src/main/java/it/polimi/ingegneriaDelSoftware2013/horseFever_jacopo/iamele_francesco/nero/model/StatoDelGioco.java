package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class StatoDelGioco  {
	private boolean inizio=false;
	private int numTurno=0;
	private Giocatore giocatoreDiTurno=null;
	private TipoFaseGiocoFamily tipoFaseGiocoFamily;
	private List<Giocatore> giocatori = new ArrayList<Giocatore>();	
	private List<Scuderia> corsie=new ArrayList<Scuderia>();
	private List<Scuderia> classifica=new ArrayList<Scuderia>();
	private List<Scommessa> scommesseFattePrimaFase=new ArrayList<Scommessa>();
	private List<Scommessa> scommesseFatteSecondaFase=new ArrayList<Scommessa>();
	private final int numTurniTotali;
	private Giocatore primoGiocatore;
	private final Mazziere mazziere;

	public Mazziere getMazziere() {
		return mazziere;
	}

	public StatoDelGioco(int numTurniTotali, Mazziere mazziere){
		this.numTurniTotali=numTurniTotali;
		this.mazziere = mazziere;
	}

	public boolean isInizio() {
		return inizio;
	}
	public void setInizio(boolean inizio) {
		this.inizio = inizio;	
	}

	public TipoFaseGiocoFamily getTipoFaseGiocoFamily() {
		return tipoFaseGiocoFamily;
	}
	public void setTipoFaseGiocoFamily(TipoFaseGiocoFamily tipoFaseGiocoFamily) {
		this.tipoFaseGiocoFamily = tipoFaseGiocoFamily;
	}


	public List<Giocatore> getGiocatori() {
		return giocatori;
	}
	public void aggiungiGiocatore(Giocatore giocatoreDaAggiungere){
		giocatori.add(giocatoreDaAggiungere);
	}
	public void rimuoviGiocatore(int indirizzo){
		giocatori.remove(indirizzo);
	}


	public List<Scuderia> getCorsie() {
		return corsie;
	}
	public void aggiungiCorsia(Scuderia corsiaDaAggiungere){
		corsie.add(corsiaDaAggiungere);
	}
	public void rimuoviCorsia(int indirizzo){
		corsie.remove(indirizzo);
	}
	public void setCorsie(List<Scuderia> corsieDaSettare){
		this.corsie=corsieDaSettare;

	}


	public List<Scuderia> getClassifica() {
		return classifica;
	}

	public void addClassifica(Scuderia classifica) {
		this.classifica.add(classifica);
	}


	public int getNumTurno() {
		return numTurno;
	}
	public void setNumTurno(int numTurno) {
		this.numTurno = numTurno;
	}
	public void addNumTurno(){
		this.numTurno++;
	}


	public List<Scommessa> getScommesseFattePrimaFase() {
		return scommesseFattePrimaFase;
	}
	public void aggiungiScommesseFattePrimaFase(Scommessa scommessaDaAggiungere){
		scommesseFattePrimaFase.add(scommessaDaAggiungere);
	}
	public void rimuoviScommesseFattePrimaFase(int indirizzo){
		scommesseFattePrimaFase.remove(indirizzo);
	}


	public void setGiocatori(List<Giocatore> giocatori){
		this.giocatori=giocatori;
	}
	public Giocatore getGiocatoreDiTurno() {
		return giocatoreDiTurno;
	}
	public void setGiocatoreDiTurno(Giocatore giocatoreDiTurno) {
		if(this.giocatoreDiTurno == null){
			this.giocatoreDiTurno = giocatoreDiTurno;			
		} else {
			this.giocatoreDiTurno.setMioTurno(false);
			this.giocatoreDiTurno = giocatoreDiTurno;			
			this.giocatoreDiTurno.setMioTurno(true);
		}
	}
	public int getNumTurniTotali(){
		return numTurniTotali;
	}


	public List<Scommessa> getScommesseFatteSecondaFase() {
		return scommesseFatteSecondaFase;
	}
	public void setScommesseFatteSecondaFase(
			List<Scommessa> scommesseFatteSecondaFase) {
		this.scommesseFatteSecondaFase = scommesseFatteSecondaFase;
	}
	public void aggiungiScommesseFatteSecondaFase(Scommessa scommessaDaAggiungere){
		scommesseFatteSecondaFase.add(scommessaDaAggiungere);
	}
	public void rimuoviScommesseFatteSecondaFase(int indirizzo){
		scommesseFatteSecondaFase.remove(indirizzo);
	}	

	public void assegnaCasualmentePrimoGiocatore(){
		Random r= new Random();
		int s=r.nextInt(giocatori.size());
		giocatori.get(s).setPrimoGiocatore(true);
		giocatori=MetodiDiSupporto.creaListaOrdinata(giocatori, giocatori.get(s));
		primoGiocatore = giocatori.get(s);
		giocatoreDiTurno = giocatori.get(s);
	}

	public void aggiornaPrimoGiocatore(){
		giocatori.get(0).setPrimoGiocatore(false);
		giocatori.get(1).setPrimoGiocatore(true);
		primoGiocatore = giocatori.get(1);
		giocatori=MetodiDiSupporto.creaListaOrdinata(giocatori, giocatori.get(1));
	}
	
	public Scuderia getScuderiaDalColore(Colore colore){
		List<Scuderia> temp = new LinkedList<Scuderia>();
		for(Scuderia s : corsie){
			if(s.getColore().equals(colore)){
				temp.add(s);
			}
		}
		if(temp.size()==1){
			return temp.get(0);
		} else if(temp.size()<1) {
			throw new IllegalStateException("Nessuna scuderia trovata");
		} else {
			throw new IllegalStateException("Troppe scuderie con lo stesso colore: "+temp.size());
		}
	}

	public Giocatore getPrimoGiocatore() {
		return primoGiocatore;
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;

import java.util.*;

public class StatoDelGioco  {
	private boolean inizio=false;
	private TipoFaseGiocoFamily tipoFaseGiocoFamily;
	private List<Giocatore> giocatori=null;
	private List<Scuderia> corsie=null;
	private List<Quotazione> quotazioni=null;
	private int numTurno;
	private Giocatore primoGiocatore=null;
	private List<Scommessa> scommesseFattePrimaFase=null;
	private List<Scommessa> scommesseFatteSecondaFase=null;
    private Giocatore giocatoreDiTurno=null;
	private final int numTurniTotali;
	
	public StatoDelGioco(int numTurniTotali){
	this.numTurniTotali=numTurniTotali;
	giocatori=new ArrayList<Giocatore>();
	corsie=new ArrayList<Scuderia>();
	quotazioni=new ArrayList<Quotazione>();
	scommesseFattePrimaFase=new ArrayList<Scommessa>();
	scommesseFatteSecondaFase=new ArrayList<Scommessa>();
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
	
	
	public List<Quotazione> getQuotazioni() {
		return quotazioni;
	}
	public void aggiungiQuotazioni(Quotazione quotazioneDaAggiungere){
		quotazioni.add(quotazioneDaAggiungere);
	}
	public void rimuoviQuotazioni(int indirizzo){
		quotazioni.remove(indirizzo);
	}
	
	
	public int getNumTurno() {
		return numTurno;
	}
	public void setNumTurno(int numTurno) {
		this.numTurno = numTurno;
	}
	
	
	public Giocatore getPrimoGiocatore() {
		return primoGiocatore;
	}
	public void setPrimoGiocatore(Giocatore primoGiocatore) {
		this.primoGiocatore = primoGiocatore;
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
		this.giocatoreDiTurno = giocatoreDiTurno;
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
		
	}
	public void aggiornaPrimoGiocatore(){
		giocatori.get(0).setPrimoGiocatore(false);
		giocatori.get(1).setPrimoGiocatore(true);
		giocatori=MetodiDiSupporto.creaListaOrdinata(giocatori, giocatori.get(1));
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.util.*;
/***
 * Questa classe filtra lo stato del gioco di tutti gli
 * elementi che NON devono essere visti dai client.
 * Il Costruttore salva tutti i valori che devono essere inviati al client
 * Questa classe ha solo Getters
 * @author Jacopo
 *
 */

public class StatoDelGiocoView {
	private boolean inizio;
	private TipoFaseGiocoFamily tipoFaseGiocoFamily;
	private List<GiocatoreView> altriGiocatoriView;
	private List<Scuderia> corsie;
	private List<Scuderia> classifica;
	private int numTurno;
	private List<Scommessa> scommesseFatte;
	private GiocatoreView giocatoreDiTurno;
	private final int numTurniTotali;
	
	public StatoDelGiocoView(StatoDelGioco statoDaFiltrare, Giocatore clientReclamante)
	{
		this.inizio=statoDaFiltrare.isInizio();
		this.tipoFaseGiocoFamily=statoDaFiltrare.getTipoFaseGiocoFamily();
		this.corsie=statoDaFiltrare.getCorsie();
		this.classifica=statoDaFiltrare.getClassifica();
		this.numTurno=statoDaFiltrare.getNumTurno();
		this.numTurniTotali=statoDaFiltrare.getNumTurniTotali();
		altriGiocatoriView= new ArrayList<GiocatoreView>();
		oscuraGiocatori(statoDaFiltrare.getGiocatori(),clientReclamante);
	}
	
	private void oscuraGiocatori(List<Giocatore> giocatoriDaOscurare,Giocatore clientReclamante){
		
		for(int i=0; i<giocatoriDaOscurare.size();i++){
			if(giocatoriDaOscurare.get(i)==clientReclamante) continue; //da controllare
			altriGiocatoriView.add(new GiocatoreView(giocatoriDaOscurare.get(i)));
		}
	}
	
	
	public boolean isInizio() {
		return inizio;
	}
	
	public TipoFaseGiocoFamily getTipoFaseGiocoFamily() {
		return tipoFaseGiocoFamily;
	}
	
	public List<GiocatoreView> getGiocatori() {
		return altriGiocatoriView;
	}
	
	public List<Scuderia> getCorsie() {
		return corsie;
	}
	
	public List<Scuderia> getClassifica(){
		return classifica;
	}
	
	public int getNumTurno() {
		return numTurno;
	}
	
	public List<Scommessa> getScommesseFatte() {
		return scommesseFatte;
	}
	
	public GiocatoreView getGiocatoreDiTurno() {
		return giocatoreDiTurno;
	}
	
	public int getNumTurniTotali() {
		return numTurniTotali;
	}

}

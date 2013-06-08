package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Questa classe filtra lo stato del gioco di tutti gli elementi che NON devono
 * essere visti dai client. Il Costruttore salva tutti i valori che devono
 * essere inviati al client Questa classe ha solo Getters
 * 
 * @author Jacopo
 * 
 */

public class StatoDelGiocoView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1906104705205607270L;
	private final boolean inizio;
	private final TipoFaseGiocoFamily tipoFaseGiocoFamily;
	private final List<GiocatoreView> giocatoriView;
	private final List<Scuderia> corsie;
	private final List<Scuderia> classifica;
	private final int numTurno;
	private final GiocatoreView giocatoreDiTurno;
	private final GiocatoreView mioGiocatore;
	private final int numTurniTotali;
	private final Map<Giocatore, String> nomi;
	private final Map<Giocatore, Long> ids;

	public StatoDelGiocoView(StatoDelGioco statoDaFiltrare,
			Giocatore clientReclamante,
			Map<Giocatore, String> nomi, Map<Giocatore, Long> ids) {
		this.inizio = statoDaFiltrare.isInizio();
		this.tipoFaseGiocoFamily = statoDaFiltrare.getTipoFaseGiocoFamily();
		this.corsie = statoDaFiltrare.getCorsie();
		this.classifica = statoDaFiltrare.getClassifica();
		this.numTurno = statoDaFiltrare.getNumTurno();
		this.numTurniTotali = statoDaFiltrare.getNumTurniTotali();
		this.nomi = nomi;
		this.ids = ids;
		this.mioGiocatore = new GiocatoreView(clientReclamante,
				nomi.get(clientReclamante), ids.get(clientReclamante), false);
		Giocatore giocatoreDiTurnoIntero = statoDaFiltrare.getGiocatoreDiTurno();
		this.giocatoreDiTurno = new GiocatoreView(giocatoreDiTurnoIntero,  nomi
				.get(giocatoreDiTurnoIntero), ids.get(giocatoreDiTurnoIntero), true);
		giocatoriView = new ArrayList<GiocatoreView>();
		oscuraGiocatori(statoDaFiltrare.getGiocatori(), clientReclamante);
	}

	private void oscuraGiocatori(List<Giocatore> giocatoriDaOscurare,
			Giocatore clientReclamante) {

		for (int i = 0; i < giocatoriDaOscurare.size(); i++) {
			Giocatore giocatoreDaOscurare = giocatoriDaOscurare.get(i);
			giocatoriView.add(new GiocatoreView(giocatoreDaOscurare, nomi
					.get(giocatoreDaOscurare), ids.get(giocatoreDaOscurare), true));
		}
	}

	public boolean isInizio() {
		return inizio;
	}

	public TipoFaseGiocoFamily getTipoFaseGiocoFamily() {
		return tipoFaseGiocoFamily;
	}

	public List<GiocatoreView> getGiocatori() {
		return giocatoriView;
	}

	public List<Scuderia> getCorsie() {
		return corsie;
	}

	public List<Scuderia> getClassifica() {
		return classifica;
	}

	public int getNumTurno() {
		return numTurno;
	}

	public GiocatoreView getGiocatoreDiTurno() {
		return giocatoreDiTurno;
	}

	public int getNumTurniTotali() {
		return numTurniTotali;
	}

	public GiocatoreView getMioGiocatore() {
		return mioGiocatore;
	}

}

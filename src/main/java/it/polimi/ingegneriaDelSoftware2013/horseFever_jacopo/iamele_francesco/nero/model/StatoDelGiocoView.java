package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
	private final TipoFaseGiocoFamily tipoFaseGiocoFamily;
	private final List<GiocatoreView> giocatoriView;
	private final List<Scuderia> corsie;
	private final List<Scuderia> classifica;
	private final int numTurno;
	private final GiocatoreView primoGiocatore;
	private final GiocatoreView giocatoreDiTurno;
	private final GiocatoreView mioGiocatore;
	private final int numTurniTotali;
	private final Map<Giocatore, String> nomi;
	private final Map<Giocatore, Long> ids;
	private final List<MossaCorsa> mosseCorsa;

	public StatoDelGiocoView(StatoDelGioco statoDaFiltrare,
			Giocatore clientReclamante,
			Map<Giocatore, String> nomi, Map<Giocatore, Long> ids) {
		this.tipoFaseGiocoFamily = statoDaFiltrare.getTipoFaseGiocoFamily();
		this.corsie = filtraCorsie(statoDaFiltrare.getCorsie(), statoDaFiltrare.getTipoFaseGiocoFamily());
		this.classifica = statoDaFiltrare.getClassifica();
		this.numTurno = statoDaFiltrare.getNumTurno();
		this.numTurniTotali = statoDaFiltrare.getNumTurniTotali();
		this.nomi = nomi;
		this.ids = ids;
		this.mioGiocatore = new GiocatoreView(clientReclamante,
				nomi.get(clientReclamante), ids.get(clientReclamante), false);

		Giocatore giocatoreDiTurnoIntero = statoDaFiltrare.getGiocatoreDiTurno();
		Giocatore primoGiocatoreIntero = statoDaFiltrare.getPrimoGiocatore();

		if(giocatoreDiTurnoIntero==null){
			this.giocatoreDiTurno = null;
		} else {
			this.giocatoreDiTurno = new GiocatoreView(giocatoreDiTurnoIntero,  nomi
					.get(giocatoreDiTurnoIntero), ids.get(giocatoreDiTurnoIntero), true);
		}
		this.primoGiocatore = new GiocatoreView(primoGiocatoreIntero,  nomi
				.get(primoGiocatoreIntero), ids.get(primoGiocatoreIntero), true);
		giocatoriView = new ArrayList<GiocatoreView>();
		oscuraGiocatori(statoDaFiltrare.getGiocatori(), clientReclamante);
		this.mosseCorsa = new LinkedList<MossaCorsa>();
	}

	private List<Scuderia> filtraCorsie(List<Scuderia> corsie, TipoFaseGiocoFamily tipoFaseGiocoFamily) {
		if(tipoFaseGiocoFamily.equals(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA) ||
				tipoFaseGiocoFamily.equals(TipoFaseGiocoFamily.F_S_ANTIORARIA)){		
			List<Scuderia> scuderieOscurate = new LinkedList<Scuderia>();
			for(Scuderia s : corsie){
				scuderieOscurate.add(oscuraScuderia(s));
			}
			return scuderieOscurate;
		} else {
			return corsie;
		}
	}

	private Scuderia oscuraScuderia(Scuderia s) {
		Scuderia oscurata = Scuderia.oscura(s);
		return oscurata;
	}

	public StatoDelGiocoView(StatoDelGioco statoDelGioco, Giocatore g, Map<Giocatore, String> nomiClients, Map<Giocatore, Long> iDClients, List<MossaCorsa> mosseCorsa) {
		this(statoDelGioco, g, nomiClients, iDClients);
		this.mosseCorsa.addAll(mosseCorsa);
	}

	private void oscuraGiocatori(List<Giocatore> giocatoriDaOscurare,
			Giocatore clientReclamante) {

		for (Giocatore g : giocatoriDaOscurare) {
			if(g.equals(clientReclamante)){
				giocatoriView.add(new GiocatoreView(g, nomi.get(g), ids.get(g), false));
			}else {
				giocatoriView.add(new GiocatoreView(g, nomi
						.get(g), ids.get(g), true));
			}
		}
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

	public List<MossaCorsa> getMosseCorsa() {
		return mosseCorsa;
	}

	public GiocatoreView getPrimoGiocatore() {
		return primoGiocatore;
	}

	public Scuderia getScuderiaDalColore(Colore colore) {
		for(Scuderia scud : corsie){
			if(scud.getColore().equals(colore)){
				return scud;
			}
		}
		throw new NoSuchElementException("Nessuna scuderia associata al colore: "+colore);
	}

}

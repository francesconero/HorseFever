package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiocatoreView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6804680571901175264L;
	private final int danari;
	private final int puntiVittoria;
	private final List<Scuderia> scuderie;
	private final Personaggio personaggio;
	private final boolean primoGiocatore;
	private final List<CartaAzione> carteAzione;
	private final String nomeUtente;
	private final long ID;
	private final boolean oscurato;

	public GiocatoreView(Giocatore giocatore, String nomeUtente,
			long ID, boolean oscurato) {
		this.oscurato = oscurato;
		this.danari = giocatore.getDanari();
		this.puntiVittoria = giocatore.getPuntiVittoria();
		this.personaggio = giocatore.getPersonaggio();
		this.primoGiocatore = giocatore.isPrimoGiocatore();
		this.scuderie = giocatore.getScuderie();
		if(oscurato){
		this.carteAzione = oscuraCarteAzione(giocatore
				.getCarteAzione());
		} else {
			this.carteAzione = giocatore.getCarteAzione();
		}
		this.nomeUtente = nomeUtente;
		this.ID = ID;
	}

	private List<CartaAzione> oscuraCarteAzione(List<CartaAzione> carteAzione) {
		List<CartaAzione> out = new ArrayList<CartaAzione>();
		for (int i = 0; i < carteAzione.size(); i++) {
			CartaAzione cartaDaAggiungere = new CartaAzione();
			out.add(cartaDaAggiungere);// CartaAzione coperta la
												// prendiamo da file
		}
		return out;
	}

	public int getDanari() {
		return danari;
	}

	public int getPuntiVittoria() {
		return puntiVittoria;
	}

	public List<Scuderia> getScuderie() {
		return scuderie;
	}

	public Personaggio getPersonaggio() {
		return personaggio;
	}

	public boolean isPrimoGiocatore() {
		return primoGiocatore;
	}

	public List<CartaAzione> getCarteAzioni() {
		return carteAzione;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public long getID() {
		return ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ID ^ (ID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GiocatoreView other = (GiocatoreView) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	public String getInformazioniGenerali() {
		String nomeS = "Giocatore " + ID + ": " + nomeUtente;
		String personaggioS = "Personaggio: " + personaggio.getNome();
		String danariS = "Danari: " + danari;
		String PVS = "Punti vittoria: " + puntiVittoria;
		String scuderieS = "Scuderia posseduta: " + scuderie.get(0).getColore();
		String numeroCarte = "Numero carte azione possedute: "
				+ carteAzione.size();

		String out = nomeS + "\n" + personaggioS + "\n" + danariS + "\n" + PVS + "\n"
				+ scuderieS + "\n" + numeroCarte;

		if (primoGiocatore) {
			out += "\nE' il primo giocatore!";
		}

		return out;
	}

}

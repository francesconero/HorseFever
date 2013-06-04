package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GiocatoreView implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6804680571901175264L;
	private final int danari;
	private final int puntiVittoria;
	private final List<Scuderia> scuderie;
	private final Personaggio personaggio;
	private final boolean primoGiocatore;
	private final List<CartaAzione> carteAzioniCoperte;
	private final String nomeUtente;
	private final long ID;
	
	public GiocatoreView(Giocatore giocatoreDaOscurare, String nomeUtente, long ID){
		this.danari=giocatoreDaOscurare.getDanari();
		this.puntiVittoria=giocatoreDaOscurare.getPuntiVittoria();
		this.personaggio=giocatoreDaOscurare.getPersonaggio();
		this.primoGiocatore=giocatoreDaOscurare.isPrimoGiocatore();
		this.scuderie=giocatoreDaOscurare.getScuderie();
		carteAzioniCoperte= new ArrayList<CartaAzione>();
		for (int i=0; i<giocatoreDaOscurare.getCarteAzione().size();i++){
			CartaAzione cartaDaAggiungere=new CartaAzione();
			carteAzioniCoperte.add(cartaDaAggiungere);//CartaAzione coperta la prendiamo da file	
		}
		this.nomeUtente = nomeUtente;
		this.ID = ID;
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

	public List<CartaAzione> getCarteAzioniCoperte() {
		return carteAzioniCoperte;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}

	public long getID() {
		return ID;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GiocatoreView){
			GiocatoreView daConfrontare = (GiocatoreView) obj;
			if(daConfrontare.ID==ID){
				return true;
			}
		}
		return false;
	}
	

}

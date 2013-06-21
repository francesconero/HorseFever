package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/***
 * Questa classe rappresenta un mazziere virtuale
 * che ha il compito di creare tutte le carte, creare 2 dadisprint e mischiare
 * separatamente i rispettivi mazzi
 * @author Jacopo
 *
 */
public class Mazziere {
	protected final List<CartaAzione> carteAzione;
	protected final List<Personaggio> personaggi;
	protected final List<CartaMovimento> carteMovimento;
	protected Colore coloreDado1 = Colore.BIANCO;
	protected Colore coloreDado2 = Colore.BIANCO;
	
	public Mazziere() {
		this.personaggi=Risorse.getInstance().getPersonaggi(); 
		this.carteAzione=Risorse.getInstance().getCarteAzione();
		this.carteMovimento=Risorse.getInstance().getCarteMovimento();		
	}
	
	public Colore getColoreDado1(){
		return coloreDado1;
	}
	
	public Colore getColoreDado2(){
		return coloreDado2;
	}
	
	public void lanciaDado1(){
		List<Colore> colori = Arrays.asList(Colore.values());
		Collections.shuffle(colori);
		coloreDado1 = colori.get(0);
	}
	
	public void lanciaDado2(){
		List<Colore> colori = Arrays.asList(Colore.values());
		Collections.shuffle(colori);
		coloreDado2 = colori.get(0);
	}
	
	public void mischiaCarteAzione(){
		Collections.shuffle(carteAzione);
	}
	
	public void mischiaCarteMovimento(){
		Collections.shuffle(carteMovimento);
	}
	
	public void mischiaPersonaggi(){
		Collections.shuffle(personaggi);
	}
	
	public Personaggio popPersonaggio() throws CarteFiniteException{ // distribuisce carte personaggio
		if (personaggi.size()==0) throw new CarteFiniteException(); 
		Personaggio personaggioDaPassare= personaggi.remove(0);
		return personaggioDaPassare;
	}
	
	public CartaAzione popCartaAzione() throws CarteFiniteException{
		if(carteAzione.size()==0) throw new CarteFiniteException();
		CartaAzione cartaDaPassare= carteAzione.remove(0);
		return cartaDaPassare;
	}
	
	public CartaMovimento popCartaMovimento() throws CarteFiniteException{
		if (carteMovimento.size()==0) throw new CarteFiniteException();
		CartaMovimento cartaDaPassare= carteMovimento.remove(0);
		return cartaDaPassare;
	}
	
	public void setCartaMovimento(List<CartaMovimento> carteDaSettare){
		this.carteMovimento.clear();
		this.carteMovimento.addAll(carteDaSettare);
	}
	
	public void resetCarteMovimento() {
		this.carteMovimento.clear();
		this.carteMovimento.addAll(Risorse.getInstance().getCarteMovimento());		
	}
	
	public void resetCarteAzione() {
		this.carteAzione.clear();
		this.carteAzione.addAll(Risorse.getInstance().getCarteAzione());
	}

	public void shuffle(List<Scuderia> scuderieTemp) {
		Collections.shuffle(scuderieTemp);
	}
}

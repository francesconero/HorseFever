package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.io.IOException;
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
	
	public Mazziere() throws  IOException{
		this.personaggi=Risorse.getIInstance().getPersonaggi(); 
		this.carteAzione=Risorse.getIInstance().getCarteAzione();
		this.carteMovimento=Risorse.getIInstance().getCarteMovimento();		
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
		Personaggio personaggioDaPassare= personaggi.remove(0);
		if (personaggioDaPassare==null) throw new CarteFiniteException(); 
		return personaggioDaPassare;
	}
	
	public CartaAzione popCartaAzione() throws CarteFiniteException{
		CartaAzione cartaDaPassare= carteAzione.remove(0);
		if(cartaDaPassare==null) throw new CarteFiniteException();
		return cartaDaPassare;
	}
	
	public CartaMovimento popCartaMovimento() throws CarteFiniteException{
		CartaMovimento cartaDaPassare= carteMovimento.remove(0);
		if (cartaDaPassare==null) throw new CarteFiniteException();
		return cartaDaPassare;
	}
	
	public void setCartaMovimento(List<CartaMovimento> carteDaSettare){
		this.carteMovimento.clear();
		this.carteMovimento.addAll(carteDaSettare);
	}
	
	public void resetCarteMovimento() throws  IOException{
		this.carteMovimento.clear();
		this.carteMovimento.addAll(Risorse.getIInstance().getCarteMovimento());		
	}
	
	public void resetCarteAzione() throws  IOException{
		this.carteAzione.clear();
		this.carteAzione.addAll(Risorse.getIInstance().getCarteAzione());
	}

	public void shuffle(List<Scuderia> scuderieTemp) {
		Collections.shuffle(scuderieTemp);
	}
}

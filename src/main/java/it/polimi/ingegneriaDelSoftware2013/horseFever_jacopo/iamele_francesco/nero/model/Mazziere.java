package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.io.IOException;
import java.util.*;



/***
 * Questa classe rappresenta un mazziere virtuale
 * che ha il compito di creare tutte le carte, creare 2 dadisprint e mischiare
 * separatamente i rispettivi mazzi
 * @author Jacopo
 *
 */
public class Mazziere {
	private DadoSprint dadoSprint2=new DadoSprint();
	private DadoSprint dadoSprint1=new DadoSprint();
	private final List<CartaAzione> carteAzione;
	private final List<Personaggio> personaggi;
	private final List<CartaMovimento> carteMovimento;
	
	
	public Mazziere() throws  IOException{
		
		dadoSprint2.lanciaDado();
		dadoSprint1.lanciaDado();
		this.personaggi=Risorse.getIInstance().getPersonaggi(); 
		this.carteAzione=Risorse.getIInstance().getCarteAzione();
		this.carteMovimento=Risorse.getIInstance().getCarteMovimento();
		
		
	}
	
	public DadoSprint getDadoSprint2() {
		return dadoSprint2;
	}
	
	public void setDadoSprint2(DadoSprint dadoSprint2) {
		this.dadoSprint2 = dadoSprint2;
	}
	
	public DadoSprint getDadoSprint1() {
		return dadoSprint1;
	}
	
	public void setDadoSprint1(DadoSprint dadoSprint1) {
		this.dadoSprint1 = dadoSprint1;
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
	
	public void resetCarteMovimento() throws  IOException{
		this.carteMovimento.clear();
		//this.carteMovimento.addAll(Risorse.getCarteMovimento());
		
	}
	
	public void resetCarteAzione() throws  IOException{
		this.carteAzione.clear();
		//this.carteAzione.addAll(Risorse.getCarteAzione());
	}
}

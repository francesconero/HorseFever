package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MazziereDeterministico extends Mazziere {

	private Random random;

	public MazziereDeterministico(long random) throws IOException {
		super();
		this.random = new Random(random);
	}
	
	@Override
	public void mischiaCarteAzione() {
		Collections.shuffle(carteAzione, random);
	}
	
	@Override
	public void mischiaCarteMovimento() {
		Collections.shuffle(carteMovimento, random);
	}
	
	@Override
	public void lanciaDado1() {
		List<Colore> colori = Arrays.asList(Colore.values());
		Collections.shuffle(colori, random);
		coloreDado1 = colori.get(0);
	}
	
	@Override
	public void lanciaDado2() {
		List<Colore> colori = Arrays.asList(Colore.values());
		Collections.shuffle(colori, random);
		coloreDado2 = colori.get(0);
	}
	
	@Override
	public Colore getColoreDado1() {
		return coloreDado1;
	}
	
	@Override
	public Colore getColoreDado2() {
		return coloreDado2;
	}
	
	@Override
	public void shuffle(List<Scuderia> scuderieTemp) {
		Collections.shuffle(scuderieTemp, random);
	}

}

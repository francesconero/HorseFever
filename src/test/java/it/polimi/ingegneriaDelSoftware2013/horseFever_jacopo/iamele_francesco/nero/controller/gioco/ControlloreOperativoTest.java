package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import org.junit.BeforeClass;
import org.junit.Test;

public class ControlloreOperativoTest {
	private Mazziere mazziere;
	private StatoDelGioco statoDelGiocoTest;
	private ControlloreFasiGioco controlloreFasiGiocoTest;
	private List<CartaAzione> carteAzionePartenza=new ArrayList<CartaAzione>();
	private List<EffettoAzione> effettoAzione=new ArrayList<EffettoAzione>();
	private List<Integer> valori=new ArrayList<Integer>();
	private List<CartaMovimento> carteMovimento=new ArrayList<CartaMovimento>();
	private List<Integer> movimenti=new ArrayList<Integer>();
	
	private void inizializzaVariabili()throws NumErratoGiocatoriException, CarteFiniteException, IOException{
		controlloreFasiGiocoTest=new ControlloreFasiGioco(5);
		statoDelGiocoTest=controlloreFasiGiocoTest.getStatoDelGioco();
		mazziere=controlloreFasiGiocoTest.getMazziere();
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		statoDelGiocoTest.assegnaCasualmentePrimoGiocatore();
		valori.add(4);
		effettoAzione.add(new EffettoAzione(TipoAzione.PARTENZA,valori));
		valori.remove(0);
		valori.add(0);
		carteAzionePartenza.add(new CartaAzione("carta prova1",'a', effettoAzione ,true,false));
		effettoAzione.remove(0);
		effettoAzione.add(new EffettoAzione(TipoAzione.PARTENZA,valori));
		carteAzionePartenza.add(new CartaAzione("prova2", 'b', effettoAzione, false, true));
		for(int i=0;i<6;i++){
			movimenti.add(2);
		}
		for(int i=0;i<12;i++){
			carteMovimento.add(new CartaMovimento(movimenti));
		}
		
		mazziere.setCartaMovimento(carteMovimento);
		
		
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testPartenza() throws NumErratoGiocatoriException, CarteFiniteException, IOException  {
		inizializzaVariabili();
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzionePartenza.get(0));
		statoDelGiocoTest.getCorsie().get(1).addCartaAzione(carteAzionePartenza.get(1));
		int movimentoCorsia0ASPETTATO=statoDelGiocoTest.getCorsie().get(0).getCarteAzione().get(0).getEffetti().get(0).getValori().get(0);
		int movimentoCorsia1ASPETTATO=statoDelGiocoTest.getCorsie().get(1).getCarteAzione().get(0).getEffetti().get(0).getValori().get(0);
		statoDelGiocoTest=ControlloreOperativo.partenza(statoDelGiocoTest, mazziere);
		assertEquals("il movimento della quotazione 3 deve essere 2",2,carteMovimento.get(0).getMovimento(3));
		assertEquals("il movimento della scuderia"+statoDelGiocoTest.getCorsie().get(0).getColore()+"deve essere di"+movimentoCorsia0ASPETTATO,movimentoCorsia0ASPETTATO,statoDelGiocoTest.getCorsie().get(0).getPosizione());
		assertEquals("il movimento della scuderia"+statoDelGiocoTest.getCorsie().get(1).getColore()+"deve essere di"+movimentoCorsia1ASPETTATO,movimentoCorsia1ASPETTATO,statoDelGiocoTest.getCorsie().get(1).getPosizione());
		assertEquals("il movimento della scuderia"+statoDelGiocoTest.getCorsie().get(2).getColore()+"deve essere di 2",2,statoDelGiocoTest.getCorsie().get(2).getPosizione());
	}

}

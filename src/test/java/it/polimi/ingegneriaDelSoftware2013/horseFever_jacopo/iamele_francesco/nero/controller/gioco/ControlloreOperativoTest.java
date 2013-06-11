package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import static org.junit.Assert.assertEquals;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterministico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ControlloreOperativoTest {
	private Mazziere mazziere;
	private StatoDelGioco statoDelGiocoTest;
	private ControlloreFasiGioco controlloreFasiGiocoTest;
	private List<CartaAzione> carteAzionePartenza=new ArrayList<CartaAzione>();
	private List<CartaAzione> carteAzioneEliminaCarte=new ArrayList<CartaAzione>();
	private List<EffettoAzione> effettoAzione=new ArrayList<EffettoAzione>();
	private List<Integer> valori=new ArrayList<Integer>();
	private List<CartaMovimento> carteMovimento=new ArrayList<CartaMovimento>();
	private List<Integer> movimenti=new ArrayList<Integer>();
	private long randomSeed = 27;
	
	private void inizializzaVariabili()throws NumErratoGiocatoriException, CarteFiniteException, IOException{
		controlloreFasiGiocoTest=new ControlloreFasiGioco(5, new MazziereDeterministico(randomSeed));
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
		carteAzionePartenza.add(new CartaAzione("carta prova2", 'b', effettoAzione, false, true));
		valori.remove(0);
		valori.add(3);
		effettoAzione.remove(0);
		effettoAzione.add(new EffettoAzione(TipoAzione.MODIFICATORE_PARTENZA,valori));
		carteAzionePartenza.add(new CartaAzione("carta prova3", 'c',effettoAzione,true, false));
		valori.clear();
		valori.add(1);
		effettoAzione.clear();
		effettoAzione.add(new EffettoAzione(TipoAzione.CARTE_AZIONE,valori));
		carteAzioneEliminaCarte.add(new CartaAzione("carta prova4", effettoAzione,false,false ));
		movimenti.add(4);
		for(int i=0;i<2;i++){
			movimenti.add(2);
		}
		movimenti.add(3);
		movimenti.add(1);
		movimenti.add(1);
		for(int i=0;i<12;i++){
			carteMovimento.add(new CartaMovimento(movimenti));
		}
		
		mazziere.setCartaMovimento(carteMovimento);
		
		
	}

	@Before
	public void setUpBefore() throws Exception {
		inizializzaVariabili();
	}

	
	@Test
	public void testPartenza() throws NumErratoGiocatoriException, CarteFiniteException, IOException  {		
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzioneEliminaCarte.get(0));
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzionePartenza.get(0));
		statoDelGiocoTest.getCorsie().get(1).addCartaAzione(carteAzionePartenza.get(1));
		statoDelGiocoTest.getCorsie().get(2).addCartaAzione(carteAzionePartenza.get(2));
		statoDelGiocoTest.getCorsie().get(3).addCartaAzione(carteAzionePartenza.get(2));
		statoDelGiocoTest.getCorsie().get(3).addCartaAzione(carteAzionePartenza.get(0));
		int movimentoCorsia0ASPETTATO=4;
		int movimentoCorsia1ASPETTATO=statoDelGiocoTest.getCorsie().get(1).getCarteAzione().get(0).getEffetti().get(0).getValori().get(0);
		int movimentoCorsia2ASPETTATO=statoDelGiocoTest.getCorsie().get(2).getCarteAzione().get(0).getEffetti().get(0).getValori().get(0)+movimenti.get(2);
		statoDelGiocoTest=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGiocoTest);
		statoDelGiocoTest=ControlloreOperativo.partenza(statoDelGiocoTest, mazziere);
		assertEquals("il movimento della quotazione 3 deve essere 2",2,carteMovimento.get(0).getMovimento(3));
		assertEquals("il movimento della scuderia0"+statoDelGiocoTest.getCorsie().get(0).getColore()+"deve essere di"+movimentoCorsia0ASPETTATO,movimentoCorsia0ASPETTATO,statoDelGiocoTest.getCorsie().get(0).getPosizione());
		assertEquals("il movimento della scuderia1"+statoDelGiocoTest.getCorsie().get(1).getColore()+"deve essere di"+movimentoCorsia1ASPETTATO,movimentoCorsia1ASPETTATO,statoDelGiocoTest.getCorsie().get(1).getPosizione());
		assertEquals("il movimento della scuderia2"+statoDelGiocoTest.getCorsie().get(2).getColore()+"deve essere di"+movimentoCorsia2ASPETTATO,movimentoCorsia2ASPETTATO,statoDelGiocoTest.getCorsie().get(2).getPosizione());
		assertEquals("il movimento della scuderia3"+statoDelGiocoTest.getCorsie().get(3).getColore()+"deve essere di"+4,4,statoDelGiocoTest.getCorsie().get(3).getPosizione());
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere,null);
		int count=0;
		while (statoDelGiocoTest.getClassifica().size()!=statoDelGiocoTest.getCorsie().size()){
			count++;
			System.out.println("nel While della corsa per la "+count+" volta");
			statoDelGiocoTest=ControlloreOperativo.movimento(statoDelGiocoTest, mazziere,null);
			statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere,null);
			
		}
		assertEquals("le scuderie arrivate devono essere 6",statoDelGiocoTest.getClassifica().size(),statoDelGiocoTest.getCorsie().size());
	}
	
	@After
	public void tearDownAfter(){
		carteAzionePartenza=new ArrayList<CartaAzione>();
		carteAzioneEliminaCarte=new ArrayList<CartaAzione>();
		effettoAzione=new ArrayList<EffettoAzione>();
		valori=new ArrayList<Integer>();
		carteMovimento=new ArrayList<CartaMovimento>();
		movimenti=new ArrayList<Integer>();
	}
	
	

}

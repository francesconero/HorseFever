package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
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
	private List<CartaAzione> carteAzioneSprint=new ArrayList<CartaAzione>();
	private List<CartaAzione> carteAzioneEliminaCarte=new ArrayList<CartaAzione>();
	private List<CartaAzione> carteAzioneQuotazione=new ArrayList<CartaAzione>();
	private List<CartaAzione> carteAzioneFotoFinish=new ArrayList<CartaAzione>();
	private List<CartaAzione> carteAzioneTraguardo=new ArrayList<CartaAzione>();
	private List<EffettoAzione> effettoAzioneSprint=new ArrayList<EffettoAzione>();
	private List<EffettoAzione> effettoAzionePartenza=new ArrayList<EffettoAzione>();
	private List<EffettoAzione> effettoAzioneCARTE_AZIONE=new ArrayList<EffettoAzione>();
	private List<EffettoAzione> effettoAzioneQUOTAZIONE=new ArrayList<EffettoAzione>();
	private List<EffettoAzione> effettoAzioneFotoFinish=new ArrayList<EffettoAzione>();
	private List<Integer> valori=new ArrayList<Integer>();
	private List<CartaMovimento> carteMovimento=new ArrayList<CartaMovimento>();
	private List<Integer> movimenti=new ArrayList<Integer>();
	private List<CartaAzione> carteAzione= new ArrayList<CartaAzione>();
	private long randomSeed = 27;
	
	private void inizializzaVariabili()throws NumErratoGiocatoriException, CarteFiniteException, IOException{
		controlloreFasiGiocoTest=new ControlloreFasiGioco(5, new MazziereDeterministico(randomSeed));
		statoDelGiocoTest=controlloreFasiGiocoTest.getStatoDelGioco();
		mazziere=controlloreFasiGiocoTest.getMazziere();
		mazziere.mischiaCarteMovimento();
		mazziere.mischiaCarteAzione();
		try{
			while(true){
				carteAzione.add(mazziere.popCartaAzione());
			}
			
		}catch (CarteFiniteException e){
			System.out.println("Carte Azione Finite!! Ho caricato "+carteAzione.size()+" carteAzione");
		}
		statoDelGiocoTest.assegnaCasualmentePrimoGiocatore();
		for(int i=0;i<carteAzione.size();i++){
			if(carteAzione.get(i).getEffetti().get(0).getTipo()==TipoAzione.TRAGUARDO){
				System.out.println("carta Traguardo in posizione "+i+" con valore "+carteAzione.get(i).getEffetti().get(0).getValori().get(0));
				carteAzioneTraguardo.add(carteAzione.get(i));
			}
		}
		valori.add(2);
		effettoAzioneQUOTAZIONE.add(new EffettoAzione(TipoAzione.QUOTAZIONE, valori));
		carteAzioneQuotazione.add(new CartaAzione("carta prova7", effettoAzioneQUOTAZIONE, false, false));
		valori.remove(0);
		valori.add(4);
		effettoAzionePartenza.add(new EffettoAzione(TipoAzione.PARTENZA,valori));
		valori.remove(0);
		valori.add(0);
		effettoAzioneFotoFinish.add(new EffettoAzione(TipoAzione.PHOTOFINISH, valori));
		carteAzioneFotoFinish.add(new CartaAzione("carta FotofinishNegativa", 'f', effettoAzioneFotoFinish, false, true));
		carteAzionePartenza.add(new CartaAzione("carta prova1",'a', effettoAzionePartenza ,true,false));
		effettoAzionePartenza.remove(0);
		effettoAzionePartenza.add(new EffettoAzione(TipoAzione.PARTENZA,valori));
		carteAzionePartenza.add(new CartaAzione("carta prova2", 'b', effettoAzionePartenza, false, true));
		valori.remove(0);
		valori.add(3);
		effettoAzionePartenza.remove(0);
		effettoAzionePartenza.add(new EffettoAzione(TipoAzione.MODIFICATORE_PARTENZA,valori));
		carteAzionePartenza.add(new CartaAzione("carta prova3", 'c',effettoAzionePartenza,true, false));
		valori.clear();
		valori.add(1);
		effettoAzioneFotoFinish.remove(0);
		effettoAzioneFotoFinish.add(new EffettoAzione(TipoAzione.PHOTOFINISH, valori));
		carteAzioneFotoFinish.add(new CartaAzione("carta FotofinishPositiva", 'f', effettoAzioneFotoFinish, true, false));
		effettoAzioneCARTE_AZIONE.add(new EffettoAzione(TipoAzione.CARTE_AZIONE,valori));
		carteAzioneEliminaCarte.add(new CartaAzione("carta prova4", effettoAzioneCARTE_AZIONE,false,false ));
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
		effettoAzioneSprint.add(new EffettoAzione(TipoAzione.MODIFICATORE_SPRINT, valori));
		carteAzioneSprint.add(new CartaAzione("carta prova5", effettoAzioneSprint, true, false));
		valori.clear();
		valori.add(-1);
		effettoAzioneCARTE_AZIONE.remove(0);
		effettoAzioneCARTE_AZIONE.add(new EffettoAzione(TipoAzione.CARTE_AZIONE,valori));
		carteAzioneEliminaCarte.add(new CartaAzione("carta prova6", effettoAzioneCARTE_AZIONE, false, false));
		
		
	}

	@Before
	public void setUpBefore() throws Exception {
		inizializzaVariabili();
		
	}

	
	@Test
	public void testPartenzaECorsa() throws NumErratoGiocatoriException, CarteFiniteException, IOException  {		
		System.out.println("####같testPartenzaECorsa같####");
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
	
	@Test
	public void testSprint(){
		System.out.println("####같testSprint같####");
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzioneSprint.get(0));
		for(int i=0;i<statoDelGiocoTest.getCorsie().size();i++){
			assertEquals("la posizione della scuderia"+i+"deve essere 0",0,statoDelGiocoTest.getCorsie().get(i).getPosizione());
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		boolean sprint=false;
		for(int i=0;i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getPosizione()>0){
				sprint=true;
				if(i==0)assertEquals("lo sprint della scuderia"+i+"deve essere di 2",2,statoDelGiocoTest.getCorsie().get(i).getPosizione());
			}
			
		}
		assertTrue(sprint);
	}
	
	@Test 
	public void testApplicaEffettiCARTE_AZIONEPreCorsa(){
		System.out.println("####같testApplicaEffettiCARTE_AZIONEPreCorsa같####");
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzioneEliminaCarte.get(0));
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzioneEliminaCarte.get(1));
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzionePartenza.get(0));
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzionePartenza.get(1));
		statoDelGiocoTest.getCorsie().get(0).addCartaAzione(carteAzionePartenza.get(2));
		statoDelGiocoTest=ControlloreOperativo.applicaEffettiCARTE_AZIONEPreCorsa(statoDelGiocoTest);
		assertEquals("le carte azione della Scuderia "+statoDelGiocoTest.getCorsie().get(0).getColore()+ "devono essere "+statoDelGiocoTest.getCorsie().get(0).getCarteAzione(),2,statoDelGiocoTest.getCorsie().get(0).getCarteAzione().size());
		
	}
	
	@Test
	public void testApplicaEffettiQUOTAZIONEPreCorsa(){
		System.out.println("####같testApplicaEffettiQUOTAZIONEPreCorsa같####");
		statoDelGiocoTest.getCorsie().get(5).addCartaAzione(carteAzioneQuotazione.get(0));
		int quotazioneOriginale=statoDelGiocoTest.getCorsie().get(5).getQuotazione();
		statoDelGiocoTest=ControlloreOperativo.applicaEffettiQUOTAZIONEPreCorsa(statoDelGiocoTest);
		assertEquals("la scuderia "+statoDelGiocoTest.getCorsie().get(5).getColore()+" con quotazione originale "+quotazioneOriginale+ "deve essere invariata",quotazioneOriginale,statoDelGiocoTest.getCorsie().get(5).getQuotazione());
		
	}

	@Test
	public void testNuoveQuotazioni(){
		System.out.println("####같testNuoveQuotazioni같####");
		List<Integer> valoriQuotazioni= new ArrayList<Integer>();
		for(int i=0;i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.addClassifica(statoDelGiocoTest.getCorsie().get(i));
			valoriQuotazioni.add(statoDelGiocoTest.getCorsie().get(i).getQuotazione());
		}
		statoDelGiocoTest=ControlloreOperativo.nuoveQuotazioni(statoDelGiocoTest);
		for(int i=0;i<statoDelGiocoTest.getCorsie().size();i++){
			int j=valoriQuotazioni.get(i);
			assertEquals("la scuderia "+statoDelGiocoTest.getCorsie().get(i).getColore()+" con quotazione preCambio"+valoriQuotazioni.get(i)+"deve avere la stessa quotazione",j,statoDelGiocoTest.getCorsie().get(i).getQuotazione());
		}
		statoDelGiocoTest.getClassifica().clear();
		
		
		for(int i=statoDelGiocoTest.getCorsie().size()-1;i>=0;i--){
			statoDelGiocoTest.addClassifica(statoDelGiocoTest.getCorsie().get(i));
		}
		statoDelGiocoTest=ControlloreOperativo.nuoveQuotazioni(statoDelGiocoTest);
		for(int i=0;i<3;i++){
			int j=valoriQuotazioni.get(i);
			assertEquals("la scuderia "+statoDelGiocoTest.getCorsie().get(i).getColore()+" con quotazione preCambio"+valoriQuotazioni.get(i)+"deve incrementare la sua quotazione di 1",j+1,statoDelGiocoTest.getCorsie().get(i).getQuotazione());
		}
		for(int i=3;i<statoDelGiocoTest.getCorsie().size();i++){
			int j=valoriQuotazioni.get(i);
			assertEquals("la scuderia "+statoDelGiocoTest.getCorsie().get(i).getColore()+" con quotazione preCambio"+valoriQuotazioni.get(i)+"deve decrementare la sua quotazione di 1",j-1,statoDelGiocoTest.getCorsie().get(i).getQuotazione());
		}
	}
	
	@Test
	public void testFotoFinishSenzaCarteAzione(){
		System.out.println("####같testFotoFinishSenzaCarteAzione같####");
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(12);
			System.out.println("Scuderia: "+statoDelGiocoTest.getCorsie().get(i).getColore()+"");
		}
		
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			System.out.println("Scuderia: "+statoDelGiocoTest.getCorsie().get(i).getColore()+" in posizione "+statoDelGiocoTest.getCorsie().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getCorsie().get(i).getQuotazione());
			System.out.println(i+" Scuderia: "+statoDelGiocoTest.getClassifica().get(i).getColore()+" in posizione "+statoDelGiocoTest.getClassifica().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getClassifica().get(i).getQuotazione());
		}
		assertEquals("tutte le scuderie devono essere in classifica",statoDelGiocoTest.getCorsie().size(),statoDelGiocoTest.getClassifica().size());
	}
	
	@Test
	public void testFotoFinishConCarteAzioniScuderieSprintate(){
		System.out.println("####같testFotoFinishConCarteAzioniScuderieSprintate같####");
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.BLU){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(0));
			}
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.BIANCO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(1)); 
			}
		}
		
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(12);
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			System.out.println("Scuderia: "+statoDelGiocoTest.getCorsie().get(i).getColore()+" in posizione "+statoDelGiocoTest.getCorsie().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getCorsie().get(i).getQuotazione());
			System.out.println(i+" Scuderia: "+statoDelGiocoTest.getClassifica().get(i).getColore()+" in posizione "+statoDelGiocoTest.getClassifica().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getClassifica().get(i).getQuotazione());
		}
		assertEquals("al secondo posto ci deve essere la scuderia BLU",Colore.BLU,statoDelGiocoTest.getClassifica().get(1).getColore());
	}
	
	@Test
	public void testFotoFinishConCarteAzioniAltreScuderie(){
		System.out.println("####같testFotoFinishConCarteAzioniAltreScuderie같####");
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.NERO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(0));
			}
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.GIALLO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(1)); 
			}
		}
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(12);
			
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			System.out.println("Scuderia: "+statoDelGiocoTest.getCorsie().get(i).getColore()+" in posizione "+statoDelGiocoTest.getCorsie().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getCorsie().get(i).getQuotazione());
			System.out.println(i+" Scuderia: "+statoDelGiocoTest.getClassifica().get(i).getColore()+" in posizione "+statoDelGiocoTest.getClassifica().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getClassifica().get(i).getQuotazione());
		}
		assertEquals("al terzo posto ci deve essere la scuderia GIALLA",Colore.GIALLO,statoDelGiocoTest.getClassifica().get(2).getColore());
		assertEquals("all'ultimo posto ci deve essere la scuderia NERA",Colore.NERO,statoDelGiocoTest.getClassifica().get(5).getColore());
	}
	
	@Test
	public void testFotoFinishConCartaAzionePositiva(){
		System.out.println("####같testFotoFinishConCartaAzionePositiva같####");
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.GIALLO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(1)); 
			}
		}
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(12);
			
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			System.out.println("Scuderia: "+statoDelGiocoTest.getCorsie().get(i).getColore()+" in posizione "+statoDelGiocoTest.getCorsie().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getCorsie().get(i).getQuotazione());
			System.out.println(i+" Scuderia: "+statoDelGiocoTest.getClassifica().get(i).getColore()+" in posizione "+statoDelGiocoTest.getClassifica().get(i).getPosizione()+" con quotazione "+statoDelGiocoTest.getClassifica().get(i).getQuotazione());
		}
		assertEquals("al terzo posto ci deve essere la scuderia GIALLA",Colore.GIALLO,statoDelGiocoTest.getClassifica().get(2).getColore());
		
	}
	
	@Test
	public void testEliminaCarte(){
		System.out.println("####같testEliminaCarte같####");
		int posizione=0;
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.GIALLO){
				posizione=i;
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(0));
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(1));
			}
		}
		statoDelGiocoTest=ControlloreOperativo.eliminaCarte(statoDelGiocoTest);
		assertEquals("la scuderia gialla non deve avere carte azione",0,statoDelGiocoTest.getCorsie().get(posizione).getCarteAzione().size());
	}
	
	@Test
	public void testCarteAzioneTraguardo(){
		System.out.println("####같testCarteAzioneTraguardo같####");
		int posizioneGiallo=0;
		int posizioneNero=0;
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.GIALLO){
				posizioneGiallo=i;
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneTraguardo.get(0)); 
			}
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.NERO){
				posizioneNero=i;
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneTraguardo.get(1));
			}
		}
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(14);	
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		assertEquals("la Scuderia GIALLA deve essere in posizione 16",16,statoDelGiocoTest.getCorsie().get(posizioneGiallo).getPosizione());
		assertEquals("la Scuderia NERA deve essere in posizione 12",12,statoDelGiocoTest.getCorsie().get(posizioneNero).getPosizione());
	}
	
	@Test
	public void testChiediAlPrimoGiocatore(){
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(13);	
		}
		for(int i=2;i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).assegnaQuotazione(7);
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		assertTrue(true);
	}
	
	@Test
	public void testChiediAlPrimoGiocatoreConCarteAzione(){
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.NERO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(0));
			}
			if(statoDelGiocoTest.getCorsie().get(i).getColore()==Colore.GIALLO){
				statoDelGiocoTest.getCorsie().get(i).addCartaAzione(carteAzioneFotoFinish.get(1)); 
			}
		}
		for(int i=0; i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).setPosizione(13);	
		}
		for(int i=2;i<statoDelGiocoTest.getCorsie().size();i++){
			statoDelGiocoTest.getCorsie().get(i).assegnaQuotazione(7);
		}
		statoDelGiocoTest=ControlloreOperativo.sprint(statoDelGiocoTest, mazziere, null);
		assertTrue(true);
	}
	
	
	@After
	public void tearDownAfter(){
		carteAzionePartenza=new ArrayList<CartaAzione>();
		carteAzioneEliminaCarte=new ArrayList<CartaAzione>();
		effettoAzionePartenza=new ArrayList<EffettoAzione>();
		valori=new ArrayList<Integer>();
		carteMovimento=new ArrayList<CartaMovimento>();
		movimenti=new ArrayList<Integer>();
		carteAzioneQuotazione=new ArrayList<CartaAzione>();
		carteAzioneSprint=new ArrayList<CartaAzione>();
		effettoAzioneCARTE_AZIONE= new ArrayList<EffettoAzione>();
		effettoAzioneQUOTAZIONE= new ArrayList<EffettoAzione>();
		effettoAzioneSprint= new ArrayList<EffettoAzione>();
		
		
	}
	
	

}

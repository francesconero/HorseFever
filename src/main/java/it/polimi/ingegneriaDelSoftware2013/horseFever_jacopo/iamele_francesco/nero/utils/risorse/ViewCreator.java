package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterministico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ViewCreator {

	public static StatoDelGiocoView creaStatoDelGiocoViewScommessa(){
		StatoDelGioco statoDelGioco = creaStatoDelGiocoScommessa();		
		return createView(statoDelGioco);
	}

	public static StatoDelGiocoView creaStatoDelGiocoViewTruccamento() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoTruccamento();
		return createView(statoDelGioco);
	}

	public static StatoDelGiocoView creaStatoDelGiocoViewCorsa() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoCorsa();
		return createView(statoDelGioco, mosseCorsaFake(statoDelGioco));
	}

	private static StatoDelGiocoView createView(StatoDelGioco statoDelGioco,
			List<MossaCorsa> mosseCorsaFake) {
		Map<Giocatore, String> nomi = new LinkedHashMap<Giocatore, String>();
		nomi.put(statoDelGioco.getGiocatori().get(0), "Giocatore test 1");
		nomi.put(statoDelGioco.getGiocatori().get(1), "Giocatore test 2");
		Map<Giocatore, Long> ids = new LinkedHashMap<Giocatore, Long>();
		ids.put(statoDelGioco.getGiocatori().get(0), 1l);
		ids.put(statoDelGioco.getGiocatori().get(1), 2l);
		return new StatoDelGiocoView(statoDelGioco, statoDelGioco.getPrimoGiocatore(), nomi, ids, mosseCorsaFake);
	}

	private static List<MossaCorsa> mosseCorsaFake(StatoDelGioco statoDelGioco) {
		List<MossaCorsa> mosseFake = new LinkedList<MossaCorsa>();
		
		Map<Scuderia, Integer> nP = new HashMap<Scuderia, Integer>();
		for(Scuderia s: statoDelGioco.getCorsie()){
			nP.put(s, 3);
		}
		mosseFake.add(new Partenza("PARTITI", nP ));
		mosseFake.add(new FineGara("E' finita"));
		return mosseFake;
	}

	private static StatoDelGiocoView createView(StatoDelGioco statoDelGioco) {
		Map<Giocatore, String> nomi = new LinkedHashMap<Giocatore, String>();
		nomi.put(statoDelGioco.getGiocatori().get(0), "Giocatore test 1");
		nomi.put(statoDelGioco.getGiocatori().get(1), "Giocatore test 2");
		Map<Giocatore, Long> ids = new LinkedHashMap<Giocatore, Long>();
		ids.put(statoDelGioco.getGiocatori().get(0), 1l);
		ids.put(statoDelGioco.getGiocatori().get(1), 2l);
		return new StatoDelGiocoView(statoDelGioco, statoDelGioco.getGiocatoreDiTurno(), nomi, ids);
	}

	private static StatoDelGioco creaStatoDelGiocoIniziale() {
		ControlloreFasiGioco cFS = null;		
		try {
			cFS = new ControlloreFasiGioco(2, new MazziereDeterministico(0));
		} catch (NumErratoGiocatoriException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CarteFiniteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cFS.getStatoDelGioco();		
	}	

	private static StatoDelGioco creaStatoDelGiocoPreparazione() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoIniziale();
		statoDelGioco.setInizio(true);
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.PREPARAZIONE);
		statoDelGioco.getMazziere().mischiaCarteMovimento();
		statoDelGioco.getMazziere().mischiaCarteAzione();
		statoDelGioco.assegnaCasualmentePrimoGiocatore();
		statoDelGioco.addNumTurno();
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoDistribuzioneCarte() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoPreparazione();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.DISTRIBUZIONE_CARTE);
		try {
			for (Giocatore g: statoDelGioco.getGiocatori()){
				g.setCarteAzione(statoDelGioco.getMazziere().popCartaAzione());
			}
			for (Giocatore g: statoDelGioco.getGiocatori()){
				g.setCarteAzione(statoDelGioco.getMazziere().popCartaAzione());
			}
		} catch (CarteFiniteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoEliminazioneGiocatore() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoDistribuzioneCarte();
		List<Giocatore>giocatoriDaEliminare=new ArrayList<Giocatore>();
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			if(statoDelGioco.getGiocatori().get(i).getDanari()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100){
				do{
					statoDelGioco.getGiocatori().get(i).removePuntiVittoria(2);
					if(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<=0){
						break;
					}
				}while ((statoDelGioco.getGiocatori().get(i).getDanari()<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()*100)&&(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()>0));
				if(statoDelGioco.getGiocatori().get(i).getPuntiVittoria()<=0){
					giocatoriDaEliminare.add(statoDelGioco.getGiocatori().get(i));
				}
			}
		}
		statoDelGioco.getGiocatori().removeAll(giocatoriDaEliminare);
		for(int i=0;i<giocatoriDaEliminare.size();i++){
			if(giocatoriDaEliminare.get(i).isPrimoGiocatore()){
				statoDelGioco.assegnaCasualmentePrimoGiocatore();
			}
		}
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoScommessa() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoEliminazioneGiocatore();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ORARIA);
		statoDelGioco.setGiocatoreDiTurno(statoDelGioco.getGiocatori().get(0));
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoTruccamento() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoScommessa();
		Scommessa s1 = new Scommessa(TipoScommessa.PIAZZATO, 500, Colore.NERO);
		Scommessa s2 = new Scommessa(TipoScommessa.PIAZZATO, 500, Colore.BIANCO);
		
		statoDelGioco.getGiocatori().get(0).addScommessa(s1);
		statoDelGioco.getGiocatori().get(0).addScommessa(s2);
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA);
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoCorsa() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoScommessa2();
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_C_CORSA);
		
		return statoDelGioco;
	}

	private static StatoDelGioco creaStatoDelGiocoScommessa2() {
		StatoDelGioco statoDelGioco = creaStatoDelGiocoTruccamento();
		for(CartaAzione cA : statoDelGioco.getGiocatori().get(0).getCarteAzione()){
			statoDelGioco.getCorsie().get(0).addCartaAzione(cA);
		}
		for(CartaAzione cA : statoDelGioco.getGiocatori().get(1).getCarteAzione()){
			statoDelGioco.getCorsie().get(0).addCartaAzione(cA);
		}
		statoDelGioco.setTipoFaseGiocoFamily(TipoFaseGiocoFamily.F_S_ANTIORARIA);
		statoDelGioco.getGiocatori().get(0).getCarteAzione().clear();
		statoDelGioco.getGiocatori().get(1).getCarteAzione().clear();
		return statoDelGioco;
	}
	
	

}

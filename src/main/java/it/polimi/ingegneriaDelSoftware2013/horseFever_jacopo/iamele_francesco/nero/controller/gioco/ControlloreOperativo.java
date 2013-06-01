package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import java.util.ArrayList;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
/***
 * Questa classe controlla la faseCorsa (fase troppo complessa per essere gestita dal ControlloreFasi)
 * i suoi metodi sono chiamati unicamente dal controlloreFasi e sono metodi statici
 * @author Jacopo
 *
 */
public class ControlloreOperativo {
	private final static int posizioneDelTraguardo=12;
	
	
	
	
	private static void controllaArrivo(StatoDelGioco statoDelGioco){
		ArrayList<Scuderia> fotoFinish=new ArrayList<Scuderia>();
		for (int i=0; i<statoDelGioco.getCorsie().size(); i++){
			if((statoDelGioco.getCorsie().get(i).getPosizione()>=posizioneDelTraguardo)&&(statoDelGioco.getCorsie().get(i).isArrivato()==false)){
				statoDelGioco.getCorsie().get(i).setArrivato(true);
				fotoFinish.add(statoDelGioco.getCorsie().get(i));
			}
		}
		if (fotoFinish.size()>1)statoDelGioco=fotoFinish(statoDelGioco);
		else statoDelGioco.addClassifica(fotoFinish.get(0));
		
	}
	
	private static StatoDelGioco fotoFinish(StatoDelGioco statoDelGioco){
		//se sono presenti carte azione applica effetti e aggiungi in classifica
		//altrimenti
		
		
		return statoDelGioco;
	}
	
	public static StatoDelGioco posizionaCarta(StatoDelGioco statoDelGioco, PosizionaCarta posizionaCarta){
		int count=0;
		while (posizionaCarta.getScuderiaAssociata().getColore()!=statoDelGioco.getCorsie().get(count).getColore())count++;
		statoDelGioco.getCorsie().get(count).addCartaAzione((CartaAzione)posizionaCarta.getCartaDaPosizionare());
		return statoDelGioco;
	}

	public static StatoDelGioco partenza(StatoDelGioco statoDelGioco, Mazziere mazziere) throws CarteFiniteException{
		CartaMovimento cartaMovimento=mazziere.popCartaMovimento();
		for(int i=0; i<statoDelGioco.getCorsie().size();i++){
			int movimento=cartaMovimento.getCartaMovimento().get(statoDelGioco.getCorsie().get(i));
			//controlla eventuali carteAzioni e applica movimento	
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco sprint(StatoDelGioco statoDelGioco,Mazziere mazziere){
		mazziere.getDadoSprint1().lanciaDado();
		int count=0;
		while (mazziere.getDadoSprint1().getColore()!=statoDelGioco.getCorsie().get(count).getColore()){
			count++;
		}
		if(statoDelGioco.getCorsie().get(count).isArrivato()); //se il cavallo � arrivato non applica lo sprint
		else{
			//controlla eventuali carteAzione e applica sprint 
			controllaArrivo(statoDelGioco);
		}
		mazziere.getDadoSprint2().lanciaDado();
		if(mazziere.getDadoSprint1().getColore()==mazziere.getDadoSprint2().getColore())return statoDelGioco;
		count=0;
		while(mazziere.getDadoSprint2().getColore()!=statoDelGioco.getCorsie().get(count).getColore())count++;
		if(statoDelGioco.getCorsie().get(count).isArrivato());
		else{
			//controlla carteAzione e applica sprint eventuale
			controllaArrivo(statoDelGioco);
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco movimento(StatoDelGioco statoDelGioco, Mazziere mazziere) throws CarteFiniteException{
		CartaMovimento cartaMovimento=mazziere.popCartaMovimento();
		for(int i=0; i<statoDelGioco.getCorsie().size();i++){
			int movimento=cartaMovimento.getCartaMovimento().get(statoDelGioco.getCorsie().get(i));
			//controlla eventuali carteAzioni e applica movimento	
		}
		return statoDelGioco;
	}

	
	
}

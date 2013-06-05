package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;

import java.util.ArrayList;
import java.util.List;
/***
 * Questa classe controlla la faseCorsa (fase troppo complessa per essere gestita dal ControlloreFasi)
 * i suoi metodi sono chiamati unicamente dal controlloreFasi e sono metodi statici
 * @author Jacopo
 *
 */
public class ControlloreOperativo {
	private final static int posizioneDelTraguardo=12;
	
	
	
	
	private static int posizioneMassima(List<Scuderia> scuderie){
		int posizioneMassima=0;
		for(int i=0;i<scuderie.size();i++){
			if(scuderie.get(i).isArrivato());
			else{
				if(posizioneMassima<scuderie.get(i).getPosizione()){
					posizioneMassima=scuderie.get(i).getPosizione();
				}
			}
		}
		
		return posizioneMassima;
	}
	
	
	private static int posizioneMinima(List<Scuderia> scuderie){
		int posizioneMinima=posizioneDelTraguardo;
		for(int i=0;i<scuderie.size();i++){
			if(scuderie.get(i).isArrivato());
			else{
				if(posizioneMinima>scuderie.get(i).getPosizione()){
					posizioneMinima=scuderie.get(i).getPosizione();
				}
			}
		}
		return posizioneMinima;
	}
	
	private static Scuderia scuderiaConCartaPhotoFinishPositiva(StatoDelGioco statoDelGioco,List<Scuderia> scuderie){
		
		for (int i=0;i<scuderie.size();i++){
			for (int j=0;j<scuderie.get(i).getCarteAzione().size();j++){
				for (int k=0;k<scuderie.get(i).getCarteAzione().get(j).getEffetti().size();k++){
					if(scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getTipo()==TipoAzione.PHOTOFINISH){
						if (scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getValori().get(0)>0){
							return scuderie.get(i);
						}
					}
				}
			}
		}
		return null;
	}
	
	
	private static void controllaArrivo(StatoDelGioco statoDelGioco){
		List<Scuderia> scuderieArrivate=new ArrayList<Scuderia>();
		for (int i=0; i<statoDelGioco.getCorsie().size(); i++){
			if((statoDelGioco.getCorsie().get(i).getPosizione()>=posizioneDelTraguardo)&&(statoDelGioco.getCorsie().get(i).isArrivato()==false)){
				statoDelGioco.getCorsie().get(i).setArrivato(true);
				int posizioneModificata=applicaEffettiTRAGUARDO(statoDelGioco.getCorsie().get(i),statoDelGioco.getCorsie().get(i).getPosizione());
				statoDelGioco.getCorsie().get(i).setPosizione(posizioneModificata);
				statoDelGioco.getCorsie().get(i).addNumCicliArrivato(1);
				scuderieArrivate.add(statoDelGioco.getCorsie().get(i));
			}
			else{
				statoDelGioco.getCorsie().get(i).addNumCicliArrivato(1);
			}
		}
		if (scuderieArrivate.size()>1)statoDelGioco=assegnaClassifica(statoDelGioco,scuderieArrivate);
		else statoDelGioco.addClassifica(scuderieArrivate.get(0));
	}
	
	private static int applicaEffettiTRAGUARDO(Scuderia scuderia, int posizioneEccessoTraguardo){
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		int posizioneImposta=posizioneEccessoTraguardo;
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for(int j=0;j<cartaTemp.getEffetti().size();j++){
				if(cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.TRAGUARDO){
					if(cartaTemp.getEffetti().get(j).getValori().get(0)==0){
						posizioneImposta=posizioneDelTraguardo;
					}
					else{
						posizioneImposta=posizioneImposta+cartaTemp.getEffetti().get(j).getValori().get(0);
					}
					
				}
			}
		}
		return posizioneImposta;
	}
	
	private static StatoDelGioco assegnaClassifica(StatoDelGioco statoDelGioco,List<Scuderia> scuderieArrivate){
		int posizioneMassima=posizioneMassima(scuderieArrivate);
		List<Scuderia> scuderieStessaPosizione=new ArrayList<Scuderia>();
		for(int i=0; i<scuderieArrivate.size();i++){
			if (scuderieArrivate.get(i).getPosizione()==posizioneMassima){
				scuderieStessaPosizione.add(scuderieArrivate.get(i));
			}
		}
		if(scuderieStessaPosizione.size()==1) statoDelGioco.addClassifica(scuderieStessaPosizione.get(0));
		else{
			Scuderia scuderia=scuderiaConCartaPhotoFinishPositiva(statoDelGioco, scuderieStessaPosizione);
			if(scuderia!=null) statoDelGioco.addClassifica(scuderia);
			
			
			
		}
		
		
		
		
		
		//se sono presenti carte azione applica effetti e aggiungi in classifica
		//altrimenti
		
		
		return statoDelGioco;
	}
	
	private static List<CartaAzione> carteNegative(List<CartaAzione> carteDaControllare) {
		List<CartaAzione> carteNegative= new ArrayList<CartaAzione>();
		for(int i=0;i<carteDaControllare.size();i++){
			if(carteDaControllare.get(i).isNegativa())carteNegative.add(carteDaControllare.get(i));
		}
		return carteNegative;
	}
	
	private static List<CartaAzione> cartePositive(List<CartaAzione> carteDaControllare) {
		List<CartaAzione> cartePositive=new ArrayList<CartaAzione>();
		for(int i=0;i<carteDaControllare.size();i++){
			if(carteDaControllare.get(i).isPositiva())cartePositive.add(carteDaControllare.get(i));
		}
		return cartePositive;	
	}
	
	private static Scuderia applicaEffettiPARTENZA(Scuderia scuderia, int movimento) { //FATTO
		
			List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
			for (int j=0;j<carteDaControllare.size();j++){
				CartaAzione cartaTemp = carteDaControllare.get(j);
				for (int k=0;k<cartaTemp.getEffetti().size();k++){
					if (cartaTemp.getEffetti().get(k).getTipo()==TipoAzione.PARTENZA){
					scuderia.addPosizione(cartaTemp.getEffetti().get(k).getValori().get(0));
					}
					else{
						scuderia.addPosizione(movimento);
					}
				}
			}
		
		return scuderia;
	}
	
	private static int applicaEffettiMODIFICATORE_PARTENZA(Scuderia scuderia, int movimento){//FATTO
		int movimentoTemp=movimento;
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for (int j=0;j<cartaTemp.getEffetti().size();j++){
				if((cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.MODIFICATORE_PARTENZA)){
					movimentoTemp=movimentoTemp+cartaTemp.getEffetti().get(j).getValori().get(0);
					if(movimentoTemp<0)movimentoTemp=0;//un cavallo non pu� mai retrocedere
				}
				
			}
		}
		return movimentoTemp;
	}
	
	private static Scuderia applicaEffettiSPRINT(Scuderia scuderia, int sprint) { //FATTO
		
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();

		for (int j=0;j<carteDaControllare.size();j++){
			CartaAzione cartaTemp = carteDaControllare.get(j);
			for (int k=0;k<cartaTemp.getEffetti().size();k++){
				if (cartaTemp.getEffetti().get(k).getTipo()==TipoAzione.SPRINT){
				scuderia.addPosizione(cartaTemp.getEffetti().get(k).getValori().get(0));
				}
				else{
					scuderia.addPosizione(sprint);
				}
			}
		}
	
	return scuderia;
}
	
	private static int applicaEffettiMODIFICATORE_SPRINT(Scuderia scuderia, int sprint){ //FATTO
		int sprintTemp=sprint;
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for (int j=0;j<cartaTemp.getEffetti().size();j++){
				if(cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.MODIFICATORE_SPRINT){
					sprintTemp=sprintTemp+cartaTemp.getEffetti().get(j).getValori().get(0);
					if (sprintTemp<0)sprintTemp=0;// un cavallo non pu� mai retrocedere
				}
				
			}
		}
		return sprintTemp;
	}
	
	private static int applicaEffettiPRIMO_ULTIMO(StatoDelGioco statoDelGioco,Scuderia scuderia, int movimento){
		int movimentoTemp=movimento;
		int posizioneMassima=posizioneMassima(statoDelGioco.getCorsie());
		int posizioneMinima=posizioneMinima(statoDelGioco.getCorsie());
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for (int j=0; j<cartaTemp.getEffetti().size();j++){
				if(cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.PRIMO_ULTIMO){
					if(cartaTemp.getEffetti().get(j).getValori().get(0)==0){
						if(scuderia.getPosizione()==posizioneMassima){
							movimentoTemp=cartaTemp.getEffetti().get(j).getValori().get(0);
						}
						
					}
					else{
						if(scuderia.getPosizione()==posizioneMinima){
							movimentoTemp=cartaTemp.getEffetti().get(j).getValori().get(0);
						}
						
					}
				}
			}
		}
		return movimentoTemp;
	}
	
	
	public static StatoDelGioco posizionaCartaAzione(StatoDelGioco statoDelGioco, PosizionaCarta posizionaCarta)throws IllegalArgumentException{//FATTO
		int count=0;
		if(!(posizionaCarta.getCartaDaPosizionare() instanceof CartaAzione))throw new IllegalArgumentException("non � una carta azione"); 
		while (posizionaCarta.getScuderiaAssociata().getColore()!=statoDelGioco.getCorsie().get(count).getColore())count++;
		statoDelGioco.getCorsie().get(count).addCartaAzione((CartaAzione)posizionaCarta.getCartaDaPosizionare());
		return statoDelGioco;
	}

	public static StatoDelGioco partenza(StatoDelGioco statoDelGioco, Mazziere mazziere) throws CarteFiniteException{//FATTO
		CartaMovimento cartaMovimento=mazziere.popCartaMovimento();
		int movimento=0;
		for(int i=0; i<statoDelGioco.getCorsie().size();i++){
			movimento=cartaMovimento.getCartaMovimento(statoDelGioco.getCorsie().get(i).getQuotazione());
			Scuderia scuderiaTemp=statoDelGioco.getCorsie().get(i);
			statoDelGioco.getCorsie().set(i, applicaEffettiPARTENZA(scuderiaTemp, applicaEffettiMODIFICATORE_PARTENZA(scuderiaTemp, movimento)));
		
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco sprint(StatoDelGioco statoDelGioco,Mazziere mazziere){
		mazziere.getDadoSprint1().lanciaDado();
		int count=0;
		final int sprintTemp=1;// in condizioni normali il cavallo sprinta di 1
		while (mazziere.getDadoSprint1().getColore()!=statoDelGioco.getCorsie().get(count).getColore()){
			count++;
		}
		if((statoDelGioco.getCorsie().get(count).isArrivato())&&(statoDelGioco.getCorsie().get(count).getNumCicliArrivato()>1)); //se il cavallo � arrivato da almeno un ciclo non applica lo sprint
		else{
			Scuderia scuderiaTemp=statoDelGioco.getCorsie().get(count);
			statoDelGioco.getCorsie().set(count, applicaEffettiSPRINT(scuderiaTemp, applicaEffettiMODIFICATORE_SPRINT(scuderiaTemp, sprintTemp))); 
			
		}
		mazziere.getDadoSprint2().lanciaDado();
		if(mazziere.getDadoSprint1().getColore()==mazziere.getDadoSprint2().getColore())return statoDelGioco;
		count=0;
		while(mazziere.getDadoSprint2().getColore()!=statoDelGioco.getCorsie().get(count).getColore())count++;
		if((statoDelGioco.getCorsie().get(count).isArrivato())&&(statoDelGioco.getCorsie().get(count).getNumCicliArrivato()>1));
		else{
			Scuderia scuderiaTemp=statoDelGioco.getCorsie().get(count);
			statoDelGioco.getCorsie().set(count, applicaEffettiSPRINT(scuderiaTemp, applicaEffettiMODIFICATORE_SPRINT(scuderiaTemp, sprintTemp)));
			
		}
		controllaArrivo(statoDelGioco);
		return statoDelGioco;
	}
	
	public static StatoDelGioco movimento(StatoDelGioco statoDelGioco, Mazziere mazziere) throws CarteFiniteException{
		CartaMovimento cartaMovimento=mazziere.popCartaMovimento();
		int movimento=0;
		for(int i=0; i<statoDelGioco.getCorsie().size();i++){
			if(statoDelGioco.getCorsie().get(i).isArrivato());
			else{
			movimento=cartaMovimento.getCartaMovimento(statoDelGioco.getCorsie().get(i).getQuotazione());
			Scuderia scuderiaTemp=statoDelGioco.getCorsie().get(i);
			movimento=applicaEffettiPRIMO_ULTIMO(statoDelGioco, scuderiaTemp, movimento);
			scuderiaTemp.addPosizione(movimento);
			statoDelGioco.getCorsie().set(i, scuderiaTemp);
			}
		}
		controllaArrivo(statoDelGioco);
		return statoDelGioco;
	}

	public static StatoDelGioco eliminaCarte(StatoDelGioco statoDelGioco) {  //FATTO
		for (int i=0;i<statoDelGioco.getCorsie().size();i++){
			List<CartaAzione> carteDaControllare = statoDelGioco.getCorsie().get(i).getCarteAzione();
			List<CartaAzione> carteDaEliminare = new ArrayList<CartaAzione>(); 
			for (int j=0;j<carteDaControllare.size();j++){
				CartaAzione cartaTemp = carteDaControllare.get(j);
				for (int k=j+1;k<carteDaControllare.size();k++){
					if (cartaTemp.getLettera()==carteDaControllare.get(k).getLettera()){
						carteDaEliminare.add(carteDaControllare.get(j));
						carteDaEliminare.add(carteDaControllare.get(k));
					}
				}
			}
			carteDaControllare.removeAll(carteDaEliminare);
			statoDelGioco.getCorsie().get(i).setCarteAzione(carteDaControllare);// non necessario
		}
		return statoDelGioco;
	}

	public static StatoDelGioco applicaEffettiCARTE_AZIONEPreCorsa(StatoDelGioco statoDelGioco) { //FATTO
		for (int i=0;i<statoDelGioco.getCorsie().size();i++){
			List<CartaAzione> carteDaControllare = statoDelGioco.getCorsie().get(i).getCarteAzione();
			List<CartaAzione> carteDaRimuovere = new ArrayList<CartaAzione>();
			for (int j=0;j<carteDaControllare.size();j++){
				CartaAzione cartaTemp = carteDaControllare.get(j);
				for (int k=0;k<cartaTemp.getEffetti().size();k++){
					if (cartaTemp.getEffetti().get(k).getTipo()==TipoAzione.CARTE_AZIONE){
						if (cartaTemp.getEffetti().get(k).getValori().get(0)==1){
							carteDaRimuovere=cartePositive(carteDaControllare);
						}else{
							carteDaRimuovere=carteNegative(carteDaControllare);
						}
					}
				}
			}
			carteDaControllare.removeAll(carteDaRimuovere);
			statoDelGioco.getCorsie().get(i).setCarteAzione(carteDaControllare);
			
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco applicaEffettiQUOTAZIONEPreCorsa(StatoDelGioco statoDelGioco) { 
		for (int i=0;i<statoDelGioco.getCorsie().size();i++){
			List<CartaAzione> carteDaControllare = statoDelGioco.getCorsie().get(i).getCarteAzione();
			for (int j=0;j<carteDaControllare.size();j++){
				CartaAzione cartaTemp = carteDaControllare.get(j);
				for (int k=0;k<cartaTemp.getEffetti().size();k++){
					if (cartaTemp.getEffetti().get(k).getTipo()==TipoAzione.QUOTAZIONE){
					statoDelGioco.getCorsie().get(i).addQuotazione(cartaTemp.getEffetti().get(k).getValori().get(0));
					}
				}
			}
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco pagamenti(StatoDelGioco statoDelGioco){
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			for(int j=0; j<statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().size();j++){
				if (statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getScuderia()==statoDelGioco.getClassifica().get(0).getColore()){//se primo posto
					if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getTipoScommessa()==TipoScommessa.VINCENTE){
						statoDelGioco.getGiocatori().get(i).addPuntiVittoria(3);
						statoDelGioco.getGiocatori().get(i).addDanari(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getDanariScommessi()*statoDelGioco.getClassifica().get(0).getQuotazione());
					}else{
						statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
						statoDelGioco.getGiocatori().get(i).addDanari(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getDanariScommessi()*2);
					}
				}else if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getScuderia()==statoDelGioco.getClassifica().get(1).getColore()){//se secondo posto
					if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getTipoScommessa()==TipoScommessa.PIAZZATO){
						statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
						statoDelGioco.getGiocatori().get(i).addDanari(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getDanariScommessi()*2);
					}
				}else if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getScuderia()==statoDelGioco.getClassifica().get(2).getColore()){//se terzo posto
					if(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getTipoScommessa()==TipoScommessa.PIAZZATO){
						statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
						statoDelGioco.getGiocatori().get(i).addDanari(statoDelGioco.getGiocatori().get(i).getScommesseEffettuate().get(j).getDanariScommessi()*2);
					}
				}
			}
			for(int j=0;j<statoDelGioco.getGiocatori().get(i).getScuderie().size();j++){
				if(statoDelGioco.getGiocatori().get(i).getScuderie().get(j).getColore()==statoDelGioco.getClassifica().get(0).getColore()){
					statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
					statoDelGioco.getGiocatori().get(i).addDanari(600);
				}else if(statoDelGioco.getGiocatori().get(i).getScuderie().get(j).getColore()==statoDelGioco.getClassifica().get(1).getColore()){
					statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
					statoDelGioco.getGiocatori().get(i).addDanari(400);
				}else if(statoDelGioco.getGiocatori().get(i).getScuderie().get(j).getColore()==statoDelGioco.getClassifica().get(2).getColore()){
					statoDelGioco.getGiocatori().get(i).addPuntiVittoria(1);
					statoDelGioco.getGiocatori().get(i).addDanari(200);
				}
			}
		}
		return statoDelGioco;
	}
	
	public static StatoDelGioco nuoveQuotazioni(StatoDelGioco statoDelGioco){
		int j=2;
		for(int i=0;i<statoDelGioco.getClassifica().size();i++){
			if(statoDelGioco.getClassifica().get(i).getQuotazione()>(i+j))statoDelGioco.getCorsie().get(i).addQuotazione(-1);
			else if(statoDelGioco.getClassifica().get(i).getQuotazione()<(i+j))statoDelGioco.getCorsie().get(i).addQuotazione(1);
			else;
		}
		return statoDelGioco;
	}
	
	public static int puntiVittoriaMassimi(StatoDelGioco statoDelGioco){
		int puntiVittoriaMassimi=0;
		for(int i=0; i<statoDelGioco.getGiocatori().size();i++){
			if (puntiVittoriaMassimi<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()){
				puntiVittoriaMassimi=statoDelGioco.getGiocatori().get(i).getPuntiVittoria();
			}
		}
		return puntiVittoriaMassimi;
	}
	
	public static int danariMassimi(StatoDelGioco statoDelGioco){
		int danariMassimi=0;
		for(int i=0;i<statoDelGioco.getGiocatori().size();i++){
			if(danariMassimi<statoDelGioco.getGiocatori().get(i).getDanari()){
				danariMassimi=statoDelGioco.getGiocatori().get(i).getDanari();
			}
		}
		return (danariMassimi);
	}
	

}

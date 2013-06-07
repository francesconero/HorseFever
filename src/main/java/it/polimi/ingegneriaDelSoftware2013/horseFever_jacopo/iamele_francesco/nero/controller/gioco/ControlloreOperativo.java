package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

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
	private final static ControlloreReteServer controllore=new ControlloreReteServer();
	
	/**
	 * Questo metodo restituisce la quotazione massima
	 * @param scuderie
	 * @return la quotazione massima
	 */
	private static int quotazioneMassima(List<Scuderia> scuderie){
		int quotazioneMassima=0;
		for(int i=0;i<scuderie.size();i++){
			if(quotazioneMassima<scuderie.get(i).getQuotazione()){
				quotazioneMassima=scuderie.get(i).getQuotazione();
			}
			else{
				continue;
			}
		}
		return quotazioneMassima;
	}
	
	/**
	 * Questo metodo restituisce la posizioneMassima
	 * che rappresenta la/le scuderia/e "arrivata/e prima" delle altre  
	 * @param scuderie
	 * @return La posizione massima
	 */
	private static int posizioneMassima(List<Scuderia> scuderie){
		int posizioneMassima=0;
		for(int i=0;i<scuderie.size();i++){
				if(posizioneMassima<scuderie.get(i).getPosizione()){
					posizioneMassima=scuderie.get(i).getPosizione();
				}
				else{
					continue;
				}
		}
		return posizioneMassima;
	}
	
	/**
	 * Questo metodo restituisce la posizioneMinima
	 * che rappresenta la/le scuderia/e "arrivata/e dopo" le altre
	 * @param scuderie
	 * @return la posizione minima
	 */
	private static int posizioneMinima(List<Scuderia> scuderie){
		int posizioneMinima=posizioneDelTraguardo;
		for(int i=0;i<scuderie.size();i++){
					if(posizioneMinima>scuderie.get(i).getPosizione()){
					posizioneMinima=scuderie.get(i).getPosizione();
					}
					else{
						continue;
					}
			}
		return posizioneMinima;
	}
	
	/**
	 * controlla L'eventuale presenza della carta azione PhotoFinish positiva
	 * @param statoDelGioco
	 * @param scuderie
	 * @return la lista di scuderie con la scuderia interessata in PRIMA posizione oppure null
	 */
	private static List<Scuderia> scuderiaConCartaPhotoFinishPositiva(StatoDelGioco statoDelGioco,List<Scuderia> scuderie){
		
		for (int i=0;i<scuderie.size();i++){
			for (int j=0;j<scuderie.get(i).getCarteAzione().size();j++){
				for (int k=0;k<scuderie.get(i).getCarteAzione().get(j).getEffetti().size();k++){
					if(scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getTipo()==TipoAzione.PHOTOFINISH){
						if (scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getValori().get(0)>0){
							scuderie=MetodiDiSupporto.creaListaOrdinata(scuderie, scuderie.get(i));
							return scuderie;
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * controlla L'eventuale presenza della carta azione PhotoFinish negativa
	 * @param statoDelGioco
	 * @param scuderie
	 * @return la lista di scuderia con la scuderia interessata in ULTIMA posizione oppure null
	 */
	private static List<Scuderia> scuderiaConCartaPhotoFinishNegativa(StatoDelGioco statoDelGioco,List<Scuderia> scuderie){
		
		for (int i=0;i<scuderie.size();i++){
			for (int j=0;j<scuderie.get(i).getCarteAzione().size();j++){
				for (int k=0;k<scuderie.get(i).getCarteAzione().get(j).getEffetti().size();k++){
					if(scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getTipo()==TipoAzione.PHOTOFINISH){
						if (scuderie.get(i).getCarteAzione().get(j).getEffetti().get(k).getValori().get(0)==0){
							if(i==scuderie.size()-1){ // se è l'ultimo elemento ritorna la lista
								return scuderie;
							}else{
							scuderie=MetodiDiSupporto.creaListaOrdinata(scuderie, scuderie.get(i+1)); //altrimenti ordina l'elemento successivo (l'interessato sarà ultimo) 
							return scuderie;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Controlla l'eventuale presenza di scuderie arrivate
	 * @param statoDelGioco
	 */
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
			else if(statoDelGioco.getCorsie().get(i).isArrivato()==true){
				statoDelGioco.getCorsie().get(i).addNumCicliArrivato(1);
			}
			else{
				continue;
			}
		}
		if (scuderieArrivate.size()>1)statoDelGioco=assegnaClassifica(statoDelGioco,scuderieArrivate);
		else statoDelGioco.addClassifica(scuderieArrivate.get(0));
	}
	
	/**
	 * Applica gli effetti delle carte azione TRAGUARDO
	 * @param scuderia
	 * @param posizioneEccessoTraguardo
	 * @return il valore modificato dalla carta azione oppure
	 * il valore passato come parametro (nel caso non vi siano carte)
	 */
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
	
	/**
	 * Questo metodo è decisamente il più complesso del gioco;
	 * Controlla se tra le scuderie arrivate vi sono carte azione di tipo photofinish e le quotazioni di tutte le scuderie.
	 * In caso di parità manda la lista al primo giocatore che la deve riordinare a suo piacimento e rimandarla al server.
	 * @param statoDelGioco
	 * @param scuderieArrivate
	 * @return Lo stato del gioco modificato
	 */
	private static StatoDelGioco assegnaClassifica(StatoDelGioco statoDelGioco,List<Scuderia> scuderieArrivate){
		while(scuderieArrivate.size()>0){
			int posizioneMassima=posizioneMassima(scuderieArrivate);
			List<Scuderia> scuderieStessaPosizione=new ArrayList<Scuderia>();
			for(int i=0; i<scuderieArrivate.size();i++){
				if (scuderieArrivate.get(i).getPosizione()==posizioneMassima){
					scuderieStessaPosizione.add(scuderieArrivate.get(i));
				}
			}
			while(scuderieStessaPosizione.size()>1){
				List<Scuderia> scuderiaTemp=scuderiaConCartaPhotoFinishPositiva(statoDelGioco, scuderieStessaPosizione);
				List<Scuderia> scuderieConCartaNegativa=scuderiaConCartaPhotoFinishNegativa(statoDelGioco, scuderieStessaPosizione);
				int quotazioneMassima=quotazioneMassima(scuderieStessaPosizione);
				if(scuderieConCartaNegativa!=null){
					while(scuderieStessaPosizione.size()>2){
						if(scuderiaTemp!=null){
							statoDelGioco.addClassifica(scuderiaTemp.get(0));
							scuderieArrivate.remove(scuderiaTemp.get(0));
							scuderieStessaPosizione.remove(scuderiaTemp.get(0));
							scuderiaTemp=null;
						}
						else{
							int count=0;
							int posizioneScuderia=0;
							for(int i=0;i<scuderieStessaPosizione.size()-1;i++){
								if(scuderieStessaPosizione.get(i).getQuotazione()==quotazioneMassima){
									count++;posizioneScuderia=i;
								}
								else{
									continue;
								}
							}
							if(count>1){
								//manda la lista (meno l'ultimo elemento) al primo giocatore
							}
							else{
								statoDelGioco.addClassifica(scuderieStessaPosizione.get(posizioneScuderia));
								scuderieArrivate.remove(scuderieStessaPosizione.get(posizioneScuderia));
								scuderieStessaPosizione.remove(posizioneScuderia);
							}
						}
					}
					statoDelGioco.addClassifica(scuderieStessaPosizione.get(0));
					scuderieArrivate.remove(scuderieStessaPosizione);
					scuderieStessaPosizione.remove(0);
				}
				else{
					while(scuderieStessaPosizione.size()>2){
						if(scuderiaTemp!=null){
							statoDelGioco.addClassifica(scuderiaTemp.get(0));
							scuderieArrivate.remove(scuderiaTemp.get(0));
							scuderieStessaPosizione.remove(scuderiaTemp.get(0));
							scuderiaTemp=null;
						}
						else{
							int count=0;
							int posizioneScuderia=0;
							for(int i=0;i<scuderieStessaPosizione.size();i++){
								if(scuderieStessaPosizione.get(i).getQuotazione()==quotazioneMassima){
									count++;posizioneScuderia=i;
								}
								else{
									continue;
								}
							}
							if(count>1){
								//manda la lista al primo giocatore
							}
							else{
								statoDelGioco.addClassifica(scuderieStessaPosizione.get(posizioneScuderia));
								scuderieArrivate.remove(scuderieStessaPosizione.get(posizioneScuderia));
								scuderieStessaPosizione.remove(posizioneScuderia);
							}
						}
						statoDelGioco.addClassifica(scuderieStessaPosizione.get(0));
						scuderieArrivate.remove(scuderieStessaPosizione);
						scuderieStessaPosizione.remove(0);
					}
					
				}
			}
			statoDelGioco.addClassifica(scuderieStessaPosizione.get(0));
			scuderieArrivate.remove(scuderieStessaPosizione);
		}
		return statoDelGioco;
	}
		
	/**Questo metodo controlla la presenza di carte azioni negative
	 * 
	 * @param carteDaControllare
	 * @return le carte azioni negative o una lista vuota
	 */
	private static List<CartaAzione> carteNegative(List<CartaAzione> carteDaControllare) {
		List<CartaAzione> carteNegative= new ArrayList<CartaAzione>();
		for(int i=0;i<carteDaControllare.size();i++){
			if(carteDaControllare.get(i).isNegativa())carteNegative.add(carteDaControllare.get(i));
		}
		return carteNegative;
	}
	
	/**
	 * Questo metodo controlla la presenza di carte azioni positive
	 * @param carteDaControllare
	 * @return le carte azioni positive o una lista vuota
	 */
	private static List<CartaAzione> cartePositive(List<CartaAzione> carteDaControllare) {
		List<CartaAzione> cartePositive=new ArrayList<CartaAzione>();
		for(int i=0;i<carteDaControllare.size();i++){
			if(carteDaControllare.get(i).isPositiva())cartePositive.add(carteDaControllare.get(i));
		}
		return cartePositive;	
	}
	
	/**
	 * Controlla la presenza di carte azioni EffettiPARTENZA 
	 * e ne applica gli effetti
	 * @param scuderia
	 * @param movimento
	 * @return La scuderia su cui si e' chiesto il controllo
	 * con l'attributo posizione modificato dalla carta o dal movimento
	 */
	private static Scuderia applicaEffettiPARTENZA(Scuderia scuderia, int movimento) { 
		
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
	
	/**
	 * Questo metodo applica eventuali effettiMODIFICATORE_PARTENZA
	 * @param scuderia
	 * @param movimento
	 * @return il movimento modificato (oppure no) 
	 * che deve essere aggiunto alla scuderia passata come parametro
	 */
	private static int applicaEffettiMODIFICATORE_PARTENZA(Scuderia scuderia, int movimento){
		int movimentoTemp=movimento;
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for (int j=0;j<cartaTemp.getEffetti().size();j++){
				if((cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.MODIFICATORE_PARTENZA)){
					movimentoTemp=movimentoTemp+cartaTemp.getEffetti().get(j).getValori().get(0);
					if(movimentoTemp<0)movimentoTemp=0;//un cavallo non puo' mai retrocedere
				}
				
			}
		}
		return movimentoTemp;
	}
	
	/**
	 * Controlla la presenza di carte azioni EffettiSPRINT 
	 * e ne applica gli effetti
	 * @param scuderia
	 * @param sprint
	 * @return La scuderia su cui si è chiesto il controllo
	 * con l'attributo posizione modificato dalla carta o dal normale sprint
	 */
	private static Scuderia applicaEffettiSPRINT(Scuderia scuderia, int sprint) { 
		
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
	
	/**
	 * Questo metodo applica eventuali effettiMODIFICATORE_SPRINT
	 * @param scuderia
	 * @param sprint
	 * @return il movimento modificato (oppure no) 
	 * che deve essere aggiunto alla scuderia passata come parametro
	 */
	private static int applicaEffettiMODIFICATORE_SPRINT(Scuderia scuderia, int sprint){ 
		int sprintTemp=sprint;
		List<CartaAzione> carteDaControllare = scuderia.getCarteAzione();
		for (int i=0; i<carteDaControllare.size();i++){
			CartaAzione cartaTemp = carteDaControllare.get(i);
			for (int j=0;j<cartaTemp.getEffetti().size();j++){
				if(cartaTemp.getEffetti().get(j).getTipo()==TipoAzione.MODIFICATORE_SPRINT){
					sprintTemp=sprintTemp+cartaTemp.getEffetti().get(j).getValori().get(0);
					if (sprintTemp<0)sprintTemp=0;// un cavallo non puï¿½ mai retrocedere
				}
				
			}
		}
		return sprintTemp;
	}
	
	/**
	 * Questo metodo applica eventuali effettiPRIMO_ULTIMO
	 * @param statoDelGioco
	 * @param scuderia
	 * @param movimento
	 * @return il movimento modificato(oppure no) dalla carta PRIMO_ULTIMO
	 * da aggiungere alla scuderiaAssociate
	 */
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
	
	/**
	 * questo metodo applica la mossa di posizionamento di un giocatore
	 * @param statoDelGioco
	 * @param posizionaCarta
	 * @return lo stato del gioco modificato
	 * @throws IllegalArgumentException
	 */
	public static StatoDelGioco posizionaCartaAzione(StatoDelGioco statoDelGioco, PosizionaCarta posizionaCarta)throws IllegalArgumentException{
		int count=0;
		if(!(posizionaCarta.getCartaDaPosizionare() instanceof CartaAzione))throw new IllegalArgumentException("non ï¿½ una carta azione"); 
		while (posizionaCarta.getScuderiaAssociata().getColore()!=statoDelGioco.getCorsie().get(count).getColore())count++;
		statoDelGioco.getCorsie().get(count).addCartaAzione((CartaAzione)posizionaCarta.getCartaDaPosizionare());
		return statoDelGioco;
	}
	
    /**
     * Questo metodo rappresenta la partenza;
     * chiede una carta movimento al mazziere e applica il movimento ad ogni scuderia
     * @param statoDelGioco
     * @param mazziere
     * @return lo stato del gioco modificato
     * @throws CarteFiniteException
     */
	public static StatoDelGioco partenza(StatoDelGioco statoDelGioco, Mazziere mazziere) throws CarteFiniteException{
		CartaMovimento cartaMovimento=mazziere.popCartaMovimento();
		int movimento=0;
		for(int i=0; i<statoDelGioco.getCorsie().size();i++){
			movimento=cartaMovimento.getCartaMovimento(statoDelGioco.getCorsie().get(i).getQuotazione());
			Scuderia scuderiaTemp=statoDelGioco.getCorsie().get(i);
			statoDelGioco.getCorsie().set(i, applicaEffettiPARTENZA(scuderiaTemp, applicaEffettiMODIFICATORE_PARTENZA(scuderiaTemp, movimento)));
		
		}
		return statoDelGioco;
	}
	
	/**
	 * Questo metodo rappresenta lo sprint;
	 * normalmente lo sprint vale 1 ma potrebbe venir modificato da carte azioni SPRINT
	 * @param statoDelGioco
	 * @param mazziere
	 * @return lo stato del gioco modificato
	 */
	public static StatoDelGioco sprint(StatoDelGioco statoDelGioco,Mazziere mazziere){
		mazziere.getDadoSprint1().lanciaDado();
		int count=0;
		final int sprintTemp=1;
		while (mazziere.getDadoSprint1().getColore()!=statoDelGioco.getCorsie().get(count).getColore()){
			count++;
		}
		if((statoDelGioco.getCorsie().get(count).isArrivato())&&(statoDelGioco.getCorsie().get(count).getNumCicliArrivato()>1)); //se il cavallo ï¿½ arrivato da almeno un ciclo non applica lo sprint
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
	
	/**
	 * Questo metodo rappresenta il movimento;
	 * chiede una carta movimento al mazziere e ne applica gli effetti
	 * @param statoDelGioco
	 * @param mazziere
	 * @return lo stato del gioco modificato
	 * @throws CarteFiniteException
	 */
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
	
	/**
	 * Questo metodo elimina eventuali carte con egual lettera
	 * @param statoDelGioco
	 * @return lo stato del gioco modificato
	 */
	public static StatoDelGioco eliminaCarte(StatoDelGioco statoDelGioco) {  
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
	
 /**
  * questo metodo controlla l'eventuale presenza di carte azioni che rimuovono tutte le carte
  * azioni positive o negative e ne applica gli effetti
  * @param statoDelGioco
  * @return lo stato del gioco modificato
  */
	public static StatoDelGioco applicaEffettiCARTE_AZIONEPreCorsa(StatoDelGioco statoDelGioco) { 
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
	
	/**
	 * Cerca eventuali carte azioni che modificano la quotazione delle scuderie
	 * @param statoDelGioco
	 * @return lo stato del gioco modificato
	 */
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
	
	/**
	 * Questo metodo paga i giocatori che hanno azzeccato la/e scommessa/e
	 * e i proprietari delle scuderie sul podio
	 * @param statoDelGioco
	 * @return lo stato del gioco modificato
	 */
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
	
	/**
	 * Questo metodo aggiorna le quotazioni delle scuderie
	 * @param statoDelGioco
	 * @return lo stato del gioco modificato
	 */
	public static StatoDelGioco nuoveQuotazioni(StatoDelGioco statoDelGioco){
		int j=2;
		for(int i=0;i<statoDelGioco.getClassifica().size();i++){
			if(statoDelGioco.getClassifica().get(i).getQuotazione()>(i+j))statoDelGioco.getCorsie().get(i).addQuotazione(-1);
			else if(statoDelGioco.getClassifica().get(i).getQuotazione()<(i+j))statoDelGioco.getCorsie().get(i).addQuotazione(1);
			else;
		}
		return statoDelGioco;
	}
	
	/**
	 * questo metodo controlla il valore dei punti vittoria del/dei giocatore/i 
	 * con piu' punti vittoria
	 * @param statoDelGioco
	 * @return i punti vittoria massimi
	 */
	public static int puntiVittoriaMassimi(StatoDelGioco statoDelGioco){
		int puntiVittoriaMassimi=0;
		for(int i=0; i<statoDelGioco.getGiocatori().size();i++){
			if (puntiVittoriaMassimi<statoDelGioco.getGiocatori().get(i).getPuntiVittoria()){
				puntiVittoriaMassimi=statoDelGioco.getGiocatori().get(i).getPuntiVittoria();
			}
		}
		return puntiVittoriaMassimi;
	}
	
	/**
	 * questo metodo controlla il valore dei danari del/dei giocatore/i 
	 * con piu' danari
	 * @param statoDelGioco
	 * @return i danari massimi
	 */
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

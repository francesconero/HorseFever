package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

/**
 * Singleton che si occupa di recuperare da file, tutti i vari oggetti necessari al gioco (Carte, Personaggi, etc)
 * @author Francesco
 *
 */
public class Risorse {
	private static final Risorse  risorse = new Risorse();
	private Risorse(){
		
	}
	
	public static Risorse getInstance(){
		return risorse;
	}
	
	/**
	 * Ottieni la lista di {@link CartaAzione carte azione} definite nel file "/src/main/resources/carte/CarteAzione.xml"
	 * 
	 * @return	List<CartaAzione>	carteAzione  la lista di carte azione
	 * @throws FormatoFileErratoException	lanciato in caso di errori nel parsing XML (ad esempio non viene rispettato CartaAzione.dtd)
	 * @throws IOException	errori nel caricamento da file
	 */
	public static List<CartaAzione> getCarteAzione() throws FormatoFileErratoException,  IOException{
		try {
			return LoaderCarteAzione.getInstance().caricaCarte();
		} catch (SAXException e) {
			throw new FormatoFileErratoException("Errore nel formato del file CarteAzione.xml",e);
		}
	}

}

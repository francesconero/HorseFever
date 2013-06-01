package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;

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
	
	/**
	 * Ottieni la lista di {@link Personaggio personaggi} definiti nel file "/src/main/resources/carte/Personaggi.xml"
	 * 
	 * @return	List<Personaggio>	personaggi  la lista di personaggi
	 * @throws FormatoFileErratoException	lanciato in caso di errori nel parsing XML (ad esempio non viene rispettato Personaggio.dtd)
	 * @throws IOException	errori nel caricamento da file
	 */
	public static List<Personaggio> getPersonaggi() throws FormatoFileErratoException,  IOException{
		try {
			return LoaderPersonaggi.getInstance().caricaCarte();
		} catch (SAXException e) {
			throw new FormatoFileErratoException("Errore nel formato del file Personaggi.xml",e);
		}
	}
	
	/**
	 * Ottieni la lista di {@link   CartaMovimento carte movimento} definite nel file "/src/main/resources/carte/CarteMovimento.xml"
	 * 
	 * @return	List<CartaMovimento>	carteMovimento  la lista di carte azione
	 * @throws FormatoFileErratoException	lanciato in caso di errori nel parsing XML (ad esempio non viene rispettato CartaMovimento.dtd)
	 * @throws IOException	errori nel caricamento da file
	 */
	public static List<CartaMovimento> getCarteMovimento() throws FormatoFileErratoException,  IOException{
		try {
			return LoaderMovimenti.getInstance().caricaCarte();
		} catch (SAXException e) {
			throw new FormatoFileErratoException("Errore nel formato del file Movimenti.xml",e);
		}
	}

}

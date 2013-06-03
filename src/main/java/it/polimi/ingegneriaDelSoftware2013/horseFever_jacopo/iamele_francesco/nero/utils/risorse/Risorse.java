package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

/**
 * Singleton che si occupa di recuperare da file, tutti i vari oggetti necessari al gioco (Carte, Personaggi, etc)
 * @author Francesco
 *
 */
public class Risorse {
	
	private static Risorse risorse = null;
	private Map<Personaggio, String> immaginiPersonaggi = new HashMap<Personaggio, String>();
	private Map<CartaAzione, String> immaginiCarteAzione = new HashMap<CartaAzione, String>();
	private Map<String, String> immaginiGeneriche = new HashMap<String, String>();
	private final List<CartaAzione> carteAzione;
	private final List<Personaggio> personaggi;
	private final List<CartaMovimento> carteMovimento;
	
	private Risorse() throws FormatoFileErratoException, IOException{
		carteAzione = caricaCarteAzione();
		personaggi = caricaPersonaggi();
		carteMovimento = caricaCarteMovimento();
		caricaImmaginiGeneriche();
	}
	
	private void caricaImmaginiGeneriche() {
		try {
			immaginiGeneriche = LoaderRisorseGUI.getInstance().caricaRisorse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		}
	}

	public static Risorse getIInstance(){
		if(risorse == null){
			try {
				risorse = new Risorse();
			} catch (FormatoFileErratoException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return risorse;
	}
	
	/**
	 * Ottieni la lista di {@link CartaAzione carte azione} definite nel file "/src/main/resources/carte/CarteAzione.xml"
	 * 
	 * @return	List<CartaAzione>	carteAzione  la lista di carte azione
	 * @throws FormatoFileErratoException	lanciato in caso di errori nel parsing XML (ad esempio non viene rispettato CartaAzione.dtd)
	 * @throws IOException	errori nel caricamento da file
	 */
	private List<CartaAzione> caricaCarteAzione() throws FormatoFileErratoException,  IOException{
		try {
			immaginiCarteAzione = LoaderCarteAzione.getInstance().caricaCarte();
			return new LinkedList<CartaAzione>(immaginiCarteAzione.keySet());
		} catch (SAXException e) {
			throw new FormatoFileErratoException("Errore nel formato del file CarteAzione.xml",e);
		}
	}
	
	/**
	 * Ottieni la lista di {@link String personaggi} definiti nel file "/src/main/resources/carte/Personaggi.xml"
	 * 
	 * @return	List<Personaggio>	personaggi  la lista di personaggi
	 * @throws FormatoFileErratoException	lanciato in caso di errori nel parsing XML (ad esempio non viene rispettato Personaggio.dtd)
	 * @throws IOException	errori nel caricamento da file
	 */
	private List<Personaggio> caricaPersonaggi() throws FormatoFileErratoException,  IOException{
		try {
			immaginiPersonaggi = LoaderPersonaggi.getInstance().caricaCarte();
			return new LinkedList<Personaggio>(immaginiPersonaggi.keySet());
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
	private List<CartaMovimento> caricaCarteMovimento() throws FormatoFileErratoException,  IOException{
		try {
			return LoaderMovimenti.getInstance().caricaCarte();
		} catch (SAXException e) {
			throw new FormatoFileErratoException("Errore nel formato del file Movimenti.xml",e);
		}
	}

	public String getImmagine(Personaggio personaggio) {
		return immaginiPersonaggi.get(personaggio);
	}

	public String getImmagine(CartaAzione cartaAzione) {
		return immaginiCarteAzione.get(cartaAzione);
	}
	
	public String getImmagine(String string) {
		return immaginiGeneriche.get(string);
	}

	public List<Personaggio> getPersonaggi() {
		return personaggi;
	}

	public List<CartaAzione> getCarteAzione() {
		return carteAzione;
	}

	public List<CartaMovimento> getCarteMovimento() {
		return carteMovimento;
	}

}

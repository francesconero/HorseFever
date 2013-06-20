package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.xml.sax.SAXException;

/**
 * Singleton che si occupa di recuperare da file, tutti i vari oggetti necessari al gioco (Carte, Personaggi, etc)
 * @author Francesco
 *
 */
public class Risorse {
	
	private static Risorse risorse = null;
	private Map<Personaggio, String> immaginiPersonaggi = new LinkedHashMap<Personaggio, String>();
	private Map<CartaAzione, String> immaginiCarteAzione = new LinkedHashMap<CartaAzione, String>();
	private Map<String, String> immaginiGeneriche = new LinkedHashMap<String, String>();
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

	public synchronized static Risorse getInstance(){
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
	 * Ottieni la lista di {@link CartaAzione carte azione} definite nel file "resources/carte/CarteAzione.xml"
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
	 * Ottieni la lista di {@link String personaggi} definiti nel file "resources/carte/Personaggi.xml"
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
	 * Ottieni la lista di {@link   CartaMovimento carte movimento} definite nel file "resources/carte/CarteMovimento.xml"
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
		String out = immaginiPersonaggi.get(personaggio); 
		if(out==null){
			throw new RuntimeException("Nessuna immagine trovata per il personaggio " + personaggio);
		} else {
			return out;
		}
	}

	public String getImmagine(CartaAzione cartaAzione) {
		if(cartaAzione.isCoperta()){
			return Risorse.getInstance().getImmagine("RetroCarteAzione");
		}
		String out = immaginiCarteAzione.get(cartaAzione);; 
		if(out==null){
			throw new RuntimeException("Nessuna immagine trovata per la carta azione " + cartaAzione);
		} else {
			return out;
		}
	}
	
	public String getImmagine(String string) {
		String out = immaginiGeneriche.get(string);
		if(out==null){
			throw new RuntimeException("Nessuna immagine associata a quella stringa " + string);
		} else {
			return out;
		}
	}

	public List<Personaggio> getPersonaggi() {
		return new LinkedList<Personaggio>(personaggi);
	}

	public List<CartaAzione> getCarteAzione() {
		return new LinkedList<CartaAzione>(carteAzione);
	}

	public List<CartaMovimento> getCarteMovimento() {
		return new LinkedList<CartaMovimento>(carteMovimento);
	}

	public CartaAzione getCartaAzione(String nomeDaCercare) {
		for(CartaAzione out : carteAzione){
			if(out.getNome().equals(nomeDaCercare)){
				return out;
			}
		} 
		throw new NoSuchElementException("Non esiste nessuna carta con quel nome!");
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

final class LoaderCarteAzione extends DefaultHandler {

	private SAXParser saxParser;

	private Map<CartaAzione, String> out = new LinkedHashMap<CartaAzione, String>();
	private List<EffettoAzione> effettiTemp = new LinkedList<EffettoAzione>();
	private String nome;
	private char lettera;
	private TipoAzione tipoAzione;
	private List<Integer> valori = new LinkedList<Integer>();
	private boolean letteraPresente = false;
	private String immagine;

	private boolean positiva;

	private boolean negativa;
	
	private static final LoaderCarteAzione LOADER = new LoaderCarteAzione();

	public static LoaderCarteAzione getInstance() {
		return LOADER;
	}

	private LoaderCarteAzione() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(false);
		spf.setValidating(true);
		try {
			saxParser = spf.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public Map<CartaAzione, String> caricaCarte() throws IOException, SAXException {
		if(out.isEmpty()){
			saxParser.parse(new File("resources/carte/CarteAzione.xml"),
					this);
		}
		return out;
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("CartaAzione")) {
			effettiTemp.clear();
			letteraPresente = false;
			lettera = 0;
			nome = null;
			immagine = null;
			positiva = false;
			negativa = false;
			
			nome = attributes.getValue("nome");
			immagine = attributes.getValue("immagine");
			String temp = attributes.getValue("lettera");
			positiva = Boolean.parseBoolean(attributes.getValue("positiva"));
			negativa =  Boolean.parseBoolean(attributes.getValue("negativa"));
			
			if (temp != null) {
				letteraPresente = true;
				lettera = temp.charAt(0);
			}
		}
		if (qName.equals("Effetto")) {
			valori.clear();
			tipoAzione = interpretaAzione(attributes.getValue("tipo"));
		}
		if (qName.equals("Valore")) {
			valori.add(Integer.parseInt(attributes.getValue("valore")));
		}
	}

	private TipoAzione interpretaAzione(String value) {
		return TipoAzione.valueOf(value);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("CartaAzione")) {
			if (letteraPresente) {
				out.put(new CartaAzione(nome, lettera, effettiTemp, positiva, negativa),immagine);
			} else {
				out.put(new CartaAzione(nome, lettera, effettiTemp, positiva, negativa),immagine);
			}
		} else if (qName.equals("Effetto")) {
			effettiTemp.add(new EffettoAzione(tipoAzione, valori));
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error parsing document");
		System.out.println(e.getLocalizedMessage());
	}
	
	public static void main(String[] args) throws IOException, SAXException {
		LoaderCarteAzione test = new LoaderCarteAzione();
		List<CartaAzione> testList = new LinkedList<CartaAzione>(test.caricaCarte().keySet());
		System.out.println("Trovate " + testList.size() + " carte azione:");
		for (CartaAzione carta : testList) {
			System.out.println(carta);
		}
	}
	
}

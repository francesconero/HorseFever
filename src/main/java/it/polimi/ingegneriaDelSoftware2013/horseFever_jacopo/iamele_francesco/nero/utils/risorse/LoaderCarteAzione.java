package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

final class LoaderCarteAzione extends DefaultHandler {

	private SAXParser saxParser;

	private List<CartaAzione> out = new LinkedList<CartaAzione>();
	private List<EffettoAzione> effettiTemp = new LinkedList<EffettoAzione>();
	private String nome;
	private char lettera;
	private TipoAzione tipoAzione;
	private List<Integer> valori = new LinkedList<Integer>();
	private boolean letteraPresente = false;
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

	public List<CartaAzione> caricaCarte() throws IOException, SAXException {
		if(out.isEmpty()){
			saxParser.parse(new File("src/main/resources/carte/CarteAzione.xml"),
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
			nome = null;
			nome = attributes.getValue("nome");
			String temp = attributes.getValue("lettera");
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
				out.add(new CartaAzione(nome, lettera, effettiTemp));
			} else {
				out.add(new CartaAzione(nome, effettiTemp));
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
		List<CartaAzione> testList = test.caricaCarte();
		System.out.println("Trovate " + testList.size() + " carte azione:");
		for (CartaAzione carta : testList) {
			System.out.println(carta);
		}
	}
	
}
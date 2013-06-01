package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
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

final class LoaderPersonaggi extends DefaultHandler{
	
	private SAXParser saxParser;

	private List<Personaggio> out = new LinkedList<Personaggio>();

	private String nome;
	private int danari;
	private int quotazione;
	
	private static final LoaderPersonaggi LOADER = new LoaderPersonaggi();

	public static LoaderPersonaggi getInstance() {
		return LOADER;
	}

	private LoaderPersonaggi() {
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
	
	public List<Personaggio> caricaCarte() throws IOException, SAXException {
		if(out.isEmpty()){
			saxParser.parse(new File("src/main/resources/carte/PersonaggiFamily.xml"),
					this);
		}
		return out;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Personaggio")) {
			nome = null;
			danari = 0;
			quotazione = 0;			
			nome = attributes.getValue("nome");
			danari = Integer.parseInt(attributes.getValue("danari"));
			quotazione = Integer.parseInt(attributes.getValue("quotazione"));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("Personaggio")) {
			out.add(new Personaggio(nome, danari,quotazione));
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error parsing document");
		System.out.println(e.getLocalizedMessage());
	}
	
	public static void main(String[] args) throws IOException, SAXException {
		LoaderPersonaggi test = new LoaderPersonaggi();
		List<Personaggio> testList = test.caricaCarte();
		System.out.println("Trovati " + testList.size() + " personaggi:");
		for (Personaggio carta : testList) {
			System.out.println(carta);
		}
	}
	
}

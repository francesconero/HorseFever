package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

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

final class LoaderRisorseGUI extends DefaultHandler{
	
	private SAXParser saxParser;

	private Map<String, String> out = new LinkedHashMap<String, String>();

	private String nome;
	private String percorso;
	
	private static final LoaderRisorseGUI LOADER = new LoaderRisorseGUI();

	public static LoaderRisorseGUI getInstance() {
		return LOADER;
	}

	private LoaderRisorseGUI() {
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
	
	public Map<String, String> caricaRisorse() throws IOException, SAXException {
		if(out.isEmpty()){
			saxParser.parse(new File("src/main/resources/GUI/RisorseGUI.xml"),
					this);
		}
		return out;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Risorsa")) {
			nome = null;
			percorso = null;
			nome = attributes.getValue("nome");
			percorso = attributes.getValue("percorso");
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("Risorsa")) {
			out.put(nome, percorso);
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error parsing document");
		System.out.println(e.getLocalizedMessage());
	}
	
	public static void main(String[] args) throws IOException, SAXException {
		LoaderRisorseGUI test = new LoaderRisorseGUI();
		Map<String, String> testOut = test.caricaRisorse();
		List<String> testList = new LinkedList<String>(testOut.keySet());
		System.out.println("Trovate " + testList.size() + " risorse:");
		for (String risorsa : testList) {
			System.out.println(risorsa);
			System.out.println(testOut.get(risorsa));
		}
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaMovimento;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

final class LoaderMovimenti extends DefaultHandler{
	
	private SAXParser saxParser;

	private List<CartaMovimento> out = new LinkedList<CartaMovimento>();

	private List<Integer> movimenti = new LinkedList<Integer>();
	
	private static final LoaderMovimenti LOADER = new LoaderMovimenti();

	public static LoaderMovimenti getInstance() {
		return LOADER;
	}

	private LoaderMovimenti() {
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
	
	public List<CartaMovimento> caricaCarte() throws IOException, SAXException {
		if(out.isEmpty()){
			saxParser.parse(new File("src/main/resources/carte/Movimenti.xml"),
					this);
		}
		return out;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Movimento")) {
			movimenti.clear();
			String raw = attributes.getValue("valori");
			StringTokenizer tok = new StringTokenizer(raw);
			while(tok.hasMoreTokens()){
				int temp = Integer.parseInt(tok.nextToken());
				movimenti.add(temp);
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("Movimento")) {
			out.add(new CartaMovimento(movimenti));
		}
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error parsing document");
		System.out.println(e.getLocalizedMessage());
	}
	
	public static void main(String[] args) throws IOException, SAXException {
		LoaderMovimenti test = new LoaderMovimenti();
		List<CartaMovimento> testList = test.caricaCarte();
		System.out.println("Trovate " + testList.size() + " carte movimento:");
		for (CartaMovimento carta : testList) {
			System.out.println(carta);
		}
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import org.xml.sax.SAXException;

public class FormatoFileErratoException extends RuntimeException {

	public FormatoFileErratoException(String string, SAXException e) {
		super(string,e);
	}

}

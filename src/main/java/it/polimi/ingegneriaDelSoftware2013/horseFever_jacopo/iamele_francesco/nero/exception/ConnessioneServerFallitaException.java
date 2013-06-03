package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;


/**
 * Lanciata sul client quando questo non riesce a connettersi al server.
 * @author Francesco
 *
 */
public class ConnessioneServerFallitaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6226379262996761805L;

	public ConnessioneServerFallitaException(String string) {
		super(string);
	}

	public ConnessioneServerFallitaException(String string,
			Throwable e) {
		super(string,e);
	}

}

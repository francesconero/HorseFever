package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;



/**
 * Lanciata sul server quando uno o più client, falliscono la connessione al server.
 * 
 * @author Francesco
 */
public class AttesaClientsFallitaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717330702631793812L;

	public AttesaClientsFallitaException(String string) {
		super(string);
	}

	public AttesaClientsFallitaException(String string, Throwable e) {
		super(string, e);
	}
	
}

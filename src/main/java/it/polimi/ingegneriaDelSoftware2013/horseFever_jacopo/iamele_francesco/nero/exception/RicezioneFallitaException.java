package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

/**
 * Lanciata sul destinatario nel caso non si sia completata la procedura di ricezione di un oggetto.
 * @author Francesco
 *
 */
public class RicezioneFallitaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7009268236806017581L;

	public RicezioneFallitaException(String string) {
		super(string);
	}

	public RicezioneFallitaException(String string, Throwable e) {
		super(string, e);
	}

	public RicezioneFallitaException() {
	}

	public RicezioneFallitaException(Throwable e) {
		super(e);
	}

}

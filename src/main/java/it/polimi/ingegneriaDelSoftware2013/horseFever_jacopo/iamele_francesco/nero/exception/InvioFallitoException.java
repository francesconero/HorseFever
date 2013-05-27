package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

/**
 * Lanciata sul mittente nel caso l'invio di un oggetto tra client e server o viceversa, fallisca.
 * @author Francesco
 *
 */
public class InvioFallitoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5593853704138251546L;

	public InvioFallitoException(String string) {
		super(string);
	}

}

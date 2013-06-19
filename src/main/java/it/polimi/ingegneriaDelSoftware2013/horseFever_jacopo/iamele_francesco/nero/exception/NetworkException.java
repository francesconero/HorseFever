package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

public class NetworkException extends RuntimeException {

	public NetworkException() {
		
	}

	public NetworkException(String string) {
		super(string);
	}

	public NetworkException(Throwable e) {
		super(e);
	}

	public NetworkException(String string, Throwable e) {
		super(string, e);
	}

}

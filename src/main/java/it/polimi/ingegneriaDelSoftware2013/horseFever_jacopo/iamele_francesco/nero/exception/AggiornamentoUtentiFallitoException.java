package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

public class AggiornamentoUtentiFallitoException extends RuntimeException {

	public AggiornamentoUtentiFallitoException(InvioFallitoException e) {
		super(e);
	}

}
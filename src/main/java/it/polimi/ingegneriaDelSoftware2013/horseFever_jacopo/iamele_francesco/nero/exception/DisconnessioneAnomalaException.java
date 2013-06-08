package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception;

import java.net.Socket;

public class DisconnessioneAnomalaException extends RuntimeException {

	private final Socket socketDisconnesso;

	public DisconnessioneAnomalaException(Socket socketDisconnesso) {
		this.socketDisconnesso = socketDisconnesso;
	}

	public DisconnessioneAnomalaException(String string,
			Throwable e, Socket socketDisconnesso) {
		super(string,e);
		this.socketDisconnesso = socketDisconnesso;
	}

	public DisconnessioneAnomalaException(Throwable e, Socket socketDisconnesso) {
		super(e);
		this.socketDisconnesso = socketDisconnesso;
	}

	public Socket getSocketDisconnesso() {
		return socketDisconnesso;
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;

import java.io.IOException;


public class ControlloreConsoleScommessa extends ControlloreConsole {

	public ControlloreConsoleScommessa(
			ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);
	}

	@Override
	public void controlla() {
		chiediScommesse();
	}

	private void chiediScommesse() {
		ControlloreReteClient clientDiTurno = getClientDiTurno();
		Scommessa daFare;
		try {
			daFare = view.chiediScommessa(clientDiTurno.getProprioNome());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		clientDiTurno.scommetti(daFare);
	}

}
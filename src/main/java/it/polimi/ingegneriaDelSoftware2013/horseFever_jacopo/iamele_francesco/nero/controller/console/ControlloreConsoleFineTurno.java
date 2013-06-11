package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

public class ControlloreConsoleFineTurno extends ControlloreConsole {

	public ControlloreConsoleFineTurno(
			ControlloreConsole controlloreConsoleCorsa) {
		super(controlloreConsoleCorsa);
	}

	@Override
	protected void controlla() {
		System.out.println("La gara è finita! Ecco la situazione:");
		view.mostraSituazioneGenerale(getViewGenerica());
	}

}

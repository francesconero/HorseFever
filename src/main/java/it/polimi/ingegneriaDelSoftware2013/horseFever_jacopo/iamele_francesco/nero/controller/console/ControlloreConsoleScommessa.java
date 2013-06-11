package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;

public class ControlloreConsoleScommessa extends ControlloreConsole {

	public ControlloreConsoleScommessa(ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);
	}

	@Override
	public void controlla() {
		System.out.println("Inizia il controllore scommesse!");
		switch (getViewGenerica().getTipoFaseGiocoFamily()) {
			case F_S_ORARIA:
				chiediScommesseOrarie();
				ControlloreConsoleAlterazione ccA = new ControlloreConsoleAlterazione(this);
				ccA.controlla();
				break;
			case F_S_ANTIORARIA:
				chiediScommesseAntiorarie();
				ControlloreConsoleCorsa cCC = new ControlloreConsoleCorsa(this);
				cCC.controlla();
				break;
			default:
				throw new IllegalStateException("Fase non gestibile: " + getViewGenerica().getTipoFaseGiocoFamily());
		}
	}

	private void chiediScommesseOrarie() {
		while (getViewGenerica().getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ORARIA)) {
			ControlloreReteClient clientDiTurno = getClientDiTurno();
			view.scrivi(clientDiTurno.getProprioNome() + " e' il tuo turno di scommettere!");
			faiScommessa(clientDiTurno);
			aggiornaViste();
		}
	}

	private void chiediScommesseAntiorarie() {
		while (getViewGenerica().getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ANTIORARIA)) {
			ControlloreReteClient clientDiTurno = getClientDiTurno();
			if (view.chiediConferma("Vuoi scommettere ancora?", clientDiTurno.getProprioNome())) {
				view.scrivi("Ok allora, scommetti ancora");
				faiScommessa(clientDiTurno);
			} else {
				view.scrivi("Ok nessuna scommessa");
				nessunaScommessa(clientDiTurno);
			}
			aggiornaViste();
		}
	}

	private void nessunaScommessa(ControlloreReteClient clientDiTurno) {
		Scommessa daFare;
		daFare = new Scommessa(null, 0, null); // scommessa vuota nell'animo
		clientDiTurno.scommetti(daFare);
	}

	private void faiScommessa(ControlloreReteClient clientDiTurno) {
		Scommessa daFare;
		boolean OK = false;
		do{
			daFare = view.chiediScommessa(clientDiTurno.getProprioNome());
			OK  = clientDiTurno.scommetti(daFare);
			if(!OK){
				view.scrivi("La tua scommessa e' stata rifutata! Riprova!");			
			}
		}while(!OK);
	}

}
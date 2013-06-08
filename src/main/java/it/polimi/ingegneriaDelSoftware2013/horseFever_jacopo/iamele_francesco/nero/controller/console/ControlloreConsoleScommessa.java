package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;


public class ControlloreConsoleScommessa extends ControlloreConsole {
	
	public ControlloreConsoleScommessa(
			ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);
	}

	@Override
	public void controlla() {
		switch (getViewGenerica().getTipoFaseGiocoFamily()) {
		case F_S_ORARIA:
			chiediScommesseOrarie();
			ControlloreConsoleAlterazione ccA = new ControlloreConsoleAlterazione(this);
			ccA.controlla();
			break;
		case F_S_ANTIORARIA:
			chiediScommesseAntiorarie();
			break;
		default:
			throw new IllegalStateException("Fase non gestibile");
		}
	}

	private void chiediScommesseOrarie() {
		while(getViewGenerica().getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ORARIA)){
			ControlloreReteClient clientDiTurno = getClientDiTurno();
			faiScommessa(clientDiTurno);
			aggiornaViste();
		}
	}

	private void chiediScommesseAntiorarie() {
		while(getViewGenerica().getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ANTIORARIA)){
			ControlloreReteClient clientDiTurno = getClientDiTurno();
			if(view.chiediConferma("Vuoi scommettere ancora?")){
				faiScommessa(clientDiTurno);
			} else {
				
			}
			aggiornaViste();
		}
	}

	private void faiScommessa(ControlloreReteClient clientDiTurno) {
		Scommessa daFare;
		daFare = view.chiediScommessa(clientDiTurno.getProprioNome());
		if(!clientDiTurno.scommetti(daFare)){
			System.out.println("La tua scommessa e' stata rifutata! Riprova!");
			faiScommessa(clientDiTurno);
		}
	}

}
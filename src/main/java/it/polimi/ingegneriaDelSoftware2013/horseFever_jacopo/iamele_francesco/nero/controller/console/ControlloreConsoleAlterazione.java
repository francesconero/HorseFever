package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

public class ControlloreConsoleAlterazione extends ControlloreConsole {
	
	public ControlloreConsoleAlterazione( ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);		
	}

	@Override
	protected void controlla() {
		System.out.println("Inizia il controllore alterazione!");
		chiediCarteAzione();
		ControlloreConsoleScommessa ccS2 = new ControlloreConsoleScommessa(this);
		ccS2.controlla();
	}

	private void chiediCarteAzione() {
		while(getViewGenerica().getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA)){
			ControlloreReteClient clientDiTurno = getClientDiTurno();
			posizionaCartaAzione(clientDiTurno);
			aggiornaViste();
		}
	}

	private void posizionaCartaAzione(ControlloreReteClient clientDiTurno) {
		CartaAzione daGiocare = view.chiediCartaAzione(clientDiTurno.getProprioNome(), visteGiocatori.get(clientDiTurno).getMioGiocatore().getCarteAzioni());
		Colore suCuiGiocare = view.chiediScuderia(clientDiTurno.getProprioNome());
		Scuderia delColoreGiusto = null;
		for(Scuderia scuderia: getViewGenerica().getCorsie()){
			if(scuderia.getColore().equals(suCuiGiocare)){
				delColoreGiusto = scuderia;
			}
		}
		PosizionaCarta posizionaCarta = new PosizionaCarta(daGiocare, delColoreGiusto);
		if(!clientDiTurno.posizionaCarta(posizionaCarta)){
			throw new RuntimeException("Che cosa abbiamo giocato??");
		}
	}

}

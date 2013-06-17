package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Movimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;

import java.util.List;

public class ControlloreConsoleCorsa extends ControlloreConsole implements MossaCorsaVisitor{

	private ControlloreReteClient primoGiocatore;

	public ControlloreConsoleCorsa(ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);		
	}

	@Override
	protected void controlla() {
		System.out.println("Inizia il controllore corsa!");
		switch (getViewGenerica().getTipoFaseGiocoFamily()) {
			case F_C_SCOPRICARTAAZIONE:
				view.scrivi("Ecco i cavalli pronti alla partenza!");
				view.mostraCorsa(getViewGenerica().getCorsie());
				aggiornaViste();
			case F_C_CORSA:
				primoGiocatore = getClientPrimoGiocatore();
				List<MossaCorsa> lista = getViewGenerica().getMosseCorsa();
				System.out.println("Ci sono: "+lista.size()+" mosse corsa");
				for(MossaCorsa mossaCorsa : lista){
					mossaCorsa.accept(this);
				}		
				break;
			default:
				throw new IllegalStateException("Fase non gestibile: " + getViewGenerica().getTipoFaseGiocoFamily());
		}
	}

	public void visita(Movimento movimento) {
		view.scrivi(movimento.getNuovePosizioni().toString());
		view.scrivi(movimento.getCommento());
		view.mostraCorsa(movimento.getNuovePosizioni());
	}

	public void visita(Sprint sprint) {
		view.scrivi(sprint.getCommento());
		view.mostraCorsa(sprint.getNuovePosizioni());
	}

	public void visita(Photofinish photofinish) {
		view.scrivi(photofinish.getCommento());
	}

	public void visita(Conflitto conflitto) {
		view.scrivi(conflitto.getCommento());
		List<Colore> conflittoRisolto = view.risolviConflitto(conflitto.getScuderieInConflitto(), primoGiocatore.getProprioNome());
		primoGiocatore.risolviConflitto(conflittoRisolto);
		aggiornaViste();
		ControlloreConsoleCorsa cCC = new ControlloreConsoleCorsa(this);
		cCC.controlla();
	}

	public void visita(Classifica classifica) {
		view.scrivi(classifica.getCommento());
		view.mostraClassifica(classifica.getClassifica());
	}

	public void visita(FineGara fineGara) {
		System.out.println("Fine gara!");
		view.scrivi(fineGara.getCommento());
		aggiornaViste();	
	}

	public void visita(Partenza partenza) {
		view.scrivi(partenza.getNuovePosizioni().toString());
		view.scrivi(partenza.getCommento());
		view.mostraCorsa(partenza.getNuovePosizioni());
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Movimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;

import java.util.List;

public class ControlloreConsoleCorsa extends ControlloreConsole implements MossaCorsaVisitor{

	public ControlloreConsoleCorsa(ControlloreConsole controlloreConsoleIniziale) {
		super(controlloreConsoleIniziale);		
	}

	@Override
	protected void controlla() {
		List<MossaCorsa> lista = getViewGenerica().getMosseCorsa();
		System.out.println("I cavalli sono alle postazioni di partenza...");
		view.mostraCorsa(getViewGenerica().getCorsie());
		System.out.println("Sta per iniziare la corsa: 3...2...1...VIA!");
		
		for(MossaCorsa mossaCorsa : lista){
			mossaCorsa.accept(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
	}

	public void visita(Movimento movimento) {
		// TODO Auto-generated method stub
		
	}

	public void visita(Sprint sprint) {
		// TODO Auto-generated method stub
		
	}

	public void visita(Photofinish photofinish) {
		// TODO Auto-generated method stub
		
	}

	public void visita(Conflitto conflitto) {
		// TODO Auto-generated method stub
		
	}

	public void visita(Classifica classifica) {
		// TODO Auto-generated method stub
		
	}

	public void visita(FineGara fineGara) {
		// TODO Auto-generated method stub
		
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;

public interface MossaCorsaVisitor {
	
	public void visita(MossaCorsa movimento);
	
	public void visita(Sprint sprint);
	
	public void visita(Photofinish photofinish);
	
	public void visita(Conflitto conflitto);

	public void visita(Classifica classifica);

	public void visita(FineGara fineGara);

	public void visita(Partenza partenza);
	
}

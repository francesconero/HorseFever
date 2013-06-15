package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

public interface FramePrincipaleObserver {
	
	public void pronto();
	
	public void scommessa(Scuderia scuderia, int danari, TipoScommessa tipoScommessa);
	
	public void giocaCartaAzione(CartaAzione carta, Scuderia scuderia);

}

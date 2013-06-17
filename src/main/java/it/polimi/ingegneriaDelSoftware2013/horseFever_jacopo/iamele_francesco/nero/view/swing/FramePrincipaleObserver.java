package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.List;

public interface FramePrincipaleObserver {
	
	public void pronto();
	
	public void scommessa(Scommessa scommessa);
	
	public void giocaCartaAzione(CartaAzione carta, Scuderia scuderia);

	public void prossimoAggiornamento();

	public void stessoAggiornamento();

	public void risolviConflitto(List<Colore> soluzioneConflitto);

}

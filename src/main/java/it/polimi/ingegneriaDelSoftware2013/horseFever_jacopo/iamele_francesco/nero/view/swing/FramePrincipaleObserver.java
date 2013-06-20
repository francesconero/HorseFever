package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.List;

public interface FramePrincipaleObserver {
	
	void pronto();
	
	void scommessa(Scommessa scommessa);
	
	void giocaCartaAzione(CartaAzione carta, Scuderia scuderia);

	void prossimoAggiornamento();

	void stessoAggiornamento();

	void risolviConflitto(List<Colore> soluzioneConflitto);

	void finePartita();

	void riceviAvvertimento();

}

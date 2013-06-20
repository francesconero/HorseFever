package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.List;
import java.util.Map;

public interface View {

	int chiediNumeroGiocatori();

	List<String> chiediNomi(int numeroGiocatori);

	Scommessa chiediScommessa(String nomeGiocatore);

	void mostraSituazioneGenerale(StatoDelGiocoView current);

	void eliminaGiocatore(String proprioNome, Long id);

	boolean chiediConferma(String domanda, String nomeGiocatore);

	CartaAzione chiediCartaAzione(String nomeGiocatore,
			List<CartaAzione> carteAzionePossedute);

	Colore chiediScuderia(String proprioNome);

	void mostraCorsa(List<Scuderia> scuderie);

	void mostraCorsa(Map<Colore, Integer> scuderie);

	List<Colore> risolviConflitto(
			List<Colore> scuderieInConflitto, String nomeGiocatore);

	void mostraClassifica(List<Colore> classifica);
	
	void scrivi(String daScrivere);

}
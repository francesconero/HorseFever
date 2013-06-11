package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.List;
import java.util.Map;

public interface View {

	public int chiediNumeroGiocatori();

	public List<String> chiediNomi(int numeroGiocatori);

	public Scommessa chiediScommessa(String nomeGiocatore);

	public void mostraSituazioneGenerale(StatoDelGiocoView current);

	public  void eliminaGiocatore(String proprioNome, Long id);

	public boolean chiediConferma(String domanda, String nomeGiocatore);

	public CartaAzione chiediCartaAzione(String nomeGiocatore,
			List<CartaAzione> carteAzionePossedute);

	public Colore chiediScuderia(String proprioNome);

	public void mostraCorsa(List<Scuderia> scuderie);

	public void mostraCorsa(Map<Colore, Integer> scuderie);

	public List<Colore> risolviConflitto(
			List<Colore> scuderieInConflitto, String nomeGiocatore);

	public void mostraClassifica(List<Colore> classifica);
	
	public void scrivi(String daScrivere);

}
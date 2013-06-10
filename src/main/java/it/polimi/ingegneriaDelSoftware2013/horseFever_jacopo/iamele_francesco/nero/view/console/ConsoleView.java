package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.util.LinkedList;
import java.util.List;

public class ConsoleView {

	public int chiediNumeroGiocatori() {
		return AiutanteConsole.chiediIntero("In quanti giocate?");
	}

	public List<String> chiediNomi(int numeroGiocatori) {
		List<String> out = new LinkedList<String>();
		for(int i = 0; i<numeroGiocatori; i++){
			out.add(AiutanteConsole.chiediStringa("Inserisci il nome del giocatore"+i));
		}
		return out;
	}

	public Scommessa chiediScommessa(String nomeGiocatore) {
		Scommessa scommessa;
		do{
			Colore col = AiutanteConsole.chiediEnum("Su quale scuderia vuoi puntare?", Colore.class);
			int danariScommessi = AiutanteConsole.chiediIntero("Quanti danari vuoi puntare?");
			TipoScommessa tS = AiutanteConsole.chiediEnum("Che tipo di scommessa vuoi fare?", TipoScommessa.class);

			scommessa = new Scommessa(tS, danariScommessi, col);
		} while(!AiutanteConsole.chiediConferma("Vuoi davvero puntare questa scommessa: "+scommessa+" ? "));

		return scommessa;		
	}

	public void mostraSituazioneGenerale(StatoDelGiocoView current) {		
		System.out.println("Siamo nella fase: "+current.getTipoFaseGiocoFamily());
		System.out.println("Stanno giocando:\n");

		for(GiocatoreView giocatore : current.getGiocatori()){
			System.out.println(giocatore.getInformazioniGenerali());
			System.out.println("\n");
		}
	}

	public void eliminaGiocatore(String proprioNome, Long id) {
		System.out.println("Attenzione, il Giocatore "+id+" "+proprioNome+" non ha più denaro da scommettere, nè punti vittoria da barattare!\n");
		System.out.println("Mi dispiace "+proprioNome+". Sei stato eliminato!");
	}

	public boolean chiediConferma(String domanda, String nomeGiocatore) {
		return AiutanteConsole.chiediConferma(nomeGiocatore + " " + domanda);
	}

	public CartaAzione chiediCartaAzione(String nomeGiocatore, List<CartaAzione> carteAzionePossedute ) {
		System.out.println(nomeGiocatore+ " e' il tuo turno di giocare una carta azione!");
		return AiutanteConsole.chiediValoreLista("Quale vuoi giocare?", carteAzionePossedute );
	}

	public Colore chiediScuderia(String proprioNome) {
		return AiutanteConsole.chiediEnum("Su quale scuderia la vuoi giocare?", Colore.class);
	}
	
	public void mostraCorsa(List<Scuderia> scuderie){
		List<Integer> corsie = new LinkedList<Integer>();
		for(Scuderia scuderia: scuderie){
			corsie.add(scuderia.getPosizione());
		}
		AiutanteConsole.aggiornaCorsie(corsie);
	}
}

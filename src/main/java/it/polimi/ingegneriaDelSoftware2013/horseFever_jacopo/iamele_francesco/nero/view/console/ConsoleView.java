package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ConsoleView {

	public int chiediNumeroGiocatori() throws IOException {
		return AiutanteConsole.chiediIntero("In quanti giocate?");
	}

	public List<String> chiediNomi(int numeroGiocatori) throws IOException {
		List<String> out = new LinkedList<String>();
		for(int i = 0; i<numeroGiocatori; i++){
			out.add(AiutanteConsole.chiediStringa("Inserisci il nome del giocatore"+i));
		}
		return out;
	}
	
	public Scommessa chiediScommessa(String nomeGiocatore) throws IOException{
		System.out.println(nomeGiocatore+ " e' il tuo turno di scommettere!");
		
		Scommessa scommessa;
		do{
		Colore col = AiutanteConsole.chiediEnum("Su quale scuderia vuoi puntare?", Colore.class);
		int danariScommessi = AiutanteConsole.chiediIntero("Quanti danari vuoi puntare?");
		TipoScommessa tS = AiutanteConsole.chiediEnum("Che tipo di scommessa vuoi fare?", TipoScommessa.class);
		
		scommessa = new Scommessa(tS, danariScommessi, col);
		} while(AiutanteConsole.chiediConferma("Vuoi davvero puntare questa scommessa: "+scommessa+"\n?"));
		
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

}

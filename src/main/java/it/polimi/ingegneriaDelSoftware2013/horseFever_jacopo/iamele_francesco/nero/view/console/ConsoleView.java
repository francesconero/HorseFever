package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

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

}

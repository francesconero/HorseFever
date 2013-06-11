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
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ConsoleView implements View{

	public int chiediNumeroGiocatori() {
		return AiutanteConsole.chiediIntero("In quanti giocate?");
	}

	public List<String> chiediNomi(int numeroGiocatori) {
		List<String> out = new LinkedList<String>();
		for (int i = 0; i < numeroGiocatori; i++) {
			out.add(AiutanteConsole
					.chiediStringa("Inserisci il nome del giocatore" + i));
		}
		return out;
	}

	public Scommessa chiediScommessa(String nomeGiocatore) {
		Scommessa scommessa;
		do {
			Colore col = AiutanteConsole.chiediEnum(
					"Su quale scuderia vuoi puntare?", Colore.class);
			int danariScommessi = AiutanteConsole
					.chiediIntero("Quanti danari vuoi puntare?");
			TipoScommessa tS = AiutanteConsole.chiediEnum(
					"Che tipo di scommessa vuoi fare?", TipoScommessa.class);

			scommessa = new Scommessa(tS, danariScommessi, col);
		} while (!AiutanteConsole
				.chiediConferma("Vuoi davvero puntare questa scommessa: "
						+ scommessa + " ? "));

		return scommessa;
	}

	public void mostraSituazioneGenerale(StatoDelGiocoView current) {
		scriviACapo("Siamo nella fase: "
				+ current.getTipoFaseGiocoFamily());
		scriviACapo("Stanno giocando:\n");

		for (GiocatoreView giocatore : current.getGiocatori()) {
			scriviACapo(giocatore.getInformazioniGenerali());
			scriviACapo("\n");
		}
	}

	public void eliminaGiocatore(String proprioNome, Long id) {
		System.out
				.println("Attenzione, il Giocatore "
						+ id
						+ " "
						+ proprioNome
						+ " non ha più denaro da scommettere, nè punti vittoria da barattare!\n");
		scriviACapo("Mi dispiace " + proprioNome
				+ ". Sei stato eliminato!");
	}

	public boolean chiediConferma(String domanda, String nomeGiocatore) {
		return AiutanteConsole.chiediConferma(nomeGiocatore + " " + domanda);
	}

	public CartaAzione chiediCartaAzione(String nomeGiocatore,
			List<CartaAzione> carteAzionePossedute) {
		scriviACapo(nomeGiocatore
				+ " e' il tuo turno di giocare una carta azione!");
		return AiutanteConsole.chiediValoreLista("Quale vuoi giocare?",
				carteAzionePossedute);
	}

	public Colore chiediScuderia(String proprioNome) {
		return AiutanteConsole.chiediEnum("Su quale scuderia la vuoi giocare?",
				Colore.class);
	}

	public void mostraCorsa(List<Scuderia> scuderie) {
		Map<Colore, Integer> temp = new TreeMap<Colore, Integer>();
		for (Scuderia scuderia : scuderie) {
			temp.put(scuderia.getColore(), scuderia.getPosizione());
		}
		mostraCorsa(temp);
	}

	public void mostraCorsa(Map<Colore, Integer> scuderie) {
		SortedSet<Colore> sortedScuderie = new TreeSet<Colore>(
				scuderie.keySet());
		List<Integer> corsie = new LinkedList<Integer>();
		for (Colore scuderia : sortedScuderie) {
			corsie.add(scuderie.get(scuderia));
		}
		AiutanteConsole.aggiornaCorsie(corsie);
	}

	public List<Colore> risolviConflitto(
			List<Colore> scuderieInConflitto, String nomeGiocatore) {
		System.out
				.println(nomeGiocatore
						+ " è sorto un dilemma: sta a te decidere l'ordine di arrivo di questi cavalli!");
		scriviACapo(scuderieInConflitto);

		List<Colore> ancoraDaDecidere;
		List<Colore> giaDecisi = new LinkedList<Colore>();
		do {
			ancoraDaDecidere = new LinkedList<Colore>(scuderieInConflitto);
			giaDecisi.clear();
			for (Colore scuderia : scuderieInConflitto) {
				Colore daAggiungere = AiutanteConsole.chiediValoreLista(
						"Inserisci il prossimo cavallo tra quelli rimasti: ",
						ancoraDaDecidere);
				ancoraDaDecidere.remove(daAggiungere);
				giaDecisi.add(daAggiungere);
			}

			scriviACapo("Ok questo è il nuovo ordine da te inserito: ");
			scriviACapo(giaDecisi);
		} while (!AiutanteConsole.chiediConferma("Va bene?"));
		return giaDecisi;
	}

	public static void main(String[] args) {
		View cV= new ConsoleView();
		List<Colore> scuderieCasuali = new LinkedList<Colore>();
		scuderieCasuali.add(Colore.BIANCO);
		scuderieCasuali.add(Colore.NERO);
		scuderieCasuali.add(Colore.GIALLO);
		cV.risolviConflitto(scuderieCasuali, "Francesco");
	}

	public void mostraClassifica(List<Colore> classifica) {
		scriviACapo("L'ordine di arrivo dei cavalli è il seguente: ");
		for(int i = 0; i < classifica.size(); i++){
			scriviACapo(".::"+(i+1)+"::.");
			scriviACapo(classifica.get(i));
		}
		
	}

	public void scrivi(String daScrivere) {
		System.out.print(daScrivere);
	}
	
	private void scriviACapo(Object daScrivere) {
		scrivi(daScrivere.toString());
		System.out.println();
	}

}

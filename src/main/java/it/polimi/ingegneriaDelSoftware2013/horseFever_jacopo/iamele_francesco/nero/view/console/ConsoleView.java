package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ConsoleView implements View{

	private final AiutanteConsole aiutanteConsole;
	private final InputStream in;
	private final PrintWriter pW;

	public ConsoleView(InputStream in, PrintWriter pW){
		this.in = in;
		this.pW = pW;
		this.aiutanteConsole = new AiutanteConsole(in, pW);
	}

	public int chiediNumeroGiocatori() {
		boolean OK = false;
		int risp;
		do{
			risp = aiutanteConsole.chiediIntero("In quanti giocate? [2-6]");
			if(risp<=6&&risp>=2){
				OK = true;
			} else {
				scrivi("Devi inserire un numero tra 2 e 6!");
			}
		}while(!OK);
			return risp;
	}

	public List<String> chiediNomi(int numeroGiocatori) {
		List<String> out = new LinkedList<String>();
		for (int i = 0; i < numeroGiocatori; i++) {
			out.add(aiutanteConsole
					.chiediStringa("Inserisci il nome del giocatore " + (i+1)));
		}
		return out;
	}

	public Scommessa chiediScommessa(String nomeGiocatore) {
		Scommessa scommessa;
		do {
			Colore col = aiutanteConsole.chiediEnum(nomeGiocatore +
					" su quale scuderia vuoi puntare?", Colore.class);
			int danariScommessi = aiutanteConsole
					.chiediIntero("Quanti danari vuoi puntare?");
			TipoScommessa tS = aiutanteConsole.chiediEnum(
					"Che tipo di scommessa vuoi fare?", TipoScommessa.class);

			scommessa = new Scommessa(tS, danariScommessi, col);
		} while (!aiutanteConsole
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
		pW
		.println("Attenzione, il Giocatore "
				+ id
				+ " "
				+ proprioNome
				+ " non ha più denaro da scommettere, nè punti vittoria da barattare!\n");
		scriviACapo("Mi dispiace " + proprioNome
				+ ". Sei stato eliminato!");
	}

	public boolean chiediConferma(String domanda, String nomeGiocatore) {
		return aiutanteConsole.chiediConferma(nomeGiocatore + " " + domanda);
	}

	public CartaAzione chiediCartaAzione(String nomeGiocatore,
			List<CartaAzione> carteAzionePossedute) {
		scriviACapo(nomeGiocatore
				+ " e' il tuo turno di giocare una carta azione!");
		return aiutanteConsole.chiediValoreLista("Quale vuoi giocare?",
				carteAzionePossedute);
	}

	public Colore chiediScuderia(String proprioNome) {
		return aiutanteConsole.chiediEnum("Su quale scuderia la vuoi giocare?",
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
		List<Colore> colori = new LinkedList<Colore>();
		for (Colore scuderia : sortedScuderie) {
			corsie.add(scuderie.get(scuderia));
			colori.add(scuderia);
		}
		aiutanteConsole.aggiornaCorsie(corsie, colori);
	}

	public List<Colore> risolviConflitto(
			List<Colore> scuderieInConflitto, String nomeGiocatore) {
		pW
		.println(nomeGiocatore
				+ " è sorto un dilemma: sta a te decidere l'ordine di arrivo di questi cavalli!");
		scriviACapo(scuderieInConflitto);

		List<Colore> ancoraDaDecidere;
		List<Colore> giaDecisi = new LinkedList<Colore>();
		do {
			ancoraDaDecidere = new LinkedList<Colore>(scuderieInConflitto);
			giaDecisi.clear();
			for (Colore scuderia : scuderieInConflitto) {
				Colore daAggiungere = aiutanteConsole.chiediValoreLista(
						"Inserisci il prossimo cavallo tra quelli rimasti: ",
						ancoraDaDecidere);
				ancoraDaDecidere.remove(daAggiungere);
				giaDecisi.add(daAggiungere);
			}

			scriviACapo("Ok questo è il nuovo ordine da te inserito: ");
			scriviACapo(giaDecisi);
		} while (!aiutanteConsole.chiediConferma("Va bene?"));
		return giaDecisi;
	}

	public static void main(String[] args) {
		View cV= new ConsoleView(System.in, new PrintWriter(System.out));
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
		scriviACapo(daScrivere);
	}

	private void scriviACapo(Object daScrivere) {
		pW.println(daScrivere);
	}

	public void mostraGiocatore(GiocatoreView gV) {
		pW.println(gV.getInformazioniGenerali());
	}

	public void aCapo() {
		pW.println("");
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AiutanteConsole {

	private final BufferedReader in;
	private final PrintStream outStream;
	
	public AiutanteConsole(InputStream in, PrintStream gC){
		this.in = new BufferedReader(
				new InputStreamReader(in));
		this.outStream = gC;
	}

	public String chiediStringa(String domanda) {
		outStream.println(domanda);
		return leggiStringa();
	}

	public int chiediIntero(String domanda) {
		boolean read = false;
		int risultato = 0;
		do {
			outStream.println(domanda);
			try {
				risultato = leggiIntero();
				read = true;
			} catch (NumberFormatException e) {
				outStream.println("Devi inserire un intero!");
			}
		} while (!read);
		return risultato;
	}

	private int leggiIntero() throws NumberFormatException {
		try {
			String read = in.readLine();
			assert(read!=null);
			return Integer.parseInt(read);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String leggiStringa() {
		try {
			return in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <T extends Enum<T>> T chiediEnum(String domanda,
			Class<T> enumClass) {
		List<T> enums = Arrays.asList(enumClass.getEnumConstants());
		Map<String, T> enumMap = new TreeMap<String, T>(
				String.CASE_INSENSITIVE_ORDER);

		for (T e : enums) {
			enumMap.put(e.name(), e);
		}

		boolean read = false;
		T risultato = null;
		do {
			risultato = enumMap.get(chiediStringa(domanda + "\nPossibili valori: "
					+ enums));
			if (risultato != null) {
				read = true;
			} else {
				outStream.println("Non hai inserito nessuno dei valori possibili!");
			}
		} while (!read);
		return risultato;
	}

	public boolean chiediConferma(String domanda) {
		String risp;
		Set<String> rispPositive = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER);
		Set<String> rispNegative = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER);

		rispPositive.add("si");
		rispPositive.add("s");
		rispNegative.add("no");
		rispNegative.add("n");

		boolean out = false;
		
		do {
			risp = chiediStringa(domanda);
			out = rispPositive.contains(risp);
		} while (!(out || rispNegative.contains(risp)));

		return out;
	}

	public <T> T chiediValoreLista(String domanda,
			List<T> lista) {
		outStream.println(domanda);
		Set<Integer> valoriPossibili = new TreeSet<Integer>();
		for(int i = 0; i < lista.size(); i++ ){
			outStream.println(".:::"+i+":::.");
			outStream.println(lista.get(i));
			valoriPossibili.add(i);
		}
		
		boolean ok = false;
		int risp;
		do{
			risp = chiediIntero("Inserisci numero corrispondente all'oggetto da selezionare:");
			if(valoriPossibili.contains(risp)){
				ok = true;
			} else {
				outStream.println("Non hai inserito un numero valido!");
			}
		}while(!ok);
		
		return lista.get(risp);
	}
	
	public void main(String[] args){
		List<Integer> posizioni = new LinkedList<Integer>();
		for(int i = 0; i < Math.random()*10+5; i ++){
			posizioni.add((int) (Math.random()*19));
		}
		//aggiornaCorsie(posizioni);
	}
	
	public void aggiornaCorsie(List<Integer> corsie, List<Colore> colori) {
		outStream.print("|");
		int j = 0;
		for(Integer posizioneScuderia : corsie){
			String abbrScud = colori.get(j).name().substring(0,3); 
			outStream.print("="+abbrScud+"=");
			j++;
		};
		outStream.print("|");
		outStream.println();
		for(int i = 0; i < 19; i++){
			outStream.print("|");
			for(Integer posizioneScuderia : corsie){
				String cavalloPresente = "___";
				if(i==0){
					cavalloPresente = "===";
				}
				if(i==12){
					cavalloPresente = "###";
				}
				if(posizioneScuderia==i){
					cavalloPresente = ":H:";
				}
				if(i==12){
					outStream.print("#"+cavalloPresente+"#");
				} else if(i==0){					
					outStream.print("*"+cavalloPresente+"*");
				} else {
					outStream.print("|"+cavalloPresente+"|");
				}
			}
			outStream.print("|");
			outStream.println();
		}
		outStream.print("|");
		for(Integer posizioneScuderia : corsie){
			outStream.print("=====");
		};
		outStream.print("|");
		outStream.println();
		
	}
	
}

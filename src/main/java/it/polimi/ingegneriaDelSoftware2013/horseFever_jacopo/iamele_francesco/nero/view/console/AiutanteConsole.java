package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AiutanteConsole {

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static String chiediStringa(String domanda) {
		System.out.println(domanda);
		return leggiStringa();
	}

	public static int chiediIntero(String domanda) {
		boolean read = false;
		int out = 0;
		do {
			System.out.println(domanda);
			try {
				out = leggiIntero();
				read = true;
			} catch (NumberFormatException e) {
				System.out.println("Devi inserire un intero!");
			}
		} while (!read);
		return out;
	}

	private static int leggiIntero() throws NumberFormatException {
		try {
			return Integer.parseInt(in.readLine());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String leggiStringa() {
		try {
			return in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T extends Enum<T>> T chiediEnum(String domanda,
			Class<T> enumClass) {
		List<T> enums = Arrays.asList(enumClass.getEnumConstants());
		Map<String, T> enumMap = new TreeMap<String, T>(
				String.CASE_INSENSITIVE_ORDER);

		for (T e : enums) {
			enumMap.put(e.name(), e);
		}

		boolean read = false;
		T out = null;
		do {
			out = enumMap.get(chiediStringa(domanda + "\nPossibili valori: "
					+ enums));
			if (out != null) {
				read = true;
			} else {
				System.out
				.println("Non hai inserito nessuno dei valori possibili!");
			}
		} while (!read);
		return out;
	}

	public static boolean chiediConferma(String domanda) {
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

	public static <T> T chiediValoreLista(String domanda,
			List<T> lista) {
		System.out.println(domanda);
		Set<Integer> valoriPossibili = new TreeSet<Integer>();
		for(int i = 0; i < lista.size(); i++ ){
			System.out.println(".:::"+i+":::.");
			System.out.println(lista.get(i));
			valoriPossibili.add(i);
		}
		
		boolean OK = false;
		int risp;
		do{
			risp = chiediIntero("Inserisci numero corrispondente all'oggetto da selezionare:");
			if(valoriPossibili.contains(risp)){
				OK = true;
			} else {
				System.out.println("Non hai inserito un numero valido!");
			}
		}while(!OK);
		
		return lista.get(risp);
	}
	
	public static void main(String[] args){
		List<Integer> posizioni = new LinkedList<Integer>();
		for(int i = 0; i < Math.random()*10+5; i ++){
			posizioni.add((int) (Math.random()*19));
		}
		aggiornaCorsie(posizioni);
	}
	
	public static void aggiornaCorsie(List<Integer> corsie) {
		System.out.print("|");
		for(Integer posizioneScuderia : corsie){
			System.out.print("=====");
		};
		System.out.print("|");
		System.out.println();
		for(int i = 0; i < 19; i++){
			System.out.print("|");
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
					System.out.print("#"+cavalloPresente+"#");
				} else if(i==0){					
					System.out.print("*"+cavalloPresente+"*");
				} else {
					System.out.print("|"+cavalloPresente+"|");
				}
			}
			System.out.print("|");
			System.out.println();
		}
		System.out.print("|");
		for(Integer posizioneScuderia : corsie){
			System.out.print("=====");
		};
		System.out.print("|");
		System.out.println();
		
	}
	
}

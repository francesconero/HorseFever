package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AiutanteConsole {

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static String chiediStringa(String domanda) throws IOException {
		System.out.println(domanda);
		return leggiStringa();
	}

	public static int chiediIntero(String domanda) throws IOException {
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

	private static int leggiIntero() throws NumberFormatException, IOException {
		return Integer.parseInt(in.readLine());
	}

	private static String leggiStringa() throws IOException {
		return in.readLine();
	}

	public static <T extends Enum<T>> T chiediEnum(String domanda,
			Class<T> enumClass) throws IOException {
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

	public static boolean chiediConferma(String domanda) throws IOException {
		String risp;
		Set<String> rispPositive = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER);
		Set<String> rispNegative = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER);

		rispPositive.add("si");
		rispPositive.add("s");
		rispNegative.add("no");
		rispNegative.add("n");

		boolean out;
		do {
			risp = chiediStringa(domanda);
			out = rispPositive.contains(risp);
		} while (!(out | rispNegative.contains(risp)));

		return !out;
	}
}

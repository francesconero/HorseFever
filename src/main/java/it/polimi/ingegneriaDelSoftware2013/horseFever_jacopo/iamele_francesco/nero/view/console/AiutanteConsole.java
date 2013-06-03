package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AiutanteConsole {

	private static BufferedReader in
	= new BufferedReader(new InputStreamReader(System.in));

	public static String chiediStringa(String domanda) throws IOException{
		System.out.println(domanda);
		return leggiStringa();
	}

	public static int chiediIntero(String domanda) throws IOException{
		boolean read = false;
		int out = 0;
		do{
			System.out.println(domanda);
			try{
				out = leggiIntero();
				read = true;
			} catch (NumberFormatException e){
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
}

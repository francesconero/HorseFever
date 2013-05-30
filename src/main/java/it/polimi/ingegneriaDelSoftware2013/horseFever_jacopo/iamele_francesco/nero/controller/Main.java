package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.*;

/**
 * Questa classe istanzia il controllore del
 * gioco; prende come parametro il numero di
 * giocatori.
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	try{
    	int numGiocatori=Integer.parseInt(args[0]);
    	ControlloreGioco controlloreGioco=new ControlloreGioco(numGiocatori);
    	controlloreGioco.inizia();
    	}
    	catch (NumErratoGiocatoriException e){
    		e.printStackTrace();
    		System.err.println("numero giocatori non valido");
    		
    	}
    	catch (NumberFormatException e){
    		e.printStackTrace();
    		System.err.println("l'argomento deve essere un numero intero");
    	} 
    	catch (CarteFiniteException e) {
			e.printStackTrace();
			System.err.println("carte esaurite");
		} 
    	catch (AttesaClientsFallitaException e) {
			e.printStackTrace();
			System.err.println("attesa client fallita");
		}
    	
    }
}

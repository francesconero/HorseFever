package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import java.io.IOException;

/**
 * Questa classe istanzia il controllore del
 * gioco; prende come parametro il numero di
 * giocatori.
 *
 */
public class ServerMain 
{
    public static void main( String[] args ) 
    {
    	
    	ControlloreFasiGioco controlloreGioco = null;
    	try{
    	int numGiocatori=Integer.parseInt(args[0]);
    	controlloreGioco=new ControlloreFasiGioco(numGiocatori);
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
    	catch (AttesaUtentiFallitaException e) {
			e.printStackTrace();
			System.err.println("attesa client fallita");
		}
    	catch (IOException e){
    		e.printStackTrace();
    		System.exit(-1);
    	}
    	catch (RuntimeException e){
    		e.printStackTrace();
    	}
    	finally {
    		System.out.println("Controllore gioco: "+controlloreGioco);
    		controlloreGioco.getControlloreRete().cleanUp();
    	}
    	
    }
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.GestoreEccezioni;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterministico;

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
    	Thread.currentThread().setUncaughtExceptionHandler(GestoreEccezioni.getInstance());
    	ControlloreFasiGioco controlloreGioco = null;
    	try{
    	int numGiocatori=Integer.parseInt(args[0]);
    	if(args.length>1){
    		String det = args[1];
    		if(det.equals("-d")){
    			controlloreGioco=new ControlloreFasiGioco(numGiocatori, new MazziereDeterministico(0));    			
    		}
    	}
    	if(controlloreGioco==null){
    		controlloreGioco=new ControlloreFasiGioco(numGiocatori, new Mazziere());    			    		
    	}
    	
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
    	
    }
}

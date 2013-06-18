package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.AttesaUtentiFallitaException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.CarteFiniteException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NumErratoGiocatoriException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.GestoreEccezioni;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterminato;
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
    	Thread.setDefaultUncaughtExceptionHandler(GestoreEccezioni.getInstance());
    	
    	ControlloreFasiGioco controlloreGioco = null;
    	try{
    	int numGiocatori;
		
    	if(args.length>0){
    	numGiocatori=Integer.parseInt(args[0]);
    	} else {
    	numGiocatori = 2;
    	}
		
    	if(args.length>1){
    		String det = args[1];
    		if(det.equals("-d")){
    			controlloreGioco=new ControlloreFasiGioco(numGiocatori, new MazziereDeterministico(0), new ControlloreReteServer());    			
    		}
    	}
    	
    	ControlloreReteServer server = new ControlloreReteServer();
    	if(controlloreGioco==null){
    		controlloreGioco=new ControlloreFasiGioco(numGiocatori, new MazziereDeterminato(), server);    			    		
    	}
    	
    	controlloreGioco.inizia();
    	server.stop();
    	} catch (AttesaUtentiFallitaException e) {
			throw new RuntimeException(e);
		} catch (CarteFiniteException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NumErratoGiocatoriException e) {
			throw new RuntimeException(e);
		}
    	
    }
}

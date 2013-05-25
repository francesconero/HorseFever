package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller;

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
    	int numGiocatori=Integer.parseInt(args[0]);
    	ControlloreGioco controlloreGioco=new ControlloreGioco(numGiocatori);
    	controlloreGioco.inizia();
    	
    }
}

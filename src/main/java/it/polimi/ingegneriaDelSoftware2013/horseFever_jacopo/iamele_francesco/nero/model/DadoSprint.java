package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model;
import java.util.Random;
/***
 * La classe DadoSprint 
 * @author Jacopo
 *
 */

public class DadoSprint {
	private int numfacce=6; //Dadisprint visti come d6
	private int risultato;
	
	public void lanciaDado()
							{
							Random generator=new Random();       //creo oggetto 'generator' di tipo random
							risultato=generator.nextInt(numfacce)+1;
							
							}
	
	public int getrisultato() 									
							{
								return risultato;
							}
	
	public Colore getColore(){
		switch (risultato){
		case 1: return (Colore.BIANCO); 
		case 2: return (Colore.BLU);
		case 3: return (Colore.GIALLO);
		case 4: return (Colore.NERO);
		case 5: return (Colore.ROSSO);
		case 6: return (Colore.VERDE);
		}
		
		return null;
		}
}

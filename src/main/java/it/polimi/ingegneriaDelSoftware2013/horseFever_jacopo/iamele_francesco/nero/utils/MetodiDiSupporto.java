package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MetodiDiSupporto
{
	
	public static <E> List<E> creaListaOrdinata(List<E> listaOriginale, E nuovoPrimoElemento) throws NoSuchElementException { // mi serve per gestire il primo giocatore
		ArrayList<E> listaOrdinata = new ArrayList<E>();
		int indice = listaOriginale.indexOf(nuovoPrimoElemento);
		
		if(indice == -1)
			throw new NoSuchElementException();
		
		if (indice == 0) return (listaOriginale);  //lista già ordinata
		
		for (int i = indice; i<listaOriginale.size(); i++){
			listaOrdinata.add(listaOriginale.get(i));
		}
		
		for(int i = 0; i < indice; i++){
			listaOrdinata.add(listaOriginale.get(i));
		}
		return listaOrdinata;
	}
	
	
}
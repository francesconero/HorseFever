package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.SwingUtilities;

public class MetodiDiSupporto
{
	
	public static <E> List<E> creaListaOrdinata(List<E> listaOriginale, E nuovoPrimoElemento) throws NoSuchElementException { // mi serve per gestire il primo giocatore
		ArrayList<E> listaOrdinata = new ArrayList<E>();
		int indice = listaOriginale.indexOf(nuovoPrimoElemento);
		
		if(indice == -1)
			throw new NoSuchElementException();
		
		if (indice == 0) return (listaOriginale);  //lista gi� ordinata
		
		for (int i = indice; i<listaOriginale.size(); i++){
			listaOrdinata.add(listaOriginale.get(i));
		}
		
		for(int i = 0; i < indice; i++){
			listaOrdinata.add(listaOriginale.get(i));
		}
		return listaOrdinata;
	}

	/**
	 * Wrapper per l'utilità invokeAndWait delle SwingUtilities. Per evitare deadlock è VIETATO chiamare questo metodo dall'EDT.
	 * 
	 * @param runnable la classe runnable da  eseguire sull'EDT.
	 */
	public static void swingInvokeAndWait(Runnable runnable) {
		if(EventQueue.isDispatchThread()){
			(new IllegalStateException("Hai chiamato questo metodo dall'EDT!")).printStackTrace();
			System.exit(-1);
		}
		try {
			SwingUtilities.invokeAndWait(runnable);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void dormi(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void nuovoThread(final Runnable runnable){ 
		new Thread(){
			public void run() {
				runnable.run();
			};
		}.start();
	}
	
	
}
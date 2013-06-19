package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.NetworkException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GestoreEccezioniGrafico implements UncaughtExceptionHandler {

	protected static GestoreEccezioniGrafico instance;

	protected GestoreEccezioniGrafico(){

	}

	public synchronized void uncaughtException(Thread arg0, final Throwable arg1) {
		arg1.printStackTrace();
		if(arg1 instanceof NetworkException){
			JOptionPane.showMessageDialog(null, "Errore di comunicazione col server", "ERROR", JOptionPane.ERROR_MESSAGE, null);
			System.exit(-1);
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				arg1.printStackTrace(pw);
				

				JOptionPane.showMessageDialog(null, sw.toString(), "ERROR", JOptionPane.ERROR_MESSAGE, null);
				System.exit(-1);		
			}
		});
	}
	
	public synchronized void handle(Throwable thrown){
		uncaughtException(Thread.currentThread(), thrown);
	}

	public synchronized static UncaughtExceptionHandler getInstance(){
		return instance==null ? new GestoreEccezioniGrafico() : instance;
	}

}

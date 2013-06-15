package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

public class GestoreEccezioniGrafico implements UncaughtExceptionHandler {

	protected static GestoreEccezioniGrafico instance;

	protected GestoreEccezioniGrafico(){

	}

	public synchronized void uncaughtException(Thread arg0, final Throwable arg1) {
		arg1.printStackTrace();
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {

			public void run() {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				arg1.printStackTrace(pw);
				sw.toString();

				JOptionPane.showMessageDialog(null, arg1, "ERROR", JOptionPane.ERROR_MESSAGE, null);
				System.exit(-1);		
			}
		});
	}

	public static GestoreEccezioniGrafico getInstance(){
		return instance==null ? new GestoreEccezioniGrafico() : instance;
	}

}

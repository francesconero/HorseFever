package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.GestoreEccezioniServer;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterminato;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MazziereDeterministico;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs.LauncherFrame;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.GraphicalConsole;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Questa classe istanzia il controllore del
 * gioco; prende come parametro il numero di
 * giocatori.
 *
 */
public class ServerMain implements WindowListener {
	
	private ControlloreFasiGioco controlloreGioco;
	private GraphicalConsole gC;
	private LauncherFrame listener;
	
	public ServerMain(){
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {			
			public void run() {
				ServerMain.this.gC = new GraphicalConsole(false);
				gC.getFrame().addWindowListener(ServerMain.this);
				System.setOut(gC);
				gC.show();
			}
		});
		
	}

	public void start(String[] args){
		controlloreGioco = null;
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

		Thread.currentThread().setUncaughtExceptionHandler(GestoreEccezioniServer.getInstance());
		GestoreEccezioniServer.getInstance().setServer(controlloreGioco);
		controlloreGioco.inizia();

		System.out.println("Server chiuso");
	}

	public static void main( String[] args ){
		ServerMain server = new ServerMain();
		server.start(args);
	}
	
	public JFrame getFrame(){
		return gC.getFrame();
	}

	public void close() {
		controlloreGioco.chiudi();
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		controlloreGioco.chiudi();
		if(listener!=null){
			listener.avvertiChiusura();
		}
	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addListener(LauncherFrame launcherFrame) {
		this.listener = launcherFrame;
	}
}

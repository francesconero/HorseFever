package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenteSingolo;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.FramePrincipale;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.FramePrincipaleObserver;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.ConnettendoFrame;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.InfoDialog;

import javax.swing.JOptionPane;

public class ControlloreGrafica implements FramePrincipaleObserver {

	private final FramePrincipale framePrincipale;
	private final ControlloreUtenteSingolo controlloreUtenteSingolo;
	private boolean iniziato = true;

	public ControlloreGrafica(){
		Thread.setDefaultUncaughtExceptionHandler(GestoreEccezioniGrafico.getInstance());
		System.setProperty("sun.awt.exception.handler", GestoreEccezioniGrafico.class.getName());
		
		InfoDialog d = new InfoDialog();
		final ConnettendoFrame connettendo = new ConnettendoFrame(d.getIndirizzoIP());

		try {
			this.controlloreUtenteSingolo = new ControlloreReteClient(d.getNome(), d.getIndirizzoIP());
			controlloreUtenteSingolo.collegaGioco();
		} catch (Exception e) {
			MetodiDiSupporto.swingInvokeAndWait(new Runnable() {				
				public void run() {
					connettendo.chiudi();
					JOptionPane.showMessageDialog(null, "Errore durante la connessione al server!", "Attenzione!", JOptionPane.ERROR_MESSAGE);
				}
			});			
			throw new RuntimeException(e);
		}		
		connettendo.chiudi();

		this.framePrincipale = new FramePrincipale();
		framePrincipale.aggiungiObserver(this);
		
		while(iniziato){
			framePrincipale.aggiorna(controlloreUtenteSingolo.riceviStatoDelGioco());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		framePrincipale.chiudi();
	}

	public void scommessa(Scuderia scuderia, int danari,
			TipoScommessa tipoScommessa) {
		// TODO Auto-generated method stub

	}

	public void giocaCartaAzione(CartaAzione carta, Scuderia scuderia) {
		// TODO Auto-generated method stub

	}

	public void pronto() {

	}

	public static void main(String[] args){
		new ControlloreGrafica();
	}
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.Utente;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ControlloreReteClient;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.ControlloreFramePrincipale;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.FramePrincipaleObserver;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs.ConnettendoFrame;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs.InfoDialog;

import java.net.InetAddress;
import java.util.List;

import javax.swing.JOptionPane;

public class ControlloreGrafica implements FramePrincipaleObserver {

	private ControlloreFramePrincipale framePrincipale;
	private Utente controlloreUtenteSingolo;
	private StatoDelGiocoView ultimoStatoRicevuto;

	public ControlloreGrafica(){
		Thread.setDefaultUncaughtExceptionHandler(GestoreEccezioniGrafico.getInstance());
		System.setProperty("sun.awt.exception.handler", GestoreEccezioniGrafico.class.getName());
	}

	public void scommessa(Scommessa scommessa) {
			framePrincipale.risultatoScommessa(controlloreUtenteSingolo.scommetti(scommessa));
	}

	public void giocaCartaAzione(CartaAzione carta, Scuderia scuderia) {
		controlloreUtenteSingolo.posizionaCarta(new PosizionaCarta(carta, scuderia));
	}

	public void pronto() {

	}

	public static void main(String[] args){
		new ControlloreGrafica();
	}
	
	public void stessoAggiornamento() {
		framePrincipale.aggiorna(ultimoStatoRicevuto);
	}

	public void prossimoAggiornamento() {
		ultimoStatoRicevuto = controlloreUtenteSingolo.riceviStatoDelGioco();
		framePrincipale.aggiorna(ultimoStatoRicevuto);
	}

	public void risolviConflitto(List<Colore> soluzioneConflitto) {
		controlloreUtenteSingolo.risolviConflitto(soluzioneConflitto);
		prossimoAggiornamento();
	}

	public void finePartita() {
		framePrincipale.chiudi();
		controlloreUtenteSingolo.scollegaGioco();
		System.exit(0);
	}
	
	public void show(InetAddress localHost){
		String nome = JOptionPane.showInputDialog("Inserisci il tuo nome:");
		final ConnettendoFrame connettendo = new ConnettendoFrame(localHost.getHostAddress());

		try {
			this.controlloreUtenteSingolo = new ControlloreReteClient(nome, localHost.getHostAddress());
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

		this.framePrincipale = new ControlloreFramePrincipale();
		framePrincipale.settaObserver(this);
		framePrincipale.show();
		
		ultimoStatoRicevuto = controlloreUtenteSingolo.riceviStatoDelGioco();
		framePrincipale.aggiorna(ultimoStatoRicevuto);
	}

	public void show() {
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

		this.framePrincipale = new ControlloreFramePrincipale();
		framePrincipale.settaObserver(this);
		framePrincipale.show();
		
		ultimoStatoRicevuto = controlloreUtenteSingolo.riceviStatoDelGioco();
		framePrincipale.aggiorna(ultimoStatoRicevuto);
		if(ultimoStatoRicevuto.getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.VITTORIA)){
			controlloreUtenteSingolo.scollegaGioco();
		}
	}

	public void riceviAvvertimento() {
		String avvertimento = controlloreUtenteSingolo.riceviAvvertimento();
		framePrincipale.avverti(avvertimento);
	}
	
}

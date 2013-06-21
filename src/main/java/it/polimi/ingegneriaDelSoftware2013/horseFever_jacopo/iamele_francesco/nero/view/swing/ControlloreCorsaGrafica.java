package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.MossaCorsaVisitor;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica.ControlloreGrafica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Classifica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Conflitto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.FineGara;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Movimento;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Partenza;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Photofinish;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.Sprint;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers.TabellonePanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs.ConflittoDialog;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.InformazioniDiGiocoModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;


public class ControlloreCorsaGrafica implements MossaCorsaVisitor, PropertyChangeListener{
	
	private StatoDelGiocoView ultimoAggiornamento;
	private ControlloreGrafica osservatore;
	private TabellonePanel tabellonePanel;
	private InformazioniDiGiocoModel informazioniDiGioco;
	private Timer delayTimer;
	
	public ControlloreCorsaGrafica(StatoDelGiocoView ultimoAggiornamento, ControlloreGrafica observer, TabellonePanel tabellonePanel, InformazioniDiGiocoModel informazioniDiGioco){
		this.ultimoAggiornamento = ultimoAggiornamento;
		this.osservatore = observer;
		this.tabellonePanel = tabellonePanel;
		this.tabellonePanel.addPropertyChangeListener(this);
		this.informazioniDiGioco = informazioniDiGioco;
	}

	public void controlla() {
		delayTimer = new Timer(100, new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				prossimaMossa();
			}
		});		
		delayTimer.setInitialDelay(1500);
		delayTimer.setRepeats(false);
		delayTimer.start();
	}

	protected void prossimaMossa() {
		List<MossaCorsa> lista = ultimoAggiornamento.getMosseCorsa();
		if(!lista.isEmpty()){
			lista.remove(0).accept(this);
		}
	}

	public void visita(Movimento movimento) {
		informazioniDiGioco.addInformazione(movimento.getCommento());
		tabellonePanel.aggiornaPosizioni(movimento.getNuovePosizioni());
	}

	public void visita(Sprint sprint) {
		String commento = sprint.getCommento();
		if(commento.isEmpty()){
			commento = "Nessun cavallo ha sprintato!";
		}
		informazioniDiGioco.addInformazione(commento);
		tabellonePanel.aggiornaPosizioni(sprint.getNuovePosizioni());
	}

	public void visita(Photofinish photofinish) {
		informazioniDiGioco.addInformazione(photofinish.getCommento());
		delayTimer.stop();
		controlla();
	}

	public void visita(Conflitto conflitto) {
		informazioniDiGioco.addInformazione(conflitto.getCommento());
		informazioniDiGioco.addInformazione("Attenzione! Non si riesce a capire quale cavallo sia arrivato prima!");
		if(ultimoAggiornamento.getPrimoGiocatore().equals(ultimoAggiornamento.getMioGiocatore())){
			informazioniDiGioco.addInformazione("Sei il primo giocatore, quindi spetta a te l'ardua sentenza!");
			ConflittoDialog cD = new ConflittoDialog(conflitto.getScuderieInConflitto());
			final List<Colore> soluzione = cD.showDialog();
			MetodiDiSupporto.nuovoThread(new Runnable() {
				public void run() {
					osservatore.risolviConflitto(soluzione);
				}
			});			
		} else {
			informazioniDiGioco.addInformazione("Attendi che "+ ultimoAggiornamento.getPrimoGiocatore().getNomeUtente()+" stabilisca l'ordine di arrivo!");
			JOptionPane.showMessageDialog(tabellonePanel, "Attendi che : "+ ultimoAggiornamento.getPrimoGiocatore().getNomeUtente()+" stabilisca l'ordine di arrivo!", "Attendi", JOptionPane.INFORMATION_MESSAGE);
			MetodiDiSupporto.nuovoThread(new Runnable() {
				public void run() {
					osservatore.prossimoAggiornamento();
				}
			});
		}
		delayTimer.stop();		
	}

	public void visita(Classifica classifica) {
		informazioniDiGioco.addInformazione(classifica.getCommento());
		tabellonePanel.aggiornaClassifica(classifica.getClassifica());
	}

	public void visita(FineGara fineGara) {
		informazioniDiGioco.addInformazione(fineGara.getCommento());
		JOptionPane.showMessageDialog(tabellonePanel, "La gara è finita, è il momento di pagare le scommesse!", "Termine gara", JOptionPane.INFORMATION_MESSAGE);
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.prossimoAggiornamento();
			}
		});
		delayTimer.stop();
	}

	public void visita(Partenza partenza) {
		informazioniDiGioco.addInformazione("Ecco che i cavalli sono partiti!");
		tabellonePanel.aggiornaPosizioni(partenza.getNuovePosizioni());
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		if(arg0.getSource().equals(tabellonePanel)){
			if(arg0.getPropertyName().equals("ANIMATION_STOPPED")){
				delayTimer.stop();
				controlla();
			}
		}
	}

}

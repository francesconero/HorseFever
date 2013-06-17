package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;

import java.awt.CardLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

public class AttivitaPanel extends JTabbedPane implements PropertyChangeListener {

	private CardLayout cl;
	private ManoPanel manoPanel;
	private ScuderiePanel scuderiaPanel;
	private SelezionaCartaPanel selezionaCartaPanel;
	private JPanel panel;
	private ScommesseEffettuatePanel scommesseEffettuatePanel;

	/**
	 * Create the panel.
	 */
	public AttivitaPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
	    
	    panel = new JPanel();
	    addTab("Attivit\u00E0", null, panel, null);
	    panel.setLayout(new CardLayout(0, 0));
	    
	    selezionaCartaPanel = new SelezionaCartaPanel();
	    panel.add(selezionaCartaPanel, "selezionaCartaPanel");
	    
	    scuderiaPanel = new ScuderiePanel();
	    panel.add(scuderiaPanel, "scuderiaPanel");
	    
	    manoPanel = new ManoPanel();
	    panel.add(manoPanel, "manoPanel");

	    cl = (CardLayout) panel.getLayout();
	    cl.show(panel, "scuderiaPanel");
	    
	    manoPanel.addPropertyChangeListener(this);
	    scuderiaPanel.addPropertyChangeListener(this);
	    selezionaCartaPanel.addPropertyChangeListener(this);
	    
	    scommesseEffettuatePanel = new ScommesseEffettuatePanel();
	    addTab("Scommesse Effettuate", null, scommesseEffettuatePanel, null);
	}

	public void showManoPanel(GiocatoreView newValue) {
		cl.show(panel, "manoPanel");
		manoPanel.aggiorna(newValue);
	}

	public void showScuderiePanel(Scuderia newValue) {
		cl.show(panel, "scuderiaPanel");
		scuderiaPanel.aggiorna(newValue);
	}

	public void aggiorna(boolean mioTurnoPrimaScommessa, boolean mioTurnoAlterazione, boolean mioTurnoSecondaScommessa, List<GiocatoreView> scommesseFatte) {
		scuderiaPanel.enableTruccamento(mioTurnoAlterazione);
		scuderiaPanel.enableScommessa(mioTurnoPrimaScommessa||mioTurnoSecondaScommessa);
		scuderiaPanel.enableSalta(mioTurnoSecondaScommessa);
		scommesseEffettuatePanel.aggiorna(scommesseFatte);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().equals(scuderiaPanel)){
			if(evt.getPropertyName().equals("SCOMMESSA")){
				firePropertyChange("SCOMMESSA", null, evt.getNewValue());
			}
			if(evt.getPropertyName().equals("TRUCCAMENTO")){
				firePropertyChange("INIZIO_TRUCCAMENTO", null, evt.getNewValue());
			}
		}
		if(evt.getSource().equals(selezionaCartaPanel)){
			if(evt.getPropertyName().equals("TRUCCAMENTO")){
				firePropertyChange("FINE_TRUCCAMENTO", null, evt.getNewValue());
			}
		}
	}

	public void showSelezionaCarta(GiocatoreView mioGiocatore) {
		cl.show(panel, "selezionaCartaPanel");
		selezionaCartaPanel.aggiorna(mioGiocatore);
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AttivitaPanel extends JPanel {

	private CardLayout cl;
	private ManoPanel manoPanel;
	private ScuderiePanel scuderiaPanel;

	/**
	 * Create the panel.
	 */
	public AttivitaPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(new CardLayout(0, 0));
		
		manoPanel = new ManoPanel();
		add(manoPanel, "manoPanel");
		
		scuderiaPanel = new ScuderiePanel();
		add(scuderiaPanel, "scuderiaPanel");
		
		cl = (CardLayout) getLayout();
	    cl.show(this, "scuderiaPanel");
	}

	public void mano(GiocatoreView newValue) {
		cl.show(this, "manoPanel");
		manoPanel.aggiorna(newValue);
	}
	
	

}

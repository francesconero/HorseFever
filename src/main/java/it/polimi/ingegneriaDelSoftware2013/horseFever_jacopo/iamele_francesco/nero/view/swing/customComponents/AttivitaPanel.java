package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AttivitaPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AttivitaPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(new CardLayout(0, 0));
		
		ManoPanel manoPanel = new ManoPanel();
		add(manoPanel, "manoPanel");
		
		ScuderiePanel scuderiaPanel = new ScuderiePanel();
		add(scuderiaPanel, "scuderiaPanel");
		
		CardLayout cl = (CardLayout) getLayout();
	    cl.show(this, "scuderiaPanel");
	}
	
	

}

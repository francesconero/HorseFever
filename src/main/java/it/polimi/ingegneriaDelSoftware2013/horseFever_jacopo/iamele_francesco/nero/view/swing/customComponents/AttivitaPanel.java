package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;

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

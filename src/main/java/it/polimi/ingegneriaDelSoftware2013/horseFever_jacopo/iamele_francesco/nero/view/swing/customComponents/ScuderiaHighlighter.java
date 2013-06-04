package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ScuderiaHighlighter extends JPanel {

	/**
	 * Create the panel.
	 */
	public ScuderiaHighlighter() {
		setBorder(new LineBorder(new Color(255, 0, 0), 3));
		setOpaque(false);
	}

}

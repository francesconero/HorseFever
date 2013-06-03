package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

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

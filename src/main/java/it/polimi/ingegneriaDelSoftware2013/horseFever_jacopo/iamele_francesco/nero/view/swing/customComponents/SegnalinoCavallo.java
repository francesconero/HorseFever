package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SegnalinoCavallo extends JPanel {

	private int border = 2;

	/**
	 * Create the panel.
	 */
	public SegnalinoCavallo() {
		setOpaque(false);
		setForeground( new Color(0, 0, 0, 255) );
		setBackground( new Color(255, 0, 0, 255) );
	}
	
@Override
public void paintComponent(Graphics g) {
	g.setColor( Color.BLACK );
    g.fillOval(0, 0, getWidth(), getHeight());
    g.setColor( Color.WHITE );
    g.fillOval(border, border , getWidth()-border*2, getHeight()-border*2);
    g.setColor( getBackground() );
    g.fillOval(border*2, border*2, getWidth()-border*4, getHeight()-border*4);
   
    super.paintComponent(g);
}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1774034659075813491L;

	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);			
		g.drawImage(img, 0, 0, null);
	}

}

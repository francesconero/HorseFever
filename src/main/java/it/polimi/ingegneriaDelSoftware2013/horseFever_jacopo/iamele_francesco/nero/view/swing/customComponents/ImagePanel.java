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
		ImageIcon i = new ImageIcon(img);
		this.img = i.getImage();
		Dimension size = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);			
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
	

}

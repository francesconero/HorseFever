package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ScuderiaHighlighter extends JPanel {

	private Point2D.Float posizioneTabellone;
	private Point2D.Float dimensione = new Point2D.Float(0.13600f, 0.14705f);
	
	/**
	 * Create the panel.
	 */
	public ScuderiaHighlighter() {
		setBorder(new LineBorder(new Color(255, 0, 0), 3));
		setOpaque(false);
	}

	public Point2D.Float getPosizioneTabellone() {
		return posizioneTabellone;
	}

	public void setPosizioneTabellone(Point2D.Float posizioneTabellone) {
		this.posizioneTabellone = posizioneTabellone;
	}

	public Point2D.Float getDimensione() {
		return dimensione;
	}

	public void setDimensione(Point2D.Float dimensione) {
		this.dimensione = dimensione;
	}

	public void setPosizioneTabellone(Point location, Point point) {
		this.posizioneTabellone = new Point2D.Float(location.x/(float)point.x, location.y/(float)point.y);
	}

}

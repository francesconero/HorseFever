package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public final class ScuderiaHighlighter extends JPanel {

	private Point2D.Float posizioneTabellone;
	private Point2D.Float dimensione = new Point2D.Float(0.13600f, 0.14705f);
	private boolean selezionato = false;
	private final Colore colore;
	
	/**
	 * Create the panel.
	 */
	public ScuderiaHighlighter(Colore colore) {
		setBorder(new LineBorder(new Color(255, 0, 0), 3));
		setOpaque(false);
		seleziona(false);
		this.colore = colore;
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

	public void seleziona(boolean selezionato) {
		this.selezionato = selezionato;
		if(selezionato){
			setBorder(new LineBorder(Color.RED, 4));
		} else {
			setBorder(null);
		}
		revalidate();
	}

	public boolean isSelected() {
		return selezionato;
	}

	public Colore getColore() {
		return colore;
	}

}

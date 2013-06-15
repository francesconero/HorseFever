package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class SegnalinoCavallo extends JPanel {

	private Animatable animatable = new Animatable();
	private int border = 1;
	private Point2D.Float casellaIniziale = new Point2D.Float(0, 0);
	private Point2D.Float casellaFinale  = new Point2D.Float(0, 0);
	private int posizioneCorsa = 0;
	private double scalingFactorDimension = 0.97;

	private Colore colore;
	private Point2D.Float dimensioneClassifica = new Point2D.Float(0.028f,0.0195f);
	/**
	 * Create the panel.
	 */
	public SegnalinoCavallo(Colore colore) {
		this.colore = colore;
		setOpaque(false);
		setForeground( new Color(0, 0, 0, 255) );
		setBackground( new Color(255, 0, 0, 255) );
		setPosizioneCorsa(0);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor( Color.DARK_GRAY );
		g.fillOval(0, 0, getWidth(), getHeight());
		g.setColor( Color.BLACK );
		g.fillOval(0, 0, getWidth()-2, getHeight()-2);
		g.setColor( Color.WHITE );
		g.fillOval(border, border , getWidth()-border*2-2, getHeight()-border*2-2);
		g.setColor( getBackground() );
		g.fillOval(border*2, border*2, getWidth()-border*4-2, getHeight()-border*4-2);
		super.paintComponent(g);
	}

	public void classifica(int posizione){
		switch(posizione){
			case 1:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.04117f));
				break;
			case 2:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.06588f));
				break;
			case 3:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.09059f));
				break;
			case 4:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.11294f));
				break;
			case 5:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.13765f));
				break;
			case 6:
				animatable.setPosizioneTarget(new Point2D.Float(0.70420f, 0.16118f));
				break;
				default:
					throw new IllegalArgumentException("Posizione classifica: "+ posizione);
		}
		animatable.setDimensioneTarget(AnimationHelper.clonaPunto(dimensioneClassifica));		
	}
	
	public Point2D.Float getCasellaIniziale() {
		return casellaIniziale;
	}

	public void setCasellaIniziale(Point2D.Float casellaIniziale) {
		this.casellaIniziale = AnimationHelper.clonaPunto(casellaIniziale);
	}

	public Point2D.Float getCasellaFinale() {
		return casellaFinale;
	}

	public void setCasellaFinale(Point2D.Float casellaFinale) {
		this.casellaFinale = AnimationHelper.clonaPunto(casellaFinale);
	}

	public int getPosizioneCorsa() {
		return posizioneCorsa;
	}

	public void setPosizioneCorsa(int posizioneCorsa) {
		if(posizioneCorsa==18){
			posizioneCorsa = 18;
			animatable.setPosizioneTarget(AnimationHelper.clonaPunto(casellaFinale));
			animatable.setDimensioneTarget(new Point2D.Float((float)(animatable.getDimensioneIniziale().x* Math.pow(scalingFactorDimension , posizioneCorsa)), (float)(animatable.getDimensioneIniziale().y* Math.pow(scalingFactorDimension , posizioneCorsa))));
			return;
		}

		float nuovaY = 0;

		switch (posizioneCorsa) {
		case 0:
			nuovaY = casellaIniziale.y;
			break;
		case 1:
			nuovaY = casellaIniziale.y-0.045f;
			break;
		case 2:
			nuovaY = casellaIniziale.y-0.09f;
			break;
		case 3:
			nuovaY = casellaIniziale.y-0.135f;
			break;
		case 4:
			nuovaY = casellaIniziale.y-0.18f;
			break;
		case 5:
			nuovaY = casellaIniziale.y-0.22f;
			break;
		case 6:
			nuovaY = casellaIniziale.y-0.255f;
			break;
		case 7:
			nuovaY = casellaIniziale.y-0.29f;
			break;
		case 8:
			nuovaY = casellaIniziale.y-0.32f;
			break;
		case 9:
			nuovaY = casellaIniziale.y-0.35f;
			break;
		case 10:
			nuovaY = casellaIniziale.y-0.38f;
			break;
		case 11:
			nuovaY = casellaIniziale.y-0.41f;
			break;
		case 12:
			nuovaY = casellaIniziale.y-0.44f;
			break;
		case 13:
			nuovaY = casellaIniziale.y-0.47f;
			break;
		case 14:
			nuovaY = casellaIniziale.y-0.50f;
			break;
		case 15:
			nuovaY = casellaIniziale.y-0.53f;
			break;
		case 16:
			nuovaY = casellaIniziale.y-0.555f;
			break;
		case 17:
			nuovaY = casellaIniziale.y-0.58f;
			break;
		default:
			break;
		}
		this.posizioneCorsa = posizioneCorsa;

		float x2mx1 = casellaFinale.x - casellaIniziale.x;
		float y2my1 = casellaFinale.y - casellaIniziale.y;
		float ymy1 = nuovaY - casellaIniziale.y;
		float nuovaX = casellaIniziale.x + (((ymy1 / y2my1)) * x2mx1);
		animatable.setPosizioneTarget(new Point2D.Float(nuovaX, nuovaY));
		animatable.setDimensioneTarget(new Point2D.Float((float)(animatable.getDimensioneIniziale().x* Math.pow(scalingFactorDimension , posizioneCorsa)), (float)(animatable.getDimensioneIniziale().y* Math.pow(scalingFactorDimension , posizioneCorsa))));
	}

	public Animatable getAnimatable() {
		return animatable;
	}

	public Colore getColore() {
		return colore;
	}

}

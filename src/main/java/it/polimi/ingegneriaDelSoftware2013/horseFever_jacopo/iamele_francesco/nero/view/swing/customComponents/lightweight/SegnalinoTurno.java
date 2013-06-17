package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.Animatable;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.AnimatableComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class SegnalinoTurno extends JPanel implements AnimatableComponent {

	private Animatable animatable = new Animatable();
	private int border = 3;
	
	public SegnalinoTurno(){
		animatable.setPosizioneAttuale(new Point2D.Float(0.05042f, 0.14353f));
		animatable.setPosizioneTarget(new Point2D.Float(0.05042f, 0.14353f));
		animatable.setDimensioneAttuale(new Point2D.Float(0.050f, 0.035f));
		animatable.setDimensioneTarget(new Point2D.Float(0.050f, 0.035f));
		setTurno(1);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D ig = (Graphics2D) g;
		ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor( Color.RED );
		ig.setStroke(new BasicStroke(border));
		g.drawOval(border/2, border/2, getWidth()-border, getHeight()-border);
		

		super.paintComponent(g);
	}
	
	public void setTurno(int turno){
		switch(turno){
			case 1:
				animatable.setPosizioneTarget(new Point2D.Float(0.0504f, 0.14353f));
				break;
			case 2:
				animatable.setPosizioneTarget(new Point2D.Float(0.0504f, 0.183f));
				break;
			case 3:
				animatable.setPosizioneTarget(new Point2D.Float(0.0504f, 0.217f));
				break;
			case 4:
				animatable.setPosizioneTarget(new Point2D.Float(0.105f, 0.14353f));
				break;
			case 5:
				animatable.setPosizioneTarget(new Point2D.Float(0.105f, 0.183f));
				break;
			case 6:
				animatable.setPosizioneTarget(new Point2D.Float(0.105f, 0.217f));
				break;
			default:
				throw new IllegalArgumentException("Turno non esistente: " + turno);
		}
	}

	public Animatable getAnimatable() {
		return animatable;
	}

}

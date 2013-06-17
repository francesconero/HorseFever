package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other;

import java.awt.geom.Point2D;

public class Animatable {
	private Point2D.Float posizioneAttuale = new Point2D.Float(0.10f, 0.10f);
	private Point2D.Float posizionePartenza = new Point2D.Float(0.10f, 0.10f);
	private Point2D.Float dimensioneIniziale = new Point2D.Float(0.06f,0.020f);
	private Point2D.Float dimensioneAttuale = AnimationHelper.clonaPunto(dimensioneIniziale);
	private Point2D.Float dimensioneTarget = AnimationHelper.clonaPunto(dimensioneIniziale);
	private Point2D.Float dimensionePartenza = AnimationHelper.clonaPunto(dimensioneIniziale);
	private Point2D.Float posizioneTarget = new Point2D.Float(0f,0f);

	private int step = 0;
	
	public Point2D.Float getPosizioneAttuale() {
		return posizioneAttuale;
	}

	public void setPosizioneAttuale(Point2D.Float posizioneAttuale) {
		this.posizioneAttuale = AnimationHelper.clonaPunto(posizioneAttuale);
	}

	public Point2D.Float getDimensione() {
		return dimensioneAttuale;
	}

	private void setDimensione(Point2D.Float dimensione) {
		this.dimensioneIniziale = dimensione;
	}

	public Point2D.Float getDimensioneAttuale() {
		return dimensioneAttuale;
	}

	public void setDimensioneAttuale(Point2D.Float dimensioneAttuale) {
		this.dimensioneAttuale = dimensioneAttuale;
	}

	public Point2D.Float getPosizioneTarget() {
		return posizioneTarget;
	}

	public void setPosizioneTarget(Point2D.Float posizioneTarget) {
		step = 0;
		this.posizionePartenza = AnimationHelper.clonaPunto(posizioneAttuale);
		this.posizioneTarget = AnimationHelper.clonaPunto(posizioneTarget);
	}

	public Point2D.Float getDimensioneTarget() {
		return dimensioneTarget;
	}

	public void setDimensioneTarget(Point2D.Float dimensioneTarget) {
		this.dimensioneTarget = AnimationHelper.clonaPunto(dimensioneTarget);
		this.dimensionePartenza = AnimationHelper.clonaPunto(dimensioneAttuale);
	}

	public boolean step(){
		int frames = 60;
		if(step<frames){
			AnimationHelper.step(posizioneTarget, posizionePartenza, dimensioneTarget, dimensionePartenza, step, dimensioneAttuale, posizioneAttuale, frames);
			step++;
			return false;
		} else {
			setPosizioneTarget(AnimationHelper.clonaPunto(posizioneAttuale));
			setDimensioneTarget(AnimationHelper.clonaPunto(dimensioneAttuale));
			return true;
		}
	}

	public Point2D.Float getPosizionePartenza() {
		return posizionePartenza;
	}

	public void setPosizionePartenza(Point2D.Float posizionePartenza) {
		this.posizionePartenza = posizionePartenza;
	}

	public Point2D.Float getDimensioneIniziale() {
		return dimensioneIniziale;
	}

	public void reset() {
		step = 0;
	}
	
}

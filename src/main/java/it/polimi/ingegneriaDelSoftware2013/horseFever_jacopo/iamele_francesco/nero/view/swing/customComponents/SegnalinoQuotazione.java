package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.awt.Color;
import java.awt.geom.Point2D;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;


public class SegnalinoQuotazione extends ImagePanel {

	private Colore colore;
	private int quotazione;
	private Animatable animatable = new Animatable();

	public Animatable getAnimatable() {
		return animatable;
	}

	public SegnalinoQuotazione(Colore colore) {
		super(ritornaStringaAssociata(colore));		
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new LineBorder(new Color(255, 255, 255))));
		this.colore = colore;
		animatable.setDimensioneAttuale(new Point2D.Float(0.03025f, 0.02117f));
		animatable.setDimensioneTarget(new Point2D.Float(0.03025f, 0.02117f));
		float y = 0.04117f;
		switch (colore) {
			case BIANCO:
				animatable.setPosizioneAttuale(new Point2D.Float(0.52436f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.52436f, y));
				break;
			case BLU:
				animatable.setPosizioneAttuale(new Point2D.Float(0.34454f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.34454f, y));
				break;
			case GIALLO:
				animatable.setPosizioneAttuale(new Point2D.Float(0.48235f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.48235f, y));
				break;
			case NERO:
				animatable.setPosizioneAttuale(new Point2D.Float(0.30252f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.30252f, y));
				break;
			case ROSSO:
				animatable.setPosizioneAttuale(new Point2D.Float(0.44f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.44f, y));
				break;
			case VERDE:
				animatable.setPosizioneAttuale(new Point2D.Float(0.38655f, y));
				animatable.setPosizioneTarget(new Point2D.Float(0.38655f, y));
				break;
			default:
				break;
			
		}
	}
	
	public Colore getColore(){
		return colore;
	}
	
	public void setQuotazione(int nuovaQuotazione){
		switch (nuovaQuotazione) {
			case 2:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x, 0.04117f));
				break;
			case 3:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x, 0.06588f));
				break;
			case 4:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x,0.09059f));
				break;
			case 5:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x, 0.11294f));
				break;
			case 6:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x, 0.13765f));
				break;
			case 7:
				animatable.setPosizioneTarget(new Point2D.Float(animatable.getPosizioneTarget().x, 0.16118f));
				break;
			default:
				throw new IllegalArgumentException("Quotazione non esistente "+nuovaQuotazione);
		}
	}
	
	private static String ritornaStringaAssociata(Colore c){
		switch (c) {
			case BIANCO:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneBianco");
			case BLU:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneBlu");
			case GIALLO:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneGiallo");
			case NERO:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneNero");
			case ROSSO:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneRosso");
			case VERDE:
				return Risorse.getIInstance().getImmagine("SegnalinoQuotazioneVerde");
			default:
				throw new RuntimeException("Errore interno");
		}
	}	

}

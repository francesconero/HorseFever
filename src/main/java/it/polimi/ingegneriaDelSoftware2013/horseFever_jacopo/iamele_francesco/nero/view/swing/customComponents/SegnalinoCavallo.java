package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import javax.swing.JPanel;

public class SegnalinoCavallo extends JPanel {

	private int border = 1;
	private Point2D.Float casellaIniziale = new Point2D.Float(0, 0);
	private Point2D.Float casellaFinale  = new Point2D.Float(0, 0);;
	private Point2D.Float posizioneAttuale = new Point2D.Float(0.10f, 0.10f);
	private Point2D.Float posizionePartenza = new Point2D.Float(0.10f, 0.10f);
	private Point2D.Float dimensioneIniziale = new Point2D.Float(0.06f,0.020f);
	private Point2D.Float dimensioneAttuale = clonaPunto(dimensioneIniziale);
	private Point2D.Float dimensioneTarget = clonaPunto(dimensioneIniziale);
	private Point2D.Float dimensionePartenza = clonaPunto(dimensioneIniziale);
	private Point2D.Float posizioneTarget = new Point2D.Float(0f,0f);
	private int posizioneCorsa = 0;
	private int step = 0;
	private double scalingFactor = 0.9775;
	private double scalingFactorDimension = 0.97;

	/**
	 * Create the panel.
	 */
	public SegnalinoCavallo() {
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

	public Point2D.Float getCasellaIniziale() {
		return casellaIniziale;
	}

	public void setCasellaIniziale(Point2D.Float casellaIniziale) {
		this.casellaIniziale = clonaPunto(casellaIniziale);
	}

	public Point2D.Float getCasellaFinale() {
		return casellaFinale;
	}

	public void setCasellaFinale(Point2D.Float casellaFinale) {
		this.casellaFinale = clonaPunto(casellaFinale);
	}

	public int getPosizioneCorsa() {
		return posizioneCorsa;
	}

	public void setPosizioneCorsa(int posizioneCorsa) {
		if(posizioneCorsa==18){
			posizioneCorsa = 18;
			setPosizioneTarget(clonaPunto(casellaFinale));
			setDimensioneTarget(new Point2D.Float((float)(dimensioneIniziale.x* Math.pow(scalingFactorDimension , posizioneCorsa)), (float)(dimensioneIniziale.y* Math.pow(scalingFactorDimension , posizioneCorsa))));
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
		setPosizioneTarget(new Point2D.Float(nuovaX, nuovaY));
		setDimensioneTarget(new Point2D.Float((float)(dimensioneIniziale.x* Math.pow(scalingFactorDimension , posizioneCorsa)), (float)(dimensioneIniziale.y* Math.pow(scalingFactorDimension , posizioneCorsa))));
	}

	public Point2D.Float getPosizioneAttuale() {
		return posizioneAttuale;
	}

	public void setPosizioneAttuale(Point2D.Float posizioneAttuale) {
		this.posizioneAttuale = clonaPunto(posizioneAttuale);
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
		this.posizionePartenza = clonaPunto(posizioneAttuale);
		this.posizioneTarget = clonaPunto(posizioneTarget);
	}

	private Float clonaPunto(Float puntoDaClonare) {
		return new Point2D.Float(puntoDaClonare.x, puntoDaClonare.y);
	}

	public Point2D.Float getDimensioneTarget() {
		return dimensioneTarget;
	}

	public void setDimensioneTarget(Point2D.Float dimensioneTarget) {
		this.dimensioneTarget = clonaPunto(dimensioneTarget);
		this.dimensionePartenza = clonaPunto(dimensioneAttuale);
	}

	public boolean step(){
		int frames = 60;
		if(step<frames){
			float d = (float) posizioneTarget.distance(posizionePartenza);
			float sizeDX = dimensioneTarget.x - dimensionePartenza.x;
			float sizeDY = dimensioneTarget.y - dimensionePartenza.y;
			float littleStep = step/(float)(frames/2);
			if(littleStep < 1){
				littleStep = (float) (d/2*(myPow(littleStep, 3)));
			} else {
			littleStep -= 2;
			littleStep = (float) (d/2*(myPow(littleStep, 3) + 2));
			}
			float littleSizeStepX = step/(float)(frames/2);
			float littleSizeStepY = step/(float)(frames/2);
			if(littleSizeStepX < 1){
				littleSizeStepX = (float) (sizeDX/2*(Math.pow(littleSizeStepX, 3)));							
			} else {
			littleSizeStepX -=  2;
			littleSizeStepX = (float) (sizeDX/2*(myPow(littleSizeStepX, 3)+ 2));			
			}
			if(littleSizeStepY < 1){
				littleSizeStepY = (float) (sizeDY/2*(myPow(littleSizeStepY, 3)));							
			} else {
			littleSizeStepY -=  2;
			littleSizeStepY = (float) (sizeDY/2*(myPow(littleSizeStepY, 3)+ 2));			
			}
			dimensioneAttuale.x = littleSizeStepX + dimensionePartenza.x;
			dimensioneAttuale.y = littleSizeStepY + dimensionePartenza.y;
			Point2D.Float spostamento = new Point2D.Float();
			spostamento.x = posizioneTarget.x - posizionePartenza.x;
			spostamento.y = posizioneTarget.y - posizionePartenza.y;
			normalizza(spostamento);
			moltiplica(spostamento, littleStep);
			if(!java.lang.Double.isNaN(spostamento.x) && !java.lang.Double.isNaN(spostamento.y)){
				posizioneAttuale.x = spostamento.x + posizionePartenza.x;
				posizioneAttuale.y = spostamento.y + posizionePartenza.y;
			}
			step++;
			return false;
		} else {
			return true;
		}
	}

	private float myPow(float toFakeExponentiate, int i) {
		float out = toFakeExponentiate;
		for(int j = 0; j < i-1; j++){
			out  *= toFakeExponentiate;
		}
		return out;
	}

	private void moltiplica(Float spostamento, double littleStep) {
		spostamento.x *= littleStep;
		spostamento.y *= littleStep;
	}

	private void normalizza(Float spostamento) {
		Point2D spostamentoTemp = clonaPunto(spostamento);
		spostamento.x /= new Point2D.Float(0, 0).distance(spostamentoTemp );
		spostamento.y /= new Point2D.Float(0, 0).distance(spostamentoTemp);
	}

	public Point2D.Float getPosizionePartenza() {
		return posizionePartenza;
	}

	public void setPosizionePartenza(Point2D.Float posizionePartenza) {
		this.posizionePartenza = posizionePartenza;
	}

}

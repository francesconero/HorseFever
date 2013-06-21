package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other;

import java.awt.geom.Point2D;

public class AnimationHelper {
	
	private AnimationHelper(){
		
	}

	public static void step(Point2D.Float posizioneTarget, Point2D.Float posizionePartenza, Point2D.Float dimensioneTarget, Point2D.Float dimensionePartenza, float step, Point2D.Float dimensioneAttuale, Point2D.Float posizioneAttuale, int frames){
		float d = (float) posizioneTarget.distance(posizionePartenza);
		float sizeDX = dimensioneTarget.x - dimensionePartenza.x;
		float sizeDY = dimensioneTarget.y - dimensionePartenza.y;
		float littleStep = step/(frames/2f);
		if(littleStep < 1){
			littleStep = (float) (d/2*(myPow(littleStep, 3)));
		} else {
			littleStep -= 2;
			littleStep = (float) (d/2*(myPow(littleStep, 3) + 2));
		}
		float littleSizeStepX = step/(float)(frames/2f);
		float littleSizeStepY = step/(float)(frames/2f);
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
	}

	private static float myPow(float toFakeExponentiate, int i) {
		float out = toFakeExponentiate;
		for(int j = 0; j < i-1; j++){
			out  *= toFakeExponentiate;
		}
		return out;
	}

	private static void moltiplica(Point2D.Float spostamento, double littleStep) {
		spostamento.x *= littleStep;
		spostamento.y *= littleStep;
	}

	private static void normalizza(Point2D.Float spostamento) {
		Point2D.Float spostamentoTemp = clonaPunto(spostamento);
		spostamento.x /= new Point2D.Float(0, 0).distance(spostamentoTemp );
		spostamento.y /= new Point2D.Float(0, 0).distance(spostamentoTemp);
	}	

	public static Point2D.Float clonaPunto(Point2D.Float puntoDaClonare) {
		return new Point2D.Float(puntoDaClonare.x, puntoDaClonare.y);
	}

}

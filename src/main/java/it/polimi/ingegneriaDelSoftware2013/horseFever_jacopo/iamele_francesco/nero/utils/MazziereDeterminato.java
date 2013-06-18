package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

public class MazziereDeterminato extends Mazziere {

	@Override
	public void mischiaCarteAzione() {
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Friz Finden"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Rochelle Recherche"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Magna Velocitas"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Globus Obscuros"));
		carteAzione.add(0, Risorse.getInstance().getCartaAzione("Friz Finden"));
		carteAzione.add(1, Risorse.getInstance().getCartaAzione("Rochelle Recherche"));
		carteAzione.add(2, Risorse.getInstance().getCartaAzione("Magna Velocitas"));
		carteAzione.add(3, Risorse.getInstance().getCartaAzione("Globus Obscuros"));
		
	}
}

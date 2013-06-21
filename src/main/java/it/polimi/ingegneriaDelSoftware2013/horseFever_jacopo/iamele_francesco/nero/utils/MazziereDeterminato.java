package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

public class MazziereDeterminato extends Mazziere {

	@Override
	public void mischiaCarteAzione() {
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Serum Maleficum"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Globus Obscuros"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Felix Infernalis"));
		carteAzione.remove(Risorse.getInstance().getCartaAzione("Vigor Ferreum"));
		carteAzione.add(0, Risorse.getInstance().getCartaAzione("Serum Maleficum"));
		carteAzione.add(1, Risorse.getInstance().getCartaAzione("Globus Obscuros"));		
		carteAzione.add(2, Risorse.getInstance().getCartaAzione("Felix Infernalis"));		
		carteAzione.add(3, Risorse.getInstance().getCartaAzione("Vigor Ferreum"));		
	}
	
}

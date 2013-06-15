package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import static org.junit.Assert.assertEquals;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class RisorseTest {

	List<CartaAzione> carteAzioneOrdinate = new LinkedList<CartaAzione>();
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Magna Velocitas"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Fortuna Benevola"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Flagellum Folgoris"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Herba Magica"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("In Igni Veritas"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Fustis et Radix"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Vigor Ferreum"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Globus Obscuros"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Aqua Putrida"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Serum Maleficum"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Venenum Veneficum"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Mala Tempora"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("XIII"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Felix Infernalis"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Alfio Allibratore"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Steven Sting"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Friz Finden"));
		carteAzioneOrdinate.add(Risorse.getIInstance().getCartaAzione("Rochelle Recherche"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrdineCarteAzionet() {
		List<CartaAzione> carteAzioneDaTestare = Risorse.getIInstance().getCarteAzione();		
		assertEquals(carteAzioneOrdinate, carteAzioneDaTestare);
	}

}

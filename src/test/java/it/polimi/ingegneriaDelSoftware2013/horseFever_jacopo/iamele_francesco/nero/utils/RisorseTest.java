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
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Magna Velocitas"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Fortuna Benevola"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Flagellum Folgoris"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Herba Magica"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("In Igni Veritas"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Fustis et Radix"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Vigor Ferreum"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Globus Obscuros"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Aqua Putrida"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Serum Maleficum"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Venenum Veneficum"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Mala Tempora"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("XIII"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Felix Infernalis"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Alfio Allibratore"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Steven Sting"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Friz Finden"));
		carteAzioneOrdinate.add(Risorse.getInstance().getCartaAzione("Rochelle Recherche"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOrdineCarteAzionet() {
		List<CartaAzione> carteAzioneDaTestare = Risorse.getInstance().getCarteAzione();		
		assertEquals(carteAzioneOrdinate, carteAzioneDaTestare);
	}

}

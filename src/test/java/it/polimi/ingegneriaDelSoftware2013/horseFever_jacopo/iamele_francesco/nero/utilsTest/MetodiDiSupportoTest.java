package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utilsTest;

import static org.junit.Assert.assertEquals;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class MetodiDiSupportoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCreaListaOrdinata() {
		ArrayList<Integer> test1 = new ArrayList<Integer>();
		for (int i = 0; i<1024; i++)
		{
			test1.add(i);
		}
		
		assertEquals("La Lunghezza della lista passata deve essere uguale alla lista ritornata",test1.size(), MetodiDiSupporto.creaListaOrdinata(test1, test1.get(1000)).size());
		assertEquals("Il nuovoPrimoElemento deve essere il primo elemento",test1.get(1000),MetodiDiSupporto.creaListaOrdinata(test1, test1.get(1000)).get(0));
		assertEquals("Il nuovoPrimoElemento deve essere il primo elemento",test1.get(0),MetodiDiSupporto.creaListaOrdinata(test1, test1.get(0)).get(0));
		List<Integer> testOrdinata = MetodiDiSupporto.creaListaOrdinata(test1, test1.get(1000));
		for (int i = 1000; i < test1.size(); i++ ){
			assertEquals("L'elemento della vecchia lista in posizione "+i+" deve essere in posizione "+(i-1000)+" nella nuova lista",test1.get(i),testOrdinata.get(i-1000));
		}
		for (int i = 0; i<1000; i++){
			assertEquals("L'elemento della vecchia lista in posizione "+i+" deve essere in posizione "+(i+1000)+" nella nuova lista",testOrdinata.get(i+24),test1.get(i));
		}
	}

}
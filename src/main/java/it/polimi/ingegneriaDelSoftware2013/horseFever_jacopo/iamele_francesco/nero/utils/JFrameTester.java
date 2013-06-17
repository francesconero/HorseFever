package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class JFrameTester {

	public static void test(final Component component) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				JFrame test = new JFrame(component.getClass().getName()+" test");
				test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				test.add(component);
				test.pack();
				test.setLocationRelativeTo(null);
				test.setVisible(true);
			}
		});
	}

}

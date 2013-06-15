package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ConnettendoFrame extends JFrame {
	public ConnettendoFrame(final String ip) {
		getContentPane().setPreferredSize(new Dimension(300, 50));		
		SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				JLabel lblConnessioneAlServer = new JLabel();
				lblConnessioneAlServer.setHorizontalTextPosition(SwingConstants.CENTER);
				lblConnessioneAlServer.setHorizontalAlignment(SwingConstants.CENTER);
				getContentPane().add(lblConnessioneAlServer, BorderLayout.CENTER);
				lblConnessioneAlServer.setText("Connessione al server: "+ip);
				pack();
				setLocationRelativeTo(null);
				setVisible(true);
			}
		});
	}

	public void chiudi() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dispose();
			}			
		});		
	}

}

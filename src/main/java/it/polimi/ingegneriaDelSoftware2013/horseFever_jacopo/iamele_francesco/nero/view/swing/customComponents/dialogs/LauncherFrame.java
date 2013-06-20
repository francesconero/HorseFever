package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console.GiocoOffline;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica.ControlloreGrafica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.ImagePanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LauncherFrame implements ActionListener{

	private JFrame frame;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	protected ServerMain server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherFrame window = new LauncherFrame();			
					window.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 583);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

		ImagePanel imagePanel = new ImagePanel("resources/immagini/box_front_HF_640.jpg");
		panel.add(imagePanel);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 200));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		btnNewButton = new JButton("GIOCA OFFLINE IN MODALITA' TESTUALE");
		btnNewButton.addActionListener(this);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton);

		btnNewButton_1 = new JButton("CREA PARTITA ONLINE");
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton_1);

		btnNewButton_2 = new JButton("PARTECIPA A PARTITA ONLINE");
		btnNewButton_2.addActionListener(this);
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton_2);

		frame.pack();
	}

	protected void disposeFrame() {
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {			
			public void run() {
				frame.dispose();
			}
		});
	}

	public void show() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnNewButton)){
			MetodiDiSupporto.nuovoThread(new Runnable() {					
				public void run() {
					disposeFrame();
					GiocoOffline gO = new GiocoOffline();
					gO.inizia();
				}
			});
		}

		if(e.getSource().equals(btnNewButton_1)){
			int numeroGiocatori = 0;
			boolean DONE = false;
			do{
				try{
					numeroGiocatori = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il numero di giocatori!"));
					if(numeroGiocatori>=2&&numeroGiocatori<=6)
						DONE = true;
					else 
						JOptionPane.showMessageDialog(null, "Inserisci un intero da 2 a 6!");							
				} catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "Inserisci un intero da 2 a 6!");
				}
			} while(!DONE );

			MetodiDiSupporto.nuovoThread(new Runnable() {

				private int numeroGiocatori;

				public void run() {
					server = new ServerMain();
					server.start(new String[]{Integer.toString(numeroGiocatori)});
				}

				public Runnable initialize(int numeroGiocatori) {
					this.numeroGiocatori = numeroGiocatori;
					return this;
				}
			}.initialize(numeroGiocatori));

			MetodiDiSupporto.nuovoThread(new Runnable() {

				public void run() {
					disposeFrame();
					try {
						ControlloreGrafica cG = new ControlloreGrafica();
						cG.show(InetAddress.getLocalHost());
					} catch (UnknownHostException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}

		if(e.getSource().equals(btnNewButton_2)){
			MetodiDiSupporto.nuovoThread(new Runnable() {
				public void run() {
					disposeFrame();
					ControlloreGrafica cG = new ControlloreGrafica();
					cG.show();
				}
			});
		}
	}
}

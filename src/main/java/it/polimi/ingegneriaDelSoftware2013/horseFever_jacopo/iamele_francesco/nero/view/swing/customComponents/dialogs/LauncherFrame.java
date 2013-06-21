package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console.GiocoOffline;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica.ControlloreGrafica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.rete.ServerMain;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.ImagePanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LauncherFrame implements ActionListener, WindowListener {

	private JFrame frame;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private PrintStream stdout = System.out;
	private PrintStream stderr = System.err;
	private InputStream stdin = System.in;
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

		ImagePanel imagePanel = new ImagePanel(Risorse.getInstance().getImmagine("Cover"));
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
					GiocoOffline gO = new GiocoOffline(LauncherFrame.this);
					gO.inizia();
				}
			});
		}

		if(e.getSource().equals(btnNewButton_1)){
			int numeroGiocatori = 0;
			boolean done = false;
			boolean launch = false;
			do{
				String risposta = JOptionPane.showInputDialog("Inserisci il numero di giocatori!");
				if(risposta!=null){
					try{
						numeroGiocatori = Integer.parseInt(risposta);
						if(numeroGiocatori>=2&&numeroGiocatori<=6){
							done = true;
							launch = true;
						} else { 
							JOptionPane.showMessageDialog(null, "Inserisci un intero da 2 a 6!");
						}
					} catch (NumberFormatException ex){
						JOptionPane.showMessageDialog(null, "Inserisci un intero da 2 a 6!");
					}
				} else {
					done = true;
				}
			} while(!done );
			if(launch){
				MetodiDiSupporto.nuovoThread(new Runnable() {

					private int numeroGiocatori;

					public void run() {
						disposeFrame();
						server = new ServerMain();
						server.addListener(LauncherFrame.this);
						server.start(new String[]{Integer.toString(numeroGiocatori)});
					}

					public Runnable initialize(int numeroGiocatori) {
						this.numeroGiocatori = numeroGiocatori;
						return this;
					}
				}.initialize(numeroGiocatori));
			}

		}

		if(e.getSource().equals(btnNewButton_2)){
			MetodiDiSupporto.nuovoThread(new Runnable() {
				public void run() {
					disposeFrame();
					ControlloreGrafica cG = new ControlloreGrafica();
					cG.addListener(LauncherFrame.this);
					cG.show();
				}
			});
		}
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {
		System.setOut(stdout);
		System.setIn(stdin);
		System.setErr(stderr);
		LauncherFrame.main(new String[0]);
	}

	public void windowClosing(WindowEvent arg0) {
		System.setOut(stdout);
		System.setIn(stdin);
		System.setErr(stderr);
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void avvertiChiusura() {
		System.setOut(stdout);
		System.setIn(stdin);
		System.setErr(stderr);
		LauncherFrame.main(new String[0]);
	}
}

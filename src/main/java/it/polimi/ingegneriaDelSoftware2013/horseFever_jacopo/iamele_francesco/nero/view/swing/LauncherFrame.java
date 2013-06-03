package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.ImagePanel;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.awt.Dimension;

public class LauncherFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherFrame window = new LauncherFrame();
					window.frame.setVisible(true);
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		ImagePanel imagePanel = new ImagePanel("src/main/resources/immagini/box_front_HF_640.jpg");
		panel.add(imagePanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 200));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnNewButton = new JButton("GIOCA OFFLINE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CREA PARTITA ONLINE");
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("PARTECIPA A PARTITA ONLINE");
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(btnNewButton_2);
		
		frame.pack();
	}
}

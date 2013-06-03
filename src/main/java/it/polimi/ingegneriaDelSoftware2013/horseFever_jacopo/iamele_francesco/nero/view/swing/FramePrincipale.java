package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;


import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.SpinnerModel;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.GiocatoriPanel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.ImagePanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.AttivitaPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.TabellonePanel;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.ComponentOrientation;
import javax.swing.SpinnerListModel;

public class FramePrincipale implements FamilyView{

	private JFrame frame;
	private List<FamilyViewObserver> osservatori = new LinkedList<FamilyViewObserver>();
	private GiocatoriPanel giocatoriPanel;
	private TabellonePanel tabellonePanel;
	private AttivitaPanel attivitaPanel;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblNewLabel;
	private Component verticalStrut;
	private JPanel panel_2;
	private JSpinner spinner;
	private SpinnerListModel informazioniDiGioco = new SpinnerListModel();;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FramePrincipale window = new FramePrincipale();
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
	public FramePrincipale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param statoDelGioco 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 714, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{73, 0};
		gridBagLayout.rowHeights = new int[]{130, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2, true), new EmptyBorder(10, 10, 10, 10))));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.add(panel_2);
		
		lblNewLabel = new JLabel("INFORMAZIONI DI GIOCO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		spinner = new JSpinner();
		spinner.setModel(informazioniDiGioco);
		DefaultEditor ed = (DefaultEditor) spinner.getEditor();
		ed.getTextField().setHorizontalAlignment(JTextField.CENTER);
		ed.getTextField().setBorder(new EmptyBorder(0, 10, 2, 10));
		ed.getTextField().setEditable(false);
		panel_3.add(spinner);
		
		verticalStrut = Box.createVerticalStrut(20);
		panel.add(verticalStrut);
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{73, 104, 70, 0};
		gbl_panel_1.rowHeights = new int[]{130, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		giocatoriPanel = new GiocatoriPanel();
		GridBagConstraints gbc_giocatoriPanel = new GridBagConstraints();
		gbc_giocatoriPanel.fill = GridBagConstraints.BOTH;
		gbc_giocatoriPanel.insets = new Insets(0, 0, 0, 5);
		gbc_giocatoriPanel.gridx = 0;
		gbc_giocatoriPanel.gridy = 0;
		panel_1.add(giocatoriPanel, gbc_giocatoriPanel);
		
		tabellonePanel = new TabellonePanel();
		GridBagConstraints gbc_tabellonePanel = new GridBagConstraints();
		gbc_tabellonePanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_tabellonePanel.insets = new Insets(0, 0, 0, 5);
		gbc_tabellonePanel.gridx = 1;
		gbc_tabellonePanel.gridy = 0;
		panel_1.add(tabellonePanel, gbc_tabellonePanel);
		
		attivitaPanel = new AttivitaPanel();
		GridBagConstraints gbc_attivitaPanel = new GridBagConstraints();
		gbc_attivitaPanel.fill = GridBagConstraints.BOTH;
		gbc_attivitaPanel.gridx = 2;
		gbc_attivitaPanel.gridy = 0;
		panel_1.add(attivitaPanel, gbc_attivitaPanel);
		frame.pack();
	}

	public void aggiorna(StatoDelGiocoView view) {
		((List<String>) informazioniDiGioco.getList()).add("ciao");
	}

	public void aggiungiObserver(FamilyViewObserver observer) {
		osservatori .add(observer);
	}
	
	protected GiocatoriPanel getGiocatoriPanel() {
		return giocatoriPanel;
	}
	protected TabellonePanel getTabellonePanel() {
		return tabellonePanel;
	}
	protected AttivitaPanel getAttivitaPanel() {
		return attivitaPanel;
	}
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;


import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.FamilyView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.AttivitaPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.GiocatoriPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.TabellonePanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
					GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
					
					window.frame.setMaximizedBounds(e.getMaximumWindowBounds());

					window.frame.setExtendedState(window.frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );

					window.frame.setSize(e.getMaximumWindowBounds().width, e.getMaximumWindowBounds().height);
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
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2, true), new EmptyBorder(10, 10, 10, 10))));
		frame.getContentPane().add(panel);
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
		gbl_panel_1.columnWidths = new int[]{299, 116, 303, 0};
		gbl_panel_1.rowHeights = new int[]{736, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
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
		gbc_tabellonePanel.fill = GridBagConstraints.VERTICAL;
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

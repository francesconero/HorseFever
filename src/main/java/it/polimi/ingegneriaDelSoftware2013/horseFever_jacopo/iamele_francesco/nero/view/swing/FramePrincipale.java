package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;


import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.FamilyView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.AttivitaPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.GiocatoriPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.TabellonePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class FramePrincipale implements FamilyView{

	private JFrame frame;
	private List<FramePrincipaleObserver> osservatori = new LinkedList<FramePrincipaleObserver>();
	private GiocatoriPanel giocatoriPanel;
	private TabellonePanel tabellonePanel;
	private AttivitaPanel attivitaPanel;
	private JPanel panel;
	private JLabel lblNewLabel;
	private Component verticalStrut;
	private JPanel panel_2;
	private JSpinner spinner;
	private InformazioniDiGiocoModel informazioniDiGioco = new InformazioniDiGiocoModel();
	private TipoFaseGiocoFamily faseGioco;
	private StatoDelGiocoView ultimoAggiornamento;
	private JPanel panel_4;
	private JPanel panel_1;
	public FramePrincipale() {
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {			
			public void run() {
				initialize();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * @param statoDelGioco 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		
		informazioniDiGioco.addInformazione("Attesa altri giocatori...");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 714, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		panel_2 = new JPanel();
		panel_4.add(panel_2);
		panel_2.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		
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
		panel_4.add(panel_3);
		panel_3.setBorder(null);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		spinner = new JSpinner();
		spinner.setBorder(new LineBorder(new Color(0, 0, 0)));
		spinner.setModel(informazioniDiGioco);
		DefaultEditor ed = (DefaultEditor) spinner.getEditor();
		panel_3.add(spinner);
		verticalStrut = Box.createVerticalStrut(10);
		panel_4.add(verticalStrut);
		
		panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{350, 104, 303, 0};
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
		gbc_tabellonePanel.fill = GridBagConstraints.BOTH;
		gbc_tabellonePanel.insets = new Insets(0, 0, 0, 5);
		gbc_tabellonePanel.gridx = 1;
		gbc_tabellonePanel.gridy = 0;
		panel_1.add(tabellonePanel, gbc_tabellonePanel);
		tabellonePanel.setOpaque(false);
		
		attivitaPanel = new AttivitaPanel();
		GridBagConstraints gbc_attivitaPanel = new GridBagConstraints();
		gbc_attivitaPanel.fill = GridBagConstraints.BOTH;
		gbc_attivitaPanel.gridx = 2;
		gbc_attivitaPanel.gridy = 0;
		panel_1.add(attivitaPanel, gbc_attivitaPanel);
		ed.getTextField().setHorizontalAlignment(JTextField.CENTER);
		ed.getTextField().setBorder(new EmptyBorder(0, 10, 2, 10));
		ed.getTextField().setEditable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public void aggiorna(StatoDelGiocoView view) {
		ultimoAggiornamento = view;
		faseGioco = view.getTipoFaseGiocoFamily();
		
		switch(faseGioco){
			case PREPARAZIONE:
				informazioniDiGioco.addInformazione("Siamo nella fase preparazione!");
				break;
			case DISTRIBUZIONE_CARTE:
				informazioniDiGioco.addInformazione("Abbiamo appena distribuito le carte!!");
				break;
			case F_S_ORARIA:
				informazioniDiGioco.addInformazione("Siamo nella prima fase scommesse!");
				break;
			case F_S_ALTERAZIONE_GARA:
				informazioniDiGioco.addInformazione("E' ora di truccare la corsa!");
				break;
			case F_S_ANTIORARIA:
				break;
			case F_C_SCOPRICARTAAZIONE:
				break;
			case F_C_CORSA:
				break;
			case FINETURNO:
				break;
			case F_C_PAGAMENTI_NUOVE_QUOTAZIONI:
				break;
			case VITTORIA:
				break;
			default:
				throw new IllegalStateException("Fase non gestibile: " + view.getTipoFaseGiocoFamily());			
		}
		
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {			
			public void run() {
				aggiornaViewGenerica();		
				frame.pack();
			}
		});		
		
	}

	private void aggiornaViewGenerica() {		
		giocatoriPanel.aggiorna(ultimoAggiornamento.getGiocatori());
		tabellonePanel.aggiornaQuotazioni(ricavaQuotazioni(ultimoAggiornamento.getCorsie()));
	}

	private Map<Colore, Integer> ricavaQuotazioni(List<Scuderia> corsie) {
		Map<Colore, Integer> out = new HashMap<Colore, Integer>();
		for(Scuderia s : corsie){
			out.put(s.getColore(), s.getQuotazione());
		}
		return out;
	}

	public void aggiungiObserver(FramePrincipaleObserver observer) {
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

	public void chiudi() {
		SwingUtilities.invokeLater(new Runnable() {			
			public void run() {
				frame.dispose();			
			}
		});
	}
	
	public static void main(String[] args){
		new FramePrincipale();
	}
}

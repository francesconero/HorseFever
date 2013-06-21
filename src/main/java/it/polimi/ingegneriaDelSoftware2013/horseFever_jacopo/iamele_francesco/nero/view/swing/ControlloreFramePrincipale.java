package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;


import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.grafica.ControlloreGrafica;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGiocoView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers.AttivitaPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers.GiocatoriPanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers.TabellonePanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.InformazioniDiGiocoModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class ControlloreFramePrincipale extends WindowAdapter implements PropertyChangeListener {

	private JFrame frmHorseFever;
	private ControlloreGrafica osservatore;
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
	private boolean inGioco = true;

	public ControlloreFramePrincipale() {
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {			
			public void run() {
				initialize();
			}
		});
	}

	public void show(){
		frmHorseFever.setVisible(true);
		frmHorseFever.setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param statoDelGioco 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {

		informazioniDiGioco.addInformazione("Attesa altri giocatori...");

		frmHorseFever = new JFrame();
		frmHorseFever.setTitle("Horse Fever");
		frmHorseFever.setBounds(100, 100, 714, 471);
		frmHorseFever.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHorseFever.getContentPane().setLayout(new BorderLayout());
		frmHorseFever.addWindowStateListener(this);
		frmHorseFever.addWindowListener(this);

		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmHorseFever.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		panel_2 = new JPanel();
		panel_2.setBackground(Color.ORANGE);
		panel_4.add(panel_2);
		panel_2.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));

		lblNewLabel = new JLabel("INFORMAZIONI DI GIOCO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner.setBorder(new LineBorder(new Color(0, 0, 0)));
		spinner.setModel(informazioniDiGioco);
		DefaultEditor ed = (DefaultEditor) spinner.getEditor();
		panel_3.add(spinner);
		verticalStrut = Box.createVerticalStrut(10);
		panel_4.add(verticalStrut);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 104, 0, 0};
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
		giocatoriPanel.addPropertyChangeListener(this);
		panel_1.add(giocatoriPanel, gbc_giocatoriPanel);

		tabellonePanel = new TabellonePanel();
		GridBagConstraints gbc_tabellonePanel = new GridBagConstraints();
		gbc_tabellonePanel.fill = GridBagConstraints.BOTH;
		gbc_tabellonePanel.insets = new Insets(0, 0, 0, 5);
		gbc_tabellonePanel.gridx = 1;
		gbc_tabellonePanel.gridy = 0;
		panel_1.add(tabellonePanel, gbc_tabellonePanel);
		tabellonePanel.addPropertyChangeListener(this);
		tabellonePanel.setOpaque(false);

		attivitaPanel = new AttivitaPanel();
		GridBagConstraints gbc_attivitaPanel = new GridBagConstraints();
		gbc_attivitaPanel.fill = GridBagConstraints.BOTH;
		gbc_attivitaPanel.gridx = 2;
		gbc_attivitaPanel.gridy = 0;
		attivitaPanel.addPropertyChangeListener(this);
		panel_1.add(attivitaPanel, gbc_attivitaPanel);
		ed.getTextField().setHorizontalAlignment(JTextField.CENTER);
		ed.getTextField().setBorder(new EmptyBorder(0, 10, 2, 10));
		ed.getTextField().setEditable(false);
		frmHorseFever.pack();
	}

	/**
	 * Aggiorna la view con un nuovo stato del gioco. Questo metodo blocca fintanto che la grafica ha fatto l'update. NON chiamare dall'EDT.
	 * @param view il nuovo stato del gioco (view) con cui aggiornare la grafica.
	 */
	public void aggiorna(final StatoDelGiocoView view) {	
		LinkedList<GiocatoreView> giocatoriEliminati = new LinkedList<GiocatoreView>();
		LinkedList<GiocatoreView> giocatoriNuovi = new LinkedList<GiocatoreView>();
		if(ultimoAggiornamento!=null){
			giocatoriNuovi.addAll(view.getGiocatori());
			giocatoriEliminati.addAll(ultimoAggiornamento.getGiocatori());
			giocatoriEliminati.removeAll(giocatoriNuovi);
		}
		ultimoAggiornamento = view;
		faseGioco = view.getTipoFaseGiocoFamily();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		switch(faseGioco){
			case PREPARAZIONE:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("Benvenuto a HorseFever!");
						prossimoAggiornamento();
					}
				});				
				break;
			case ELIMINAZIONE_GIOCATORI:
				if(!ultimoAggiornamento.getGiocatori().contains(ultimoAggiornamento.getMioGiocatore())){
					inGioco = false;
					informazioniDiGioco.addInformazione("Mi dispiace, sei stato eliminato!");
					JOptionPane.showMessageDialog(frmHorseFever, "Mi dispiace, sei stato eliminato!");
					finePartita();
				} else {
					for(GiocatoreView g: giocatoriEliminati){
						informazioniDiGioco.addInformazione("Eliminato "+g.getNomeUtente()+"!");
						JOptionPane.showMessageDialog(frmHorseFever, "E' stato eliminato "+g.getNomeUtente()+"!");
					}
					prossimoAggiornamento();
				}
				break;
			case DISTRIBUZIONE_CARTE:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("Abbiamo appena distribuito le carte!");						
						aggiornaViewGenerica();		
						prossimoAggiornamento();
					}
				});
				break;
			case F_S_ORARIA:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("Siamo nella prima fase scommesse del turno: "+ultimoAggiornamento.getNumTurno());
						if(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ORARIA)){
							informazioniDiGioco.addInformazione("E' il tuo turno di scommettere! Seleziona una scuderia e scommetti dal pannello attivita'.");
							aggiornaViewGenerica();		
						} else {							
							informazioniDiGioco.addInformazione("Attendi che "+view.getGiocatoreDiTurno().getNomeUtente()+" scommetta!");
							aggiornaViewGenerica();		
							prossimoAggiornamento();
						}
					}
				});
				break;
			case F_S_ALTERAZIONE_GARA:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("E' ora di truccare la corsa!");
						if(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA)){
							informazioniDiGioco.addInformazione("E' il tuo turno di truccare la corsa! Seleziona una scuderia e trucca la corsa dal pannello attivita'.");
							aggiornaViewGenerica();		
						} else {							
							informazioniDiGioco.addInformazione("Attendi che "+view.getGiocatoreDiTurno().getNomeUtente()+" trucchi la corsa!");
							aggiornaViewGenerica();		
							prossimoAggiornamento();
						}
					}
				});
				break;
			case F_S_ANTIORARIA:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("Siamo nella seconda fase scommesse!");
						if(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ANTIORARIA)){
							informazioniDiGioco.addInformazione("E' il tuo turno di scommettere! Seleziona una scuderia e scommetti oppure passa il turno.");
							aggiornaViewGenerica();		
						} else {							
							informazioniDiGioco.addInformazione("Attendi che "+view.getGiocatoreDiTurno().getNomeUtente()+" scommetta!");
							aggiornaViewGenerica();		
							prossimoAggiornamento();
						}
					}
				});
				break;
			case F_C_SCOPRICARTAAZIONE:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						informazioniDiGioco.addInformazione("Scopriamo cosa e' stato giocato sulle scuderie!");
						resetTabellone();
						aggiornaViewGenerica();
						prossimoAggiornamento();
					}
				});
				break;
			case F_C_CORSA:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						ControlloreCorsaGrafica cCG = new ControlloreCorsaGrafica(ultimoAggiornamento, osservatore, tabellonePanel, informazioniDiGioco);
						cCG.controlla();
					}
				});
				break;
			case FINETURNO:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						prossimoAggiornamento();
					}
				});
				break;
			case F_C_PAGAMENTI_NUOVE_QUOTAZIONI:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						prossimoAggiornamento();
					}
				});
				break;
			case VITTORIA:
				MetodiDiSupporto.swingInvokeAndWait(new Runnable() {					
					public void run() {
						if(ultimoAggiornamento.getGiocatoreDiTurno()==null){
							JOptionPane.showMessageDialog(frmHorseFever, "You're all losers!");
						} else {
							if(ultimoAggiornamento.getMioGiocatore().equals(ultimoAggiornamento.getGiocatoreDiTurno())){
								JOptionPane.showMessageDialog(frmHorseFever, "Complimenti, hai vinto!!");
							} else {
								JOptionPane.showMessageDialog(frmHorseFever, "E' risultato vincitore: " + ultimoAggiornamento.getGiocatoreDiTurno().getNomeUtente());
							}							
						}
						finePartita();
					}
				});
				break;
			default:
				throw new IllegalStateException("Fase non gestibile: " + view.getTipoFaseGiocoFamily());			
		}
	}

	protected void finePartita() {
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.finePartita();
			}
		});
	}

	protected void resetTabellone() {
		tabellonePanel.resetCorsa();
	}

	private void prossimoAggiornamento() {
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.prossimoAggiornamento();
			}
		});
	}

	private void aggiornaViewGenerica() {		
		tabellonePanel.aggiornaQuotazioni(ricavaQuotazioni(ultimoAggiornamento.getCorsie()));
		tabellonePanel.aggiornaTurno(ultimoAggiornamento.getNumTurno());
		if(inGioco){
			giocatoriPanel.aggiorna(ultimoAggiornamento.getGiocatori(), ultimoAggiornamento.getMioGiocatore());
		} else {
			giocatoriPanel.aggiorna(ultimoAggiornamento.getGiocatori(), ultimoAggiornamento.getGiocatoreDiTurno());
		}
		attivitaPanel.aggiorna(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ORARIA),
				mioTurno(ultimoAggiornamento,TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA),
				mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ANTIORARIA),
				ultimoAggiornamento.getGiocatori());
		attivitaPanel.showScuderiePanel(ultimoAggiornamento.getScuderiaDalColore(tabellonePanel.getScuderiaSelezionata()));
	}

	protected boolean mioTurno(StatoDelGiocoView ultimoAggiornamento,
			TipoFaseGiocoFamily faseGioco) {
		GiocatoreView mioGiocatore = ultimoAggiornamento.getMioGiocatore();
		GiocatoreView giocatoreDiTurno = ultimoAggiornamento.getGiocatoreDiTurno();
		return ultimoAggiornamento.getTipoFaseGiocoFamily().equals(faseGioco) && mioGiocatore.equals(giocatoreDiTurno); 
	}

	private Map<Colore, Integer> ricavaQuotazioni(List<Scuderia> corsie) {
		Map<Colore, Integer> out = new HashMap<Colore, Integer>();
		for(Scuderia s : corsie){
			out.put(s.getColore(), s.getQuotazione());
		}
		return out;
	}

	public void settaObserver(ControlloreGrafica osservatore) {
		this.osservatore = osservatore;
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
				frmHorseFever.dispose();			
			}
		});		
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().equals(giocatoriPanel)){
			if(evt.getPropertyName().equals("SELEZIONATO")){
				attivitaPanel.aggiorna(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ORARIA),
						mioTurno(ultimoAggiornamento,TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA),
						mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ANTIORARIA),
						ultimoAggiornamento.getGiocatori());
				attivitaPanel.showManoPanel((GiocatoreView)evt.getNewValue());
			}
		}
		if(evt.getSource().equals(tabellonePanel)){
			if(evt.getPropertyName().equals("SELEZIONATO")){
				attivitaPanel.aggiorna(mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ORARIA),
						mioTurno(ultimoAggiornamento,TipoFaseGiocoFamily.F_S_ALTERAZIONE_GARA),
						mioTurno(ultimoAggiornamento, TipoFaseGiocoFamily.F_S_ANTIORARIA),
						ultimoAggiornamento.getGiocatori());
				attivitaPanel.showScuderiePanel(ultimoAggiornamento.getScuderiaDalColore((Colore)evt.getNewValue()));
			}
		}
		if(evt.getSource().equals(attivitaPanel)){
			if(evt.getPropertyName().equals("SCOMMESSA")){
				scomessaFatta((Scommessa)evt.getNewValue());
			}
			if(evt.getPropertyName().equals("INIZIO_TRUCCAMENTO")){
				attivitaPanel.showSelezionaCarta(ultimoAggiornamento.getMioGiocatore());
			}
			if(evt.getPropertyName().equals("FINE_TRUCCAMENTO")){
				cartaGiocata((CartaAzione)evt.getNewValue(), tabellonePanel.getScuderiaSelezionata());
			}
		}
	}

	private void cartaGiocata(final CartaAzione newValue, final Colore scuderia) {
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.giocaCartaAzione(newValue, ultimoAggiornamento.getScuderiaDalColore(scuderia));
				osservatore.prossimoAggiornamento();
			}
		});
	}

	private void scomessaFatta(final Scommessa scommessa) {
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.scommessa(scommessa);
			}
		});
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		if(e.getNewState()==JFrame.NORMAL){
			frmHorseFever.pack();
			frmHorseFever.setLocationRelativeTo(null);
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		MetodiDiSupporto.nuovoThread(new Runnable() {			
			public void run() {
				osservatore.finePartitaForzata(null);
			}
		});
	}

	public void risultatoScommessa(final boolean accettata) {
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {
			public void run() {
				if(accettata){
					JOptionPane.showMessageDialog(frmHorseFever, "La tua scommessa e' stata accettata!");
					MetodiDiSupporto.nuovoThread(new Runnable() {			
						public void run() {
							osservatore.prossimoAggiornamento();
						}
					});
				} else {						
					MetodiDiSupporto.nuovoThread(new Runnable() {			
						public void run() {
							osservatore.riceviAvvertimento();
						}
					});
				}
			}
		});
	}

	public void avverti(final String avvertimento) {
		MetodiDiSupporto.swingInvokeAndWait(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(frmHorseFever, avvertimento);
				MetodiDiSupporto.nuovoThread(new Runnable() {			
					public void run() {
						osservatore.stessoAggiornamento();
					}
				});
			}
		});

	}
}

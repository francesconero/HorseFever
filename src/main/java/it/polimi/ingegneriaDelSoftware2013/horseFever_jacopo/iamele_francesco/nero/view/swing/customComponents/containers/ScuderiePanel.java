package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.JFrameTester;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.CartaAzionePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ScuderiePanel extends JPanel {
	private JFormattedTextField textField;
	private JButton btnScommetti;
	private JComboBox<TipoScommessa> comboBox;
	private Scuderia scuderia;
	private JLabel labelScuderia;
	private JButton btnTruccaQuestaScuderia;
	private JButton btnPassa;
	private JScrollPane scrollPane;
	private JPanel cartePanel;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	
	/**
	 * Create the panel.
	 */
	public ScuderiePanel() {
		setBackground(Color.ORANGE);
		setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		labelScuderia = new JLabel("Scuderia");
		panel_1.add(labelScuderia);

		Dimension newMaximumSize = panel_1.getPreferredSize();
		newMaximumSize.width = panel_1.getMaximumSize().width;
		panel_1.setMaximumSize(newMaximumSize);

		JPanel scuderiaPanel = new JPanel();
		add(scuderiaPanel, BorderLayout.CENTER);
		scuderiaPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scuderiaPanel.add(scrollPane);

		cartePanel = new JPanel();
		cartePanel.setBackground(Color.ORANGE);
		scrollPane.setViewportView(cartePanel);
		cartePanel.setLayout(new BoxLayout(cartePanel, BoxLayout.Y_AXIS));

		JPanel panel_3 = new JPanel();
		scuderiaPanel.add(panel_3, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel_3.add(panel_2, gbc_panel_2);
		panel_2.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_2.setMinimumSize(new Dimension(10, 150));
		panel_2.setPreferredSize(new Dimension(10, 150));
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{14, 0, 0, 0, 23, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);

		JLabel lblInserisciQuantitDi = new JLabel("Inserisci quantit\u00E0 di danari da scommettere:");
		lblInserisciQuantitDi.setToolTipText("Inserisci quantit\u00E0 di danari da scommettere");
		GridBagConstraints gbc_lblInserisciQuantitDi = new GridBagConstraints();
		gbc_lblInserisciQuantitDi.gridwidth = 2;
		gbc_lblInserisciQuantitDi.fill = GridBagConstraints.BOTH;
		gbc_lblInserisciQuantitDi.insets = new Insets(0, 0, 5, 0);
		gbc_lblInserisciQuantitDi.gridx = 0;
		gbc_lblInserisciQuantitDi.gridy = 0;
		panel_2.add(lblInserisciQuantitDi, gbc_lblInserisciQuantitDi);

		textField = new JFormattedTextField(NumberFormat.getIntegerInstance(Locale.getDefault()));
		textField.setAction(action_1);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEnabled(false);
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_2.add(textField, gbc_textField);

		JLabel lblSelezionaIlTipo = new JLabel("Seleziona il tipo di scommessa:");
		GridBagConstraints gbc_lblSelezionaIlTipo = new GridBagConstraints();
		gbc_lblSelezionaIlTipo.gridwidth = 2;
		gbc_lblSelezionaIlTipo.fill = GridBagConstraints.BOTH;
		gbc_lblSelezionaIlTipo.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelezionaIlTipo.gridx = 0;
		gbc_lblSelezionaIlTipo.gridy = 2;
		panel_2.add(lblSelezionaIlTipo, gbc_lblSelezionaIlTipo);

		comboBox = new JComboBox<TipoScommessa>();
		comboBox.setEnabled(false);
		comboBox.setModel(new DefaultComboBoxModel<TipoScommessa>(TipoScommessa.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		panel_2.add(comboBox, gbc_comboBox);

		btnScommetti = new JButton("SCOMMETTI");
		btnScommetti.setAction(action_1);
		btnScommetti.setPreferredSize(new Dimension(0, 23));
		btnScommetti.setMinimumSize(new Dimension(20, 23));
		btnScommetti.setEnabled(false);
		GridBagConstraints gbc_btnScommetti = new GridBagConstraints();
		gbc_btnScommetti.insets = new Insets(0, 0, 0, 5);
		gbc_btnScommetti.fill = GridBagConstraints.BOTH;
		gbc_btnScommetti.gridx = 0;
		gbc_btnScommetti.gridy = 4;
		panel_2.add(btnScommetti, gbc_btnScommetti);

		btnPassa = new JButton("PASSA");
		btnPassa.setPreferredSize(new Dimension(0, 23));
		btnPassa.setMinimumSize(new Dimension(20, 23));
		btnPassa.setEnabled(false);
		GridBagConstraints gbc_btnPassa = new GridBagConstraints();
		gbc_btnPassa.fill = GridBagConstraints.BOTH;
		gbc_btnPassa.gridx = 1;
		gbc_btnPassa.gridy = 4;
		panel_2.add(btnPassa, gbc_btnPassa);
		btnPassa.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				firePropertyChange("SCOMMESSA", null, new Scommessa(null, 0, null));
			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new CompoundBorder(new MatteBorder(2, 0, 0, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(10, 10, 10, 10)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panel_3.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		btnTruccaQuestaScuderia = new JButton("TRUCCA");
		btnTruccaQuestaScuderia.setAction(action);
		btnTruccaQuestaScuderia.setEnabled(false);
		GridBagConstraints gbc_btnTruccaQuestaScuderia = new GridBagConstraints();
		gbc_btnTruccaQuestaScuderia.fill = GridBagConstraints.BOTH;
		gbc_btnTruccaQuestaScuderia.gridx = 0;
		gbc_btnTruccaQuestaScuderia.gridy = 0;
		panel.add(btnTruccaQuestaScuderia, gbc_btnTruccaQuestaScuderia);
	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		JFrameTester.test(new ScuderiePanel());
	}

	public void aggiorna(Scuderia newValue) {
		this.scuderia = newValue;
		labelScuderia.setText("Carte azione giocate sulla scuderia: "+newValue.getColore().name());
		cartePanel.removeAll();
		for(CartaAzione cA: newValue.getCarteAzione()){
			JPanel holder = new JPanel();
			holder.setBackground(Color.RED);
			holder.setOpaque(false);
			CartaAzionePanel cAP = new CartaAzionePanel(cA);
			Dimension newSize = cAP.getPreferredSize();
			newSize.width += 10;
			newSize.height += 10;
			holder.setPreferredSize(newSize);
			holder.setMaximumSize(newSize);
			holder.add(cAP);
			cartePanel.add(holder);
		}
		revalidate();
		repaint();
	}

	public void enableScommessa(boolean enabled) {
		textField.setEnabled(enabled);
		btnScommetti.setEnabled(enabled);
		comboBox.setEnabled(enabled);
	}

	public void enableSalta(boolean enabled) {
		btnPassa.setEnabled(enabled);
	}

	public void enableTruccamento(boolean enabled) {
		btnTruccaQuestaScuderia.setEnabled(enabled);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "TRUCCA");
			putValue(SHORT_DESCRIPTION, "Aggiungi una carta azione alla scuderia selezionata");
		}
		public void actionPerformed(ActionEvent e) {
			ScuderiePanel.this.firePropertyChange("TRUCCAMENTO", null, scuderia.getColore());
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SCOMMETTI");
			putValue(SHORT_DESCRIPTION, "Scommetti sulla scuderia selezionata");
		}
		public void actionPerformed(ActionEvent e) {
			System.out.println("swingAction_1");
			try {
				textField.commitEdit();
				int danari = ((Long)textField.getValue()).intValue();
				TipoScommessa tipoScommessa = comboBox.getItemAt(comboBox.getSelectedIndex());
				Colore c = scuderia.getColore();
				ScuderiePanel.this.firePropertyChange("SCOMMESSA", null, new Scommessa(tipoScommessa, danari, c));
			} catch (ParseException ex) {
				JOptionPane.showMessageDialog(null, "Non hai inserito un numero!", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			} catch (ClassCastException ex) {
				JOptionPane.showMessageDialog(null, "Non hai inserito un numero!", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}

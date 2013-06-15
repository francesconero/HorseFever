package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EffettoPanel extends JPanel {
	public EffettoPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{146, 0, 0};
		gridBagLayout.rowHeights = new int[]{300, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.anchor = GridBagConstraints.WEST;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.fill = GridBagConstraints.VERTICAL;
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 0;
		add(lblTipo, gbc_lblTipo);
		
		JLabel lblTipo_1 = new JLabel("TIPO");
		GridBagConstraints gbc_lblTipo_1 = new GridBagConstraints();
		gbc_lblTipo_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblTipo_1.fill = GridBagConstraints.BOTH;
		gbc_lblTipo_1.gridx = 1;
		gbc_lblTipo_1.gridy = 0;
		add(lblTipo_1, gbc_lblTipo_1);
		
		JLabel label = new JLabel("Tipo:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		JLabel label_1 = new JLabel("TIPO");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
	}

}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.TipoAzione;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class EffettoPanel extends JPanel {
	
	public EffettoPanel(EffettoAzione effettoAzioneAssociato) {
		setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(5, 5, 5, 5))));
		setOpaque(false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.fill = GridBagConstraints.BOTH;
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 0;
		add(lblTipo, gbc_lblTipo);
		
		JLabel lblTipo_1 = new JLabel(effettoAzioneAssociato.getTipo().name());
		GridBagConstraints gbc_lblTipo_1 = new GridBagConstraints();
		gbc_lblTipo_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblTipo_1.fill = GridBagConstraints.BOTH;
		gbc_lblTipo_1.gridx = 1;
		gbc_lblTipo_1.gridy = 0;
		add(lblTipo_1, gbc_lblTipo_1);
		
		JLabel label = new JLabel("Valori:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		String valori = "";
		
		for(Integer valore : effettoAzioneAssociato.getValori()){
			if(!valori.isEmpty()){
				valori+=" ";
			}
			valori += valore;
			valori +=";";
		}
		
		JLabel label_1 = new JLabel(valori);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.BOTH;
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 1;
		add(label_1, gbc_label_1);
		
	}
	
	public EffettoPanel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		JFrame test = new JFrame();
		List<Integer> valori = new LinkedList<Integer>();
		valori.add(100);
		test.getContentPane().add(new EffettoPanel(new EffettoAzione(TipoAzione.SPRINT, valori)));
		test.pack();
		test.setVisible(true);
	}

}

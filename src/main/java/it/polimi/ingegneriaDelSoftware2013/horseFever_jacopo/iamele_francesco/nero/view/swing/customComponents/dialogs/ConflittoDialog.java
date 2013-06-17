package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ConflittoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<Colore> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			List<Colore> coloriConflittualiTest = new LinkedList<Colore>();
			coloriConflittualiTest.add(Colore.NERO);
			coloriConflittualiTest.add(Colore.VERDE);
			coloriConflittualiTest.add(Colore.BLU);			
			ConflittoDialog dialog = new ConflittoDialog(coloriConflittualiTest);
			dialog.pack();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
			System.out.println(dialog.getSoluzioneConflitto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Colore> getSoluzioneConflitto(){		
		return Collections.list(listModel.elements());
	}
	
	public List<Colore> showDialog(){
		pack();
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		return getSoluzioneConflitto();
	}
	
	/**
	 * Create the dialog.
	 */
	public ConflittoDialog(List<Colore> scuderieInConflitto) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel lblTrascinaLeIcone = new JLabel("Trascina le icone delle scuderie per stabilire l'ordine di arrivo");
				panel.add(lblTrascinaLeIcone);
			}
		}
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{0, 0};
			gbl_contentPanel.rowHeights = new int[]{219, 0};
			gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
		}
		listModel = new DefaultListModel<Colore>();
		for(Colore c: scuderieInConflitto){
			listModel.addElement(c);
		}
		JList<Colore> list = new JList<Colore>(listModel);
		list.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new CompoundBorder(new LineBorder(new Color(0, 0, 0), 2), new EmptyBorder(10, 10, 10, 10))));
		list.setBackground(Color.LIGHT_GRAY);
		list.setFixedCellHeight(120);
		list.setCellRenderer(new ImageColoreListCellRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(6);
		list.setDropMode(DropMode.INSERT);

		list.setDragEnabled(true);
		list.setTransferHandler(new ListTransferHandler());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.VERTICAL;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		contentPanel.add(list, gbc_list);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}

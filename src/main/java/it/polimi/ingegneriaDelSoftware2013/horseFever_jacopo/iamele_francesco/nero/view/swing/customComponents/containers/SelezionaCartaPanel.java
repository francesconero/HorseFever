package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.CartaAzionePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class SelezionaCartaPanel extends JPanel implements MouseListener {
	private JPanel cartePanel;
	private HashMap<CartaAzione, CartaAzionePanel> cartePannelli = new HashMap<CartaAzione, CartaAzionePanel>();
	private CartaAzione selezionata;
	private JButton btnGiocaQuestaCarta;

	/**
	 * Create the panel.
	 */
	public SelezionaCartaPanel() {
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		
		JLabel lblNewLabel = new JLabel("Seleziona la carta da giocare e clicca GIOCA QUESTA CARTA");
		panel_1.add(lblNewLabel);
		
		Dimension newMaximumSize = panel_1.getPreferredSize();
		newMaximumSize.width = panel_1.getMaximumSize().width;
		panel_1.setMaximumSize(newMaximumSize);
		add(scrollPane);
		
		cartePanel = new JPanel();
		cartePanel.setBackground(Color.ORANGE);
		cartePanel.setForeground(Color.BLACK);
		scrollPane.setViewportView(cartePanel);
		cartePanel.setLayout(new BoxLayout(cartePanel, BoxLayout.Y_AXIS));

		setPreferredSize(new Dimension(299, 303));
		
		btnGiocaQuestaCarta = new JButton("GIOCA QUESTA CARTA");
		btnGiocaQuestaCarta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selezionata!=null){
					btnGiocaQuestaCarta.setEnabled(false);
					firePropertyChange("TRUCCAMENTO", null, selezionata);
				} else {
					JOptionPane.showMessageDialog(SelezionaCartaPanel.this, "Non hai selezionato una carta!", "Attenzione!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		add(btnGiocaQuestaCarta, BorderLayout.SOUTH);
	}

	public void aggiorna(GiocatoreView newValue) {
		cartePanel.removeAll();
		cartePannelli.clear();
		selezionata = null;
		for(CartaAzione cA: newValue.getCarteAzioni()){
			JPanel holder = new JPanel();
			holder.setBackground(Color.RED);
			holder.setOpaque(false);
			CartaAzionePanel cAP = new CartaAzionePanel(cA);
			cartePannelli.put(cA, cAP);
			Dimension newSize = cAP.getPreferredSize();
			newSize.width += 10;
			newSize.height += 10;
			holder.setPreferredSize(newSize);
			holder.setMaximumSize(newSize);
			holder.add(cAP);
			cAP.addMouseListener(this);
			cartePanel.add(holder);
		}
		btnGiocaQuestaCarta.setEnabled(true);
		revalidate();
		repaint();
	}

	protected JPanel getPanel() {
		return cartePanel;
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		if(arg0.getComponent() instanceof CartaAzionePanel){
			CartaAzionePanel pannelloCliccato = (CartaAzionePanel) arg0.getComponent();
			if(selezionata!=null){
				cartePannelli.get(selezionata).seleziona(false);
			}
			selezionata = pannelloCliccato.getCartaAzioneAssociata();
			pannelloCliccato.seleziona(true);			
		} else {
			if(selezionata!=null){
				cartePannelli.get(selezionata).seleziona(false);				
				selezionata = null;
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

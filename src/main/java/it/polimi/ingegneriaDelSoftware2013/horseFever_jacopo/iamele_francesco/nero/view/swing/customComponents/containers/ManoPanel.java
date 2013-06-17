package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.CartaAzionePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class ManoPanel extends JPanel {
	private JPanel panel;
	private JLabel labelMano;

	/**
	 * Create the panel.
	 */
	public ManoPanel() {
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		
		labelMano = new JLabel("Mano");
		panel_1.add(labelMano);
		
		Dimension newMaximumSize = panel_1.getPreferredSize();
		newMaximumSize.width = panel_1.getMaximumSize().width;
		panel_1.setMaximumSize(newMaximumSize);
		add(scrollPane);
		
		panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setForeground(Color.BLACK);
		scrollPane.setViewportView(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setPreferredSize(new Dimension(299, 303));
	}

	public void aggiorna(GiocatoreView newValue) {
		labelMano.setText("Mano del giocatore: " + newValue.getNomeUtente());
		JPanel cartePanel = getPanel();
		cartePanel.removeAll();
		for(CartaAzione cA: newValue.getCarteAzioni()){
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

	protected JPanel getPanel() {
		return panel;
	}
}

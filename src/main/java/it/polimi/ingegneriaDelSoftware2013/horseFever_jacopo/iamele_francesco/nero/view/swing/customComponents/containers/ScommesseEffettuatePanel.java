package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.ScommessaPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

public class ScommesseEffettuatePanel extends JPanel {
	private JPanel panel;
	private JLabel labelMano;

	/**
	 * Create the panel.
	 */
	public ScommesseEffettuatePanel() {
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		
		labelMano = new JLabel("Scommesse gi\u00E0 effettuate");
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

	}

	public void aggiorna(List<GiocatoreView> giocatori) {
		JPanel scommessePanel = getPanel();
		scommessePanel.removeAll();
		for(GiocatoreView gV: giocatori){
			for(Scommessa s : gV.getScommesseEffettuate()){
				JPanel holder = new JPanel();
				holder.setBackground(Color.RED);
				holder.setOpaque(false);
				ScommessaPanel sP = new ScommessaPanel(s, gV.getNomeUtente());
				Dimension newSize = sP.getPreferredSize();
				newSize.width += 10;
				newSize.height += 10;
				holder.setPreferredSize(newSize);
				holder.setMaximumSize(newSize);
				holder.add(sP);
				scommessePanel.add(holder);
			}
		}
		revalidate();
		repaint();
	}

	protected JPanel getPanel() {
		return panel;
	}
}

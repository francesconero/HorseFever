package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

public class ScuderiePanel extends JPanel {
	private JTextField textField;
	
	private static class ScuderiePanelCreator implements Runnable {
		
		private ScuderiePanel panel;

		public void run() {
			JFrame temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel  = new ScuderiePanel();
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
		}
		
		public ScuderiePanel getScuderiePanel(){
			return panel;
		}

	}
	/**
	 * Create the panel.
	 */
	public ScuderiePanel() {
		setPreferredSize(new Dimension(299, 732));
		setBackground(Color.ORANGE);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 600));
		scrollPane.setMinimumSize(new Dimension(23, 200));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		panel_1.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		
		JLabel lblNewLabel = new JLabel("Scuderia");
		panel_1.add(lblNewLabel);
		
		Dimension newMaximumSize = panel_1.getPreferredSize();
		newMaximumSize.width = panel_1.getMaximumSize().width;
		panel_1.setMaximumSize(newMaximumSize);
		add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(2, 0, 2, 0, (Color) new Color(0, 0, 0)));
		add(panel);
		
		
		JLabel lblNewLabel_1 = new JLabel("Scommetti");
		panel.add(lblNewLabel_1);
		
		newMaximumSize = panel.getPreferredSize();
		newMaximumSize.width = panel.getMaximumSize().width;
		panel.setMaximumSize(newMaximumSize);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 150));
		add(panel_2);
		
		JLabel lblInserisciQuantitDi = new JLabel("Inserisci quantit\u00E0 di danari da scommettere:");
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		
		JButton btnScommetti = new JButton("SCOMMETTI!");
		btnScommetti.setEnabled(false);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnScommetti, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
						.addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
						.addComponent(lblInserisciQuantitDi, Alignment.LEADING))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInserisciQuantitDi)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnScommetti, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
	}
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		SwingUtilities.invokeAndWait(new ScuderiePanelCreator());
	}
}

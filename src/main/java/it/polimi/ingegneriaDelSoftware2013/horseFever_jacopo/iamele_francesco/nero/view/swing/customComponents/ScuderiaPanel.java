package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ScuderiaPanel extends JPanel {
	
	private static class ScuderiaPanelCreator implements Runnable {

		private ScuderiaPanel panel;
		private GiocatoreView giocatore;

		public ScuderiaPanelCreator(GiocatoreView giocatore) {
			this.giocatore = giocatore;
		}

		public void run() {
			JFrame temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel  = new ScuderiaPanel(giocatore);
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
			System.out.println(panel.getImagePanel().getSize());
		}
		
		public ScuderiaPanel getGiocatorePanel(){
			return panel;
		}

	}
	
	private JLabel lblNewLabel;
	private ImagePanel imagePanel;
	private JPanel holder;
	
	private final GiocatoreView giocatoreAssociato;
	private JLabel lblInserisciImportoDa;
	private JTextField textField;
	private JButton btnScommetti;
	private JLabel lblScuderia;
	
	public ScuderiaPanel(GiocatoreView giocatore) {
		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		holder.setBorder(new LineBorder(Color.BLACK, 2));
		setBorder(new LineBorder(new Color(0, 0, 0), 5));
		setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("Carta Azione");
		lblNewLabel.setFont(new Font("Kalinga", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel imageHolder = new JPanel();
		imageHolder.setOpaque(false);
		imagePanel = new ImagePanel(Risorse.getIInstance().getImmagine(giocatore.getPersonaggio()));
		FlowLayout flowLayout = (FlowLayout) imagePanel.getLayout();
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		lblInserisciImportoDa = new JLabel("Inserisci importo da scommettere:");
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setHorizontalAlignment(SwingConstants.TRAILING);
		textField.setColumns(10);
		
		btnScommetti = new JButton("SCOMMETTI!");
		btnScommetti.setEnabled(false);
		
		lblScuderia = new JLabel("Scuderia");
		lblScuderia.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout groupLayout = new GroupLayout(holder);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(imageHolder, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblInserisciImportoDa)
						.addComponent(textField, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
						.addComponent(btnScommetti, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblScuderia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblScuderia)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imageHolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblInserisciImportoDa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnScommetti)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		imageHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		imageHolder.add(imagePanel);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		holder.setLayout(groupLayout);
		holder.setMaximumSize(getPreferredSize());
		add(holder);
		this.giocatoreAssociato = giocatore;
		aggiorna();
	}
	public void aggiorna(){
		GiocatoreView giocatore = giocatoreAssociato;
		utenteLabel().setText(giocatore.getNomeUtente());
		String l = "";
		int i = 0;
		for(Scuderia s : giocatore.getScuderie()){
			i++;
			l += s.getColore().name();
			if(i<giocatore.getScuderie().size())
				l += "; ";
		}
	}
	
	protected JLabel utenteLabel() {
		return lblNewLabel;
	}
	
	public static void main(String[] args) throws FormatoFileErratoException, IOException{
		List<Scuderia> scuderie = new LinkedList<Scuderia>();
		scuderie.add(new Scuderia(Colore.BLU, 5));
		List<CartaAzione> carte = new LinkedList<CartaAzione>();
		Personaggio p = Risorse.getIInstance().getPersonaggi().get(0);
		GiocatoreView giocatore = new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco");
		ScuderiaPanelCreator gPC = new ScuderiaPanelCreator(giocatore);
		try {
			SwingUtilities.invokeAndWait(gPC);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected ImagePanel getImagePanel() {
		return imagePanel;
	}
	public GiocatoreView getGiocatoreAssociato(){
		return giocatoreAssociato;
	}
	public JPanel getHolder() {
		return holder;
	}
	public void seleziona(boolean selezionato) {
		if(selezionato){
			Color c = Color.getHSBColor(0.8f, 1f, 0.8f);
			setBackground(c);
		} else {
			setBackground(Color.WHITE);
		}
	}
}

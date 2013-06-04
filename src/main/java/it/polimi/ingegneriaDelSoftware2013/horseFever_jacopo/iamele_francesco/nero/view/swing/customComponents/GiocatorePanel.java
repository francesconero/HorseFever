package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class GiocatorePanel extends JPanel {
	private static class GiocatorePanelCreator implements Runnable {

		private GiocatorePanel panel;
		private GiocatoreView giocatore;

		public GiocatorePanelCreator(GiocatoreView giocatore) {
			this.giocatore = giocatore;
		}

		public void run() {
			JFrame temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel  = new GiocatorePanel(giocatore);
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
			System.out.println(panel.getImagePanel().getSize());
		}
		
		public GiocatorePanel getGiocatorePanel(){
			return panel;
		}

	}
	
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private ImagePanel imagePanel;
	private JPanel holder;
	
	private final GiocatoreView giocatoreAssociato;
	
	public GiocatorePanel(GiocatoreView giocatore) {
		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		holder.setBorder(new LineBorder(Color.BLACK, 2));
		setBorder(new LineBorder(new Color(0, 0, 0), 5));
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		lblNewLabel = new JLabel("Nome Giocatore");
		lblNewLabel.setFont(new Font("Kalinga", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Nome Personaggio:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblDanari = new JLabel("Danari");
		lblDanari.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDanari.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblScuderia = new JLabel("Scuderia");
		lblScuderia.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblScuderia.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblNewLabel_2 = new JLabel("New Label");
		
		lblNewLabel_3 = new JLabel("New label");
		
		lblNewLabel_4 = new JLabel("New label");
		
		JPanel imageHolder = new JPanel();
		imageHolder.setOpaque(false);
		imagePanel = new ImagePanel(Risorse.getIInstance().getImmagine(giocatore.getPersonaggio()));
		FlowLayout flowLayout = (FlowLayout) imagePanel.getLayout();
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		GroupLayout groupLayout = new GroupLayout(holder);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(imageHolder, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.TRAILING, 0, 169, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblDanari)
								.addComponent(lblScuderia))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3, 0, 55, Short.MAX_VALUE)
								.addComponent(lblNewLabel_4, 0, 55, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imageHolder, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDanari)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblScuderia)
						.addComponent(lblNewLabel_4))
					.addGap(11))
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
		personaggioLabel().setText(giocatore.getPersonaggio().getNome());
		danariLabel().setText(giocatore.getDanari()+" $");
		String l = "";
		int i = 0;
		for(Scuderia s : giocatore.getScuderie()){
			i++;
			l += s.getColore().name();
			if(i<giocatore.getScuderie().size())
				l += "; ";
		}
		scuderiaLabel().setText(l);
	}
	
	protected JLabel utenteLabel() {
		return lblNewLabel;
	}
	protected JLabel personaggioLabel() {
		return lblNewLabel_2;
	}
	protected JLabel danariLabel() {
		return lblNewLabel_3;
	}
	protected JLabel scuderiaLabel() {
		return lblNewLabel_4;
	}
	
	public static void main(String[] args) throws FormatoFileErratoException, IOException{
		List<Scuderia> scuderie = new LinkedList<Scuderia>();
		scuderie.add(new Scuderia(Colore.BLU, 5));
		List<CartaAzione> carte = new LinkedList<CartaAzione>();
		Personaggio p = Risorse.getIInstance().getPersonaggi().get(0);
		GiocatoreView giocatore = new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco", 25);
		GiocatorePanelCreator gPC = new GiocatorePanelCreator(giocatore);
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

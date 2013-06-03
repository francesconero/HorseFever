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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class CartaAzionePanel extends JPanel {
	
	private static class CartaAzionePanelCreator implements Runnable {

		private CartaAzionePanel panel;
		private CartaAzione cartaAzione;

		public CartaAzionePanelCreator(CartaAzione cartaAzione) {
			this.cartaAzione = cartaAzione;
		}

		public void run() {
			JFrame temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel  = new CartaAzionePanel(cartaAzione);
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
			System.out.println(panel.getImagePanel().getSize());
		}
		
		public CartaAzionePanel getGiocatorePanel(){
			return panel;
		}

	}
	private ImagePanel imagePanel;
	private JPanel holder;
	
	private final CartaAzione cartaAzioneAssociata;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	
	public CartaAzionePanel(CartaAzione cartaAzione) {
		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		holder.setBorder(new LineBorder(Color.BLACK, 2));
		setBorder(new LineBorder(new Color(0, 0, 0), 5));
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		JPanel imageHolder = new JPanel();
		imageHolder.setOpaque(false);
		imagePanel = new ImagePanel(Risorse.getIInstance().getImmagine(cartaAzione));
		imagePanel.getLayout();
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JButton btnNewButton = new JButton("GIOCA CARTA");
		btnNewButton.setEnabled(false);
		
		lblNewLabel_1 = new JLabel("Carta Azione");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout groupLayout = new GroupLayout(holder);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
						.addComponent(imageHolder, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(imageHolder, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addGap(15))
		);
		imageHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		imageHolder.add(imagePanel);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		holder.setLayout(groupLayout);
		holder.setMaximumSize(getPreferredSize());
		add(holder);
		this.cartaAzioneAssociata = cartaAzione;
		aggiorna();
	}
	
	public void aggiorna(){
		lblNewLabel.setText(cartaAzioneAssociata.getNome());
	}
	
	public static void main(String[] args) throws FormatoFileErratoException, IOException{
		List<Scuderia> scuderie = new LinkedList<Scuderia>();
		scuderie.add(new Scuderia(Colore.BLU, 5));
		List<CartaAzione> carte = new LinkedList<CartaAzione>();
		Personaggio p = Risorse.getIInstance().getPersonaggi().get(0);
		GiocatoreView giocatore = new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco");
		CartaAzionePanelCreator gPC = new CartaAzionePanelCreator(Risorse.getIInstance().getCarteAzione().get((int) (Math.random()*18)));
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
	
	public CartaAzione getCartaAzioneAssociata(){
		return cartaAzioneAssociata;
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

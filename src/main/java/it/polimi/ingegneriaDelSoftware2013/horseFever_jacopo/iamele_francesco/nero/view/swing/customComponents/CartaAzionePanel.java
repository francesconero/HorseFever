package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.customLayouts.AspectRatioLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CartaAzionePanel extends JPanel {
	
	private JLabel lblNewLabel;
	private ImagePanel imagePanel;
	private JPanel holder;
	
	private final CartaAzione cartaAzioneAssociata;
	
	public CartaAzionePanel(CartaAzione cartaAzione) {
		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		holder.setMinimumSize(new Dimension(250, 400));
		holder.setPreferredSize(new Dimension(250, 400));
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 5), new EmptyBorder(15, 15, 15, 15)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		holder.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(holder);
		GridBagLayout gbl_holder = new GridBagLayout();
		gbl_holder.columnWidths = new int[]{100, 0};
		gbl_holder.rowHeights = new int[]{10, 29, 329, 14, 10, 0};
		gbl_holder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_holder.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		holder.setLayout(gbl_holder);
		
		lblNewLabel = new JLabel("Nome Giocatore");
		lblNewLabel.setFont(new Font("Kalinga", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		holder.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel imageHolder = new JPanel();
		imageHolder.setBorder(new EmptyBorder(10, 10, 10, 10));
		imageHolder.setOpaque(false);
		imagePanel = new ImagePanel(Risorse.getIInstance().getImmagine(cartaAzione));
		FlowLayout flowLayout = (FlowLayout) imagePanel.getLayout();
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		imageHolder.setLayout(new AspectRatioLayout());
		imageHolder.add(imagePanel);
		GridBagConstraints gbc_imageHolder = new GridBagConstraints();
		gbc_imageHolder.fill = GridBagConstraints.BOTH;
		gbc_imageHolder.insets = new Insets(0, 0, 5, 0);
		gbc_imageHolder.gridx = 0;
		gbc_imageHolder.gridy = 2;
		holder.add(imageHolder, gbc_imageHolder);
		this.cartaAzioneAssociata = cartaAzione;
		aggiorna();
	}
	
	public void aggiorna(){
		GiocatoreView giocatore = null;// = cartaAzioneAssociata;
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
		new CartaAzionePanel(Risorse.getIInstance().getCartaAzione("Globus Obscuros"));
	}
	
	protected ImagePanel getImagePanel() {
		return imagePanel;
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

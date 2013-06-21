package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.EffettoAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.customLayouts.AspectRatioLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public final class CartaAzionePanel extends JPanel {

	private JLabel lblNewLabel;
	private ImagePanel imagePanel;
	private JPanel holder;

	private final CartaAzione cartaAzioneAssociata;
	private JLabel lblEffetti;
	private int currentRow = 1;

	public CartaAzionePanel(CartaAzione cartaAzione) {
		String nomeCartaAzione;
		String letteraCartaAzione = null;

		if(cartaAzione.isCoperta()){
			nomeCartaAzione = "Carta Azione";
		} else {
			nomeCartaAzione = cartaAzione.getNome();
			letteraCartaAzione = cartaAzione.getLettera()+"";
		}

		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		holder.setMinimumSize(new Dimension(250, 400));
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 5), new EmptyBorder(15, 15, 15, 15)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		holder.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(holder);
		GridBagLayout gbl_holder = new GridBagLayout();
		gbl_holder.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_holder.rowHeights = new int[]{10, 0, 0, 0};
		gbl_holder.columnWidths = new int[]{100, 0};
		gbl_holder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		holder.setLayout(gbl_holder);

		lblNewLabel = new JLabel(nomeCartaAzione);
		lblNewLabel.setFont(new Font("Kalinga", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = currentRow++;
		holder.add(lblNewLabel, gbc_lblNewLabel);

		JPanel imageHolder = new JPanel();
		imageHolder.setBorder(new EmptyBorder(10, 10, 10, 10));
		imageHolder.setOpaque(false);
		imagePanel = new ImagePanel(Risorse.getInstance().getImmagine(cartaAzione));
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		imageHolder.setLayout(new AspectRatioLayout());
		imageHolder.add(imagePanel);
		GridBagConstraints gbc_imageHolder = new GridBagConstraints();
		gbc_imageHolder.fill = GridBagConstraints.BOTH;
		gbc_imageHolder.insets = new Insets(0, 0, 5, 0);
		gbc_imageHolder.gridx = 0;
		gbc_imageHolder.gridy = currentRow++;
		gbc_imageHolder.weighty = 1;
		holder.add(imageHolder, gbc_imageHolder);

		JLabel lblLettera;

		if(!cartaAzione.isCoperta()){
			if(cartaAzione.getLettera()!=0){
				lblLettera = new JLabel("Lettera: "+letteraCartaAzione);
			} else {
				lblLettera = new JLabel("Nessuna lettera associata");
			}
			lblLettera.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblLettera = new GridBagConstraints();
			gbc_lblLettera.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblLettera.gridx = 0;
			gbc_lblLettera.gridy = currentRow++;		
			holder.add(lblLettera, gbc_lblLettera);
		}
		this.cartaAzioneAssociata = cartaAzione;

		aggiorna();
	}

	public void aggiorna(){
		if(cartaAzioneAssociata.isCoperta()){
			GridBagConstraints gbc_lblEffetti = new GridBagConstraints();
			gbc_lblEffetti.insets = new Insets(0, 0, 5, 0);
			gbc_lblEffetti.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblEffetti.gridx = 0;
			gbc_lblEffetti.gridy = currentRow++;
			holder.add(Box.createVerticalStrut(20), gbc_lblEffetti);
		} else {
			lblEffetti = new JLabel("EFFETTI");
			lblEffetti.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblEffetti = new GridBagConstraints();
			gbc_lblEffetti.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblEffetti.gridx = 0;
			gbc_lblEffetti.gridy = currentRow++;
			holder.add(lblEffetti, gbc_lblEffetti);
			for(EffettoAzione eA: cartaAzioneAssociata.getEffetti()){
				EffettoPanel effettoPanel = new EffettoPanel(eA);
				gbc_lblEffetti = new GridBagConstraints();
				gbc_lblEffetti.insets = new Insets(0, 0, 5, 0);
				gbc_lblEffetti.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblEffetti.gridx = 0;
				gbc_lblEffetti.gridy = currentRow++;
				holder.add(effettoPanel, gbc_lblEffetti);
			}
		}
		revalidate();
		repaint();
	}

	protected ImagePanel getImagePanel() {
		return imagePanel;
	}

	public JPanel getHolder() {
		return holder;
	}

	public CartaAzione getCartaAzioneAssociata() {
		return cartaAzioneAssociata;
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

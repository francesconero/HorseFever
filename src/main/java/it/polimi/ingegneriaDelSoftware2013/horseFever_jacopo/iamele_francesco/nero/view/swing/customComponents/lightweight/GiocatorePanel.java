package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
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
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
	
	private GiocatoreView giocatoreAssociato;
	private JPanel panel_1;
	private JLabel label;
	private JLabel pVLabel;
	
	public GiocatorePanel(GiocatoreView giocatore) {
		setBackground(Color.WHITE);
		holder = new JPanel();
		holder.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		holder.setMinimumSize(new Dimension(50, 50));
		holder.setBackground(Color.LIGHT_GRAY);
		holder.setForeground(Color.BLACK);
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 5), new EmptyBorder(15, 15, 15, 15)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		holder.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		add(holder);
		GridBagLayout gbl_holder = new GridBagLayout();
		gbl_holder.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		gbl_holder.columnWidths = new int[]{100, 0};
		gbl_holder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
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
		imagePanel = new ImagePanel(Risorse.getInstance().getImmagine(giocatore.getPersonaggio()));
		FlowLayout flowLayout = (FlowLayout) imagePanel.getLayout();
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		imageHolder.setLayout(new AspectRatioLayout());
		imageHolder.add(imagePanel);
		GridBagConstraints gbc_imageHolder = new GridBagConstraints();
		gbc_imageHolder.fill = GridBagConstraints.BOTH;
		gbc_imageHolder.insets = new Insets(0, 0, 5, 0);
		gbc_imageHolder.gridx = 0;
		gbc_imageHolder.gridy = 2;
		gbc_imageHolder.weighty = 1;
		holder.add(imageHolder, gbc_imageHolder);
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		holder.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{10, 73, 169, 10, 0};
		gbl_panel_1.rowHeights = new int[]{14, 14, 14, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Personaggio:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("New Label");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 0;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblDanari = new JLabel("Danari:");
		lblDanari.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDanari.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDanari = new GridBagConstraints();
		gbc_lblDanari.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblDanari.insets = new Insets(0, 0, 5, 5);
		gbc_lblDanari.gridx = 1;
		gbc_lblDanari.gridy = 1;
		panel_1.add(lblDanari, gbc_lblDanari);
		
		lblNewLabel_3 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 1;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblScuderia = new JLabel("Scuderia:");
		lblScuderia.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblScuderia.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblScuderia = new GridBagConstraints();
		gbc_lblScuderia.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblScuderia.insets = new Insets(0, 0, 5, 5);
		gbc_lblScuderia.gridx = 1;
		gbc_lblScuderia.gridy = 2;
		panel_1.add(lblScuderia, gbc_lblScuderia);
		
		lblNewLabel_4 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_4.gridx = 2;
		gbc_lblNewLabel_4.gridy = 2;
		panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		label = new JLabel("Punti Vittoria:");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		panel_1.add(label, gbc_label);
		
		pVLabel = new JLabel(giocatore.getPuntiVittoria()+"");
		GridBagConstraints gbc_pVLabel = new GridBagConstraints();
		gbc_pVLabel.fill = GridBagConstraints.BOTH;
		gbc_pVLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pVLabel.gridx = 2;
		gbc_pVLabel.gridy = 3;
		panel_1.add(pVLabel, gbc_pVLabel);
		this.giocatoreAssociato = giocatore;
		aggiorna();
	}
	
	private void aggiorna(){
		GiocatoreView giocatore = giocatoreAssociato;
		utenteLabel().setText(giocatore.getNomeUtente());
		personaggioLabel().setText(giocatore.getPersonaggio().getNome());
		danariLabel().setText(giocatore.getDanari()+" $");
		pVLabel.setText(giocatore.getPuntiVittoria()+"");
		
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
		Personaggio p = Risorse.getInstance().getPersonaggi().get(0);
		GiocatoreView giocatore = new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco", 25, false);
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

	public void setGiocatoreAssociato(GiocatoreView g) {
		this.giocatoreAssociato = g;
		aggiorna();
	}
}

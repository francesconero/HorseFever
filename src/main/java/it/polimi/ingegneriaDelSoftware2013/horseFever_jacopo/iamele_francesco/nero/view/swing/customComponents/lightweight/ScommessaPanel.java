package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoScommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.JFrameTester;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.customLayouts.AspectRatioLayout;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ScommessaPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ScommessaPanel(Scommessa scommessa, String nomeGiocatore) {

		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{10, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Scommessa di "+nomeGiocatore);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new AspectRatioLayout());

		ImagePanel imagePanel;
		String percorsoImmagine = selezionaImmagine(scommessa);		
		imagePanel = new ImagePanel(percorsoImmagine);
		imagePanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.add(imagePanel);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 0, 10, 10));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.fill = GridBagConstraints.BOTH;
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = 1;
		panel.add(lblTipo, gbc_lblTipo);

		JLabel lblTipo_1 = new JLabel(scommessa.getTipoScommessa().name());
		GridBagConstraints gbc_lblTipo_1 = new GridBagConstraints();
		gbc_lblTipo_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblTipo_1.anchor = GridBagConstraints.WEST;
		gbc_lblTipo_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblTipo_1.gridx = 1;
		gbc_lblTipo_1.gridy = 1;
		panel.add(lblTipo_1, gbc_lblTipo_1);

		JLabel lblDanari = new JLabel("Danari:");
		GridBagConstraints gbc_lblDanari = new GridBagConstraints();
		gbc_lblDanari.insets = new Insets(0, 0, 5, 5);
		gbc_lblDanari.fill = GridBagConstraints.BOTH;
		gbc_lblDanari.gridx = 0;
		gbc_lblDanari.gridy = 2;
		panel.add(lblDanari, gbc_lblDanari);

		JLabel lblDanari_1 = new JLabel(scommessa.getDanariScommessi()+"");
		GridBagConstraints gbc_lblDanari_1 = new GridBagConstraints();
		gbc_lblDanari_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblDanari_1.anchor = GridBagConstraints.WEST;
		gbc_lblDanari_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblDanari_1.gridx = 1;
		gbc_lblDanari_1.gridy = 2;
		panel.add(lblDanari_1, gbc_lblDanari_1);
		
		JLabel lblScuderia = new JLabel("Scuderia:");
		GridBagConstraints gbc_lblScuderia = new GridBagConstraints();
		gbc_lblScuderia.insets = new Insets(0, 0, 5, 5);
		gbc_lblScuderia.gridx = 0;
		gbc_lblScuderia.gridy = 3;
		panel.add(lblScuderia, gbc_lblScuderia);
		
		JLabel lblScuderia_1 = new JLabel(scommessa.getScuderia().name());
		GridBagConstraints gbc_lblScuderia_1 = new GridBagConstraints();
		gbc_lblScuderia_1.fill = GridBagConstraints.BOTH;
		gbc_lblScuderia_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblScuderia_1.gridx = 1;
		gbc_lblScuderia_1.gridy = 3;
		panel.add(lblScuderia_1, gbc_lblScuderia_1);

	}

	private String selezionaImmagine(Scommessa scommessa) {
		String percorsoImmagine;
		if(scommessa.getTipoScommessa().equals(TipoScommessa.PIAZZATO)){
			switch(scommessa.getScuderia()){
				case BIANCO:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataBianco");
					break;
				case BLU:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataBlu");
					break;
				case GIALLO:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataGiallo");
					break;
				case NERO:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataNero");
					break;
				case ROSSO:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataRosso");
					break;
				case VERDE:
					percorsoImmagine = Risorse.getInstance().getImmagine("PiazzataVerde");
					break;
				default:
					throw new IllegalStateException("Tipo di colore sconosciuto");				
			}
		} else {
			switch(scommessa.getScuderia()){
				case BIANCO:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteBianco");
					break;
				case BLU:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteBlu");
					break;
				case GIALLO:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteGiallo");
					break;
				case NERO:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteNero");
					break;
				case ROSSO:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteRosso");
					break;
				case VERDE:
					percorsoImmagine = Risorse.getInstance().getImmagine("VincenteVerde");
					break;
				default:
					throw new IllegalStateException("Tipo di colore sconosciuto");				
			}
		}
		return percorsoImmagine;
	}
	
	public static void main(String[] args){
		JFrameTester.test(new ScommessaPanel(new Scommessa(TipoScommessa.PIAZZATO, 500, Colore.GIALLO), "Marco"));
	}

}

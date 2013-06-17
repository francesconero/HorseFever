package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class ImageColoreListCellRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(
			JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {

		JLabel label = (JLabel) super.getListCellRendererComponent(
				list, value, index, isSelected, cellHasFocus);

		Icon icon = getIcon(list, value, index, isSelected, cellHasFocus);
		label.setIcon(icon);
		return label;
	}

	private Icon getIcon(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		Colore c = (Colore) value;
		ImageIcon out =  new ImageIcon(Risorse.getInstance().getImmagine(c.name()));
		return out;
	}

}

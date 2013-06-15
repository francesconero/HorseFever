package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.customLayouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

public class AspectRatioLayout implements LayoutManager {

	public void addLayoutComponent(String arg0, Component arg1) {
	}

	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		float x = insets.left;
		float y = insets.top;
		x += insets.right;
		y += insets.bottom;

		Component onlyChild = parent.getComponent(0);
		Dimension d = onlyChild.getPreferredSize();
		x = (float) (parent.getSize().getWidth() - x);
		y = (float) (parent.getSize().getHeight() - y);

		float proportion = onlyChild.getPreferredSize().width/(float)onlyChild.getPreferredSize().height;
		int outWidth;
		int outHeight;
		int pad = 0;
		if (x / y > proportion) {  
			outWidth = (int) (y * proportion);  
			outHeight = (int) y;
			pad = (int) ((x-outWidth)/2);
			onlyChild.setBounds(new Rectangle(pad + insets.left, insets.top, outWidth, outHeight));
		} else {  
			outWidth = (int) x;  
			outHeight = (int) (x / proportion);  
			pad = (int) ((y-outHeight)/2);
			onlyChild.setBounds(new Rectangle(insets.left, pad + insets.top, outWidth, outHeight));
		}
	}

	public Dimension minimumLayoutSize(Container arg0) {
		return preferredLayoutSize(arg0);
	}

	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		int x = insets.left;
		int y = insets.top;
		x += insets.right;
		y += insets.bottom;

		Component[] onlyChild = parent.getComponents();
		Dimension d = onlyChild[0].getPreferredSize();
		d.width += x;
		d.height += y;

		return d;
	}

	public void removeLayoutComponent(Component arg0) {
		// TODO Auto-generated method stub

	}

}

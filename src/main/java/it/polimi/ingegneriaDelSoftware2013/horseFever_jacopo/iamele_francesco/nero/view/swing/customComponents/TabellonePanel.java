package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

public class TabellonePanel extends JPanel {

	private static class TabellonePanelCreator implements Runnable {

		private TabellonePanel panel;

		public void run() {
			JFrame temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel = new TabellonePanel();
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
		}

		public TabellonePanel getTabellonePanel() {
			return panel;
		}

	}

	private List<SegnalinoCavallo> segnalini = new LinkedList<SegnalinoCavallo>();

	private Point puntoFinale = new Point(260, -600);
	private Point[] puntiIniziali = {
			new Point(44, 671),
			new Point(121, 715),
			new Point(202, 715),
			new Point(280, 715),
			new Point(362, 715),
			new Point(435, 715)
	};

	private int[] posizioniAttuali = new int[6];

	private int initialStep = 40;
	private int initialSize = 30;
	private float decreasingFactorStep = 0.9615f;
	private float scalingFactor = 0.965f;

	/**
	 * Create the panel.
	 */
	public TabellonePanel() {
		setBorder(new LineBorder(Color.BLACK, 2));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLayeredPane layeredPane = new JLayeredPane();
		add(layeredPane);
		ImagePanel imagePanel = new ImagePanel(Risorse.getIInstance()
				.getImmagine("Tabellone"));
		imagePanel.setBounds(0, 0, imagePanel.getPreferredSize().width,
				imagePanel.getPreferredSize().height);
		layeredPane.add(imagePanel);
		layeredPane.setPreferredSize(imagePanel.getPreferredSize());

		ScuderiaHighlighter scuderiaHighlighter = new ScuderiaHighlighter();
		scuderiaHighlighter.setBorder(new LineBorder(Color.GREEN, 3));
		scuderiaHighlighter.setBackground(Color.BLACK);
		layeredPane.setLayer(scuderiaHighlighter, 1);
		scuderiaHighlighter.setBounds(15, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter);

		ScuderiaHighlighter scuderiaHighlighter_1 = new ScuderiaHighlighter();
		scuderiaHighlighter_1.setBackground(Color.BLUE);
		layeredPane.setLayer(scuderiaHighlighter_1, 1);
		scuderiaHighlighter_1.setBounds(97, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter_1);

		ScuderiaHighlighter scuderiaHighlighter_2 = new ScuderiaHighlighter();
		scuderiaHighlighter_2.setBackground(Color.GREEN);
		layeredPane.setLayer(scuderiaHighlighter_2, 1);
		scuderiaHighlighter_2.setBounds(179, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter_2);

		ScuderiaHighlighter scuderiaHighlighter_3 = new ScuderiaHighlighter();
		scuderiaHighlighter_3.setBackground(Color.RED);
		layeredPane.setLayer(scuderiaHighlighter_3, 1);
		scuderiaHighlighter_3.setBounds(261, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter_3);

		ScuderiaHighlighter scuderiaHighlighter_4 = new ScuderiaHighlighter();
		scuderiaHighlighter_4.setBackground(Color.YELLOW);
		layeredPane.setLayer(scuderiaHighlighter_4, 1);
		scuderiaHighlighter_4.setBounds(343, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter_4);

		ScuderiaHighlighter scuderiaHighlighter_5 = new ScuderiaHighlighter();
		scuderiaHighlighter_5.setBackground(Color.WHITE);
		layeredPane.setLayer(scuderiaHighlighter_5, 1);
		scuderiaHighlighter_5.setBounds(425, 715, 81, 125);
		layeredPane.add(scuderiaHighlighter_5);

		SegnalinoCavallo segnalinoCavallo = new SegnalinoCavallo();
		segnalinoCavallo.setBackground(Color.BLACK);
		layeredPane.setLayer(segnalinoCavallo, 1);
		segnalinoCavallo.setBounds(44, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo);
		layeredPane.add(segnalinoCavallo);

		SegnalinoCavallo segnalinoCavallo_1 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_1, 1);
		segnalinoCavallo_1.setBackground(Color.BLUE);
		segnalinoCavallo_1.setBounds(121, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_1);
		layeredPane.add(segnalinoCavallo_1);

		SegnalinoCavallo segnalinoCavallo_2 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_2, 1);
		segnalinoCavallo_2.setBackground(Color.GREEN);
		segnalinoCavallo_2.setBounds(202, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_2);
		layeredPane.add(segnalinoCavallo_2);

		SegnalinoCavallo segnalinoCavallo_3 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_3, 1);
		segnalinoCavallo_3.setBackground(Color.RED);
		segnalinoCavallo_3.setBounds(280, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_3);
		layeredPane.add(segnalinoCavallo_3);

		SegnalinoCavallo segnalinoCavallo_4 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_4, 1);
		segnalinoCavallo_4.setBackground(Color.YELLOW);
		segnalinoCavallo_4.setBounds(362, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_4);
		layeredPane.add(segnalinoCavallo_4);

		SegnalinoCavallo segnalinoCavallo_5 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_5, 1);
		segnalinoCavallo_5.setBackground(Color.WHITE);
		segnalinoCavallo_5.setBounds(435, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_5);
		layeredPane.add(segnalinoCavallo_5);

	}

	public static void main(String[] args) throws InterruptedException,
	InvocationTargetException {
		TabellonePanelCreator tPC = new TabellonePanelCreator();

		SwingUtilities.invokeAndWait(tPC);

		long sleepTime = 2000;		

		for(int i = 0; i<18; i++){
			Thread.sleep(sleepTime);
			tPC.panel.muoviCavalli(new int[] { r(), r(), r(), r(), r(), r() });
		}
	}

	private static int r() {
		return (int) (Math.random()*3);
	}

	private void muoviCavalli(int[] is) {
		for (int i = 0; i < segnalini.size(); i++) {
			Point nuovoPunto = elaboraNuovoPunto(i, is[i]);
			spostaSegnalino(i, nuovoPunto, is[i]);
			posizioniAttuali[i] += is[i];
		}
		revalidate();
		repaint();
	}

	private void spostaSegnalino(int cavallo,
			Point nuovoPunto, int passiFatti) {
		int newSize = (int) (initialSize * Math.pow(scalingFactor, posizioniAttuali[cavallo]+1+passiFatti));
		segnalini.get(cavallo).setBounds(nuovoPunto.x, nuovoPunto.y, newSize, newSize);
	}

	private Point elaboraNuovoPunto(int cavallo, int passi) {		
		float newStep = (float) (Math.pow(decreasingFactorStep, posizioniAttuali[cavallo]+1) * initialStep);
		
		float nuovaY = segnalini.get(cavallo).getBounds().y;
		for(int j = 0; j < passi; j++){
			nuovaY -= newStep;
			newStep *= decreasingFactorStep;
		}
		int nuovaX = calcolaX(cavallo, (int) nuovaY);
		return new Point(nuovaX, (int) nuovaY);
	}

	private int calcolaX(int cavallo, int nuovaY) {
		float x2mx1 = puntiIniziali[cavallo].x - puntoFinale.x;		
		float y2my1 = puntiIniziali[cavallo].y -  puntoFinale.y;
		float ymy1 = nuovaY  -  puntoFinale.y;
		return (int) (puntoFinale.x + (((ymy1/y2my1))*x2mx1));
	}
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class TabellonePanel extends JPanel implements ComponentListener {

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
	private List<ScuderiaHighlighter> scuderie = new LinkedList<ScuderiaHighlighter>();

	private Point puntoFinale = new Point(260, -600);
	private Point[] puntiIniziali = { new Point(44, 671), new Point(121, 715),
			new Point(202, 715), new Point(280, 715), new Point(362, 715),
			new Point(435, 715) };

	private int[] posizioniAttuali = new int[6];

	private int initialStep = 40;
	private int initialSize = 30;
	private float decreasingFactorStep = 0.9615f;
	private float scalingFactor = 0.965f;
	private Timer timer;
	private boolean animating = false;
	final Lock lock = new ReentrantLock();
	final Condition notAnimating  = lock.newCondition(); 

	/**
	 * Create the panel.
	 */
	public TabellonePanel() {
		addComponentListener(this);
		setBorder(new LineBorder(Color.BLACK, 2));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		ImagePanel imagePanel = new ImagePanel(Risorse.getIInstance()
				.getImmagine("Tabellone"));
		add(imagePanel);
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));

		JLayeredPane layeredPane = new JLayeredPane();
		imagePanel.add(layeredPane);

		ScuderiaHighlighter scuderiaHighlighter = new ScuderiaHighlighter();
		scuderiaHighlighter.setBorder(new LineBorder(Color.GREEN, 3));
		scuderiaHighlighter.setBackground(Color.BLACK);
		layeredPane.setLayer(scuderiaHighlighter, 1);
		scuderiaHighlighter.setBounds(15, 715, 81, 125);
		scuderie.add(scuderiaHighlighter);
		scuderiaHighlighter.setPosizioneTabellone(scuderiaHighlighter.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter);

		ScuderiaHighlighter scuderiaHighlighter_1 = new ScuderiaHighlighter();
		scuderiaHighlighter_1.setBackground(Color.BLUE);
		layeredPane.setLayer(scuderiaHighlighter_1, 1);
		scuderiaHighlighter_1.setBounds(97, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_1);
		scuderiaHighlighter_1.setPosizioneTabellone(scuderiaHighlighter_1.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_1);

		ScuderiaHighlighter scuderiaHighlighter_2 = new ScuderiaHighlighter();
		scuderiaHighlighter_2.setBackground(Color.GREEN);
		layeredPane.setLayer(scuderiaHighlighter_2, 1);
		scuderiaHighlighter_2.setBounds(179, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_2);
		scuderiaHighlighter_2.setPosizioneTabellone(scuderiaHighlighter_2.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_2);

		ScuderiaHighlighter scuderiaHighlighter_3 = new ScuderiaHighlighter();
		scuderiaHighlighter_3.setBackground(Color.RED);
		layeredPane.setLayer(scuderiaHighlighter_3, 1);
		scuderiaHighlighter_3.setBounds(261, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_3);
		scuderiaHighlighter_3.setPosizioneTabellone(scuderiaHighlighter_3.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_3);

		ScuderiaHighlighter scuderiaHighlighter_4 = new ScuderiaHighlighter();
		scuderiaHighlighter_4.setBackground(Color.YELLOW);
		layeredPane.setLayer(scuderiaHighlighter_4, 1);
		scuderiaHighlighter_4.setBounds(340, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_4);
		scuderiaHighlighter_4.setPosizioneTabellone(scuderiaHighlighter_4.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_4);

		ScuderiaHighlighter scuderiaHighlighter_5 = new ScuderiaHighlighter();
		scuderiaHighlighter_5.setBackground(Color.WHITE);
		layeredPane.setLayer(scuderiaHighlighter_5, 1);
		scuderiaHighlighter_5.setBounds(422, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_5);
		scuderiaHighlighter_5.setPosizioneTabellone(scuderiaHighlighter_5.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_5);

		SegnalinoCavallo segnalinoCavallo = new SegnalinoCavallo();
		segnalinoCavallo.setBackground(Color.BLACK);
		layeredPane.setLayer(segnalinoCavallo, 1);
		segnalinoCavallo.setBounds(44, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo);
		segnalinoCavallo.setCasellaIniziale(new Point2D.Float(0.09579f, 0.8f));
		segnalinoCavallo.setCasellaFinale(new Point2D.Float(0.24201f, 0.19647f));
		segnalinoCavallo.setPosizioneAttuale(segnalinoCavallo.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo);

		SegnalinoCavallo segnalinoCavallo_1 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_1, 1);
		segnalinoCavallo_1.setBackground(Color.BLUE);
		segnalinoCavallo_1.setBounds(121, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_1);
		segnalinoCavallo_1.setCasellaIniziale(new Point2D.Float(0.23865f,0.8f));
		segnalinoCavallo_1.setCasellaFinale(new Point2D.Float(0.31428f, 0.19647f));
		segnalinoCavallo_1.setPosizioneAttuale(segnalinoCavallo_1.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_1);

		SegnalinoCavallo segnalinoCavallo_2 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_2, 1);
		segnalinoCavallo_2.setBackground(Color.GREEN);
		segnalinoCavallo_2.setBounds(202, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_2);
		segnalinoCavallo_2.setCasellaIniziale(new Point2D.Float(0.37047f, 0.8f));
		segnalinoCavallo_2.setCasellaFinale(new Point2D.Float(0.38991f, 0.19647f));
		segnalinoCavallo_2.setPosizioneAttuale(segnalinoCavallo_2.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_2);

		SegnalinoCavallo segnalinoCavallo_3 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_3, 1);
		segnalinoCavallo_3.setBackground(Color.RED);
		segnalinoCavallo_3.setBounds(260, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_3);
		segnalinoCavallo_3.setCasellaIniziale(new Point2D.Float(0.5f, 0.8f));
		segnalinoCavallo_3.setCasellaFinale(new Point2D.Float(0.47226f, 0.19647f));
		segnalinoCavallo_3.setPosizioneAttuale(segnalinoCavallo_3.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_3);

		SegnalinoCavallo segnalinoCavallo_4 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_4, 1);
		segnalinoCavallo_4.setBackground(Color.YELLOW);
		segnalinoCavallo_4.setBounds(362, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_4);
		segnalinoCavallo_4.setCasellaIniziale(new Point2D.Float(0.635f, 0.8f));
		segnalinoCavallo_4.setCasellaFinale(new Point2D.Float(0.53949f, 0.19647f));
		segnalinoCavallo_4.setPosizioneAttuale(segnalinoCavallo_4.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_4);

		SegnalinoCavallo segnalinoCavallo_5 = new SegnalinoCavallo();
		layeredPane.setLayer(segnalinoCavallo_5, 1);
		segnalinoCavallo_5.setBackground(Color.WHITE);
		segnalinoCavallo_5.setBounds(435, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_5);
		segnalinoCavallo_5.setCasellaIniziale(new Point2D.Float(0.765f, 0.8f));
		segnalinoCavallo_5.setCasellaFinale(new Point2D.Float(0.61f, 0.19647f));
		segnalinoCavallo_5.setPosizioneAttuale(segnalinoCavallo_5.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_5);
		layeredPane.setPreferredSize(imagePanel.getPreferredSize());

	}

	public static void main(String[] args) throws InterruptedException,
	InvocationTargetException {
		final TabellonePanelCreator tPC = new TabellonePanelCreator();

		SwingUtilities.invokeAndWait(tPC);

		long sleepTime = 0;

		for (int j = 1; j < 200; j+=2) {
			int i = j%19;
			Thread.sleep(sleepTime);
			SwingUtilities.invokeAndWait(new Runnable() {				
				private int[] movements;

				public void run() {
					tPC.panel.aggiornaPosizioni(movements);
				}				
				public Runnable init(int[] movements){
					this.movements = movements;
					return this;
				}
			}.init(new int[]{r(),r(),r(),i,i,i,i}));
			tPC.panel.waitAnimation();
		}
	}

	private void waitAnimation() {
		lock.lock();
		try{
			while(animating){	
				try {
					notAnimating.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private static int r() {
		return (int) (Math.random()*19);
	}

	private void aggiornaPosizioni(int[] nuovePosizioni) {
		animating = true;
		int delay = 15; //milliseconds

		int i = 0;
		for(SegnalinoCavallo sC : segnalini){
			sC.setPosizioneCorsa(nuovePosizioni[i]);
			i++;
		}		
		ActionListener taskPerformer = new ActionListener() {
			private final long stepTime = 16666666;
			private long lastStep = System.nanoTime() - stepTime;
			private long timePool = 0;
			private boolean finished = false;

			public void actionPerformed(ActionEvent evt) {
				
				timePool += System.nanoTime() - lastStep;
				lastStep = System.nanoTime();
				
				while(timePool>stepTime&&!finished){
					for(SegnalinoCavallo sC : segnalini){
						if(sC.step()){
							finished = true;
						}
					}
					timePool -= stepTime;
					timePool += System.nanoTime() - lastStep;
					lastStep = System.nanoTime();
				}
				if(finished){
					timer.stop();
					lock.lock();
					try{
						animating = false;
						notAnimating.signalAll();
					} finally {
						lock.unlock();
					}
				}
				aggiorna();
			}
		};
		timer = new Timer(delay, taskPerformer);
		timer.start();

	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentResized(ComponentEvent e) {
		if (e.getComponent().equals(this)) {
			aggiorna();
		}
	}

	private void aggiorna() {
		for(SegnalinoCavallo sC: segnalini){
			int newX = (int) (getWidth()*sC.getPosizioneAttuale().x);
			int newY = (int) (getHeight()*sC.getPosizioneAttuale().y);
			int newSizeX = (int) (getWidth()*sC.getDimensione().x);
			int newSizeY = (int) (getHeight()*sC.getDimensione().y);
			sC.setBounds(newX-newSizeX/2, newY-newSizeY/2, newSizeX, newSizeY);
		}
		for(ScuderiaHighlighter s : scuderie){
			int newX = (int) (getWidth()*s.getPosizioneTabellone().x);
			int newY = (int) (getHeight()*s.getPosizioneTabellone().y);
			int newSizeX = (int) (getWidth()*s.getDimensione().x);
			int newSizeY = (int) (getHeight()*s.getDimensione().y);
			s.setBounds(newX, newY, newSizeX, newSizeY);
		}
		revalidate();
		repaint();
	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}

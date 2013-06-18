package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.customLayouts.AspectRatioLayout;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.ImagePanel;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.ScuderiaHighlighter;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.SegnalinoCavallo;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.SegnalinoQuotazione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.SegnalinoTurno;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.AnimatableComponent;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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

	}

	private List<SegnalinoCavallo> segnalini = new LinkedList<SegnalinoCavallo>();
	private List<ScuderiaHighlighter> scuderie = new LinkedList<ScuderiaHighlighter>();
	private List<SegnalinoQuotazione> segnaliniQuotazione = new LinkedList<SegnalinoQuotazione>();

	private int initialSize = 30;
	private ScuderiaHighlighter scuderiaSelezionata; 
	private SegnalinoTurno segnalinoTurno;
	private MouseAdapter scuderiaMouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getComponent() instanceof ScuderiaHighlighter){
				selezionaScuderia((ScuderiaHighlighter)e.getComponent());
			}
		}
	};
	private ImagePanel imagePanel;
	private int animationDelay = 15;
	public Map<ActionListener, Timer> timers = new HashMap<ActionListener, Timer>();

	/**
	 * Create the panel.
	 */
	public TabellonePanel() {
		addComponentListener(this);
		setBorder(null);
		setLayout(new AspectRatioLayout());
		imagePanel = new ImagePanel(Risorse.getInstance()
				.getImmagine("Tabellone"));
		add(imagePanel);
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
		JLayeredPane layeredPane = new JLayeredPane();
		imagePanel.add(layeredPane);

		final ScuderiaHighlighter scuderiaHighlighter = new ScuderiaHighlighter(Colore.NERO);
		scuderiaHighlighter.setBackground(Color.BLACK);
		layeredPane.setLayer(scuderiaHighlighter, 1);
		scuderiaHighlighter.setBounds(15, 715, 81, 125);
		scuderie.add(scuderiaHighlighter);
		scuderiaHighlighter.setPosizioneTabellone(scuderiaHighlighter.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter);
		scuderiaHighlighter.addMouseListener(scuderiaMouseAdapter);
		scuderiaHighlighter.seleziona(true);
		scuderiaSelezionata = scuderiaHighlighter;

		final ScuderiaHighlighter scuderiaHighlighter_1 = new ScuderiaHighlighter(Colore.BLU);
		scuderiaHighlighter_1.setBackground(Color.BLUE);
		layeredPane.setLayer(scuderiaHighlighter_1, 1);
		scuderiaHighlighter_1.setBounds(97, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_1);
		scuderiaHighlighter_1.setPosizioneTabellone(scuderiaHighlighter_1.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_1);
		scuderiaHighlighter_1.addMouseListener(scuderiaMouseAdapter);
		final ScuderiaHighlighter scuderiaHighlighter_2 = new ScuderiaHighlighter(Colore.VERDE);
		scuderiaHighlighter_2.setBackground(Color.GREEN);
		layeredPane.setLayer(scuderiaHighlighter_2, 1);
		scuderiaHighlighter_2.setBounds(179, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_2);
		scuderiaHighlighter_2.setPosizioneTabellone(scuderiaHighlighter_2.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_2);
		scuderiaHighlighter_2.addMouseListener(scuderiaMouseAdapter);
		final ScuderiaHighlighter scuderiaHighlighter_3 = new ScuderiaHighlighter(Colore.ROSSO);
		scuderiaHighlighter_3.setBackground(Color.RED);
		layeredPane.setLayer(scuderiaHighlighter_3, 1);
		scuderiaHighlighter_3.setBounds(261, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_3);
		scuderiaHighlighter_3.setPosizioneTabellone(scuderiaHighlighter_3.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_3);
		scuderiaHighlighter_3.addMouseListener(scuderiaMouseAdapter);
		final ScuderiaHighlighter scuderiaHighlighter_4 = new ScuderiaHighlighter(Colore.GIALLO);
		scuderiaHighlighter_4.setBackground(Color.YELLOW);
		layeredPane.setLayer(scuderiaHighlighter_4, 1);
		scuderiaHighlighter_4.setBounds(340, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_4);
		scuderiaHighlighter_4.setPosizioneTabellone(scuderiaHighlighter_4.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_4);
		scuderiaHighlighter_4.addMouseListener(scuderiaMouseAdapter);
		final ScuderiaHighlighter scuderiaHighlighter_5 = new ScuderiaHighlighter(Colore.BIANCO);
		scuderiaHighlighter_5.setBackground(Color.WHITE);
		layeredPane.setLayer(scuderiaHighlighter_5, 1);
		scuderiaHighlighter_5.setBounds(422, 715, 81, 125);
		scuderie.add(scuderiaHighlighter_5);
		scuderiaHighlighter_5.setPosizioneTabellone(scuderiaHighlighter_5.getLocation(), new Point(595,850));
		layeredPane.add(scuderiaHighlighter_5);
		scuderiaHighlighter_5.addMouseListener(scuderiaMouseAdapter);
		SegnalinoCavallo segnalinoCavallo = new SegnalinoCavallo(Colore.NERO);
		segnalinoCavallo.setBackground(Color.BLACK);
		layeredPane.setLayer(segnalinoCavallo, 1);
		segnalinoCavallo.setBounds(44, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo);
		segnalinoCavallo.setCasellaIniziale(new Point2D.Float(0.09579f, 0.8f));
		segnalinoCavallo.setCasellaFinale(new Point2D.Float(0.24201f, 0.19647f));
		segnalinoCavallo.getAnimatable().setPosizioneAttuale(segnalinoCavallo.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo);

		SegnalinoCavallo segnalinoCavallo_1 = new SegnalinoCavallo(Colore.BLU);
		layeredPane.setLayer(segnalinoCavallo_1, 1);
		segnalinoCavallo_1.setBackground(Color.BLUE);
		segnalinoCavallo_1.setBounds(121, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_1);
		segnalinoCavallo_1.setCasellaIniziale(new Point2D.Float(0.23865f,0.8f));
		segnalinoCavallo_1.setCasellaFinale(new Point2D.Float(0.31428f, 0.19647f));
		segnalinoCavallo_1.getAnimatable().setPosizioneAttuale(segnalinoCavallo_1.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_1);

		SegnalinoCavallo segnalinoCavallo_2 = new SegnalinoCavallo(Colore.VERDE);
		layeredPane.setLayer(segnalinoCavallo_2, 1);
		segnalinoCavallo_2.setBackground(Color.GREEN);
		segnalinoCavallo_2.setBounds(202, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_2);
		segnalinoCavallo_2.setCasellaIniziale(new Point2D.Float(0.37047f, 0.8f));
		segnalinoCavallo_2.setCasellaFinale(new Point2D.Float(0.38991f, 0.19647f));
		segnalinoCavallo_2.getAnimatable().setPosizioneAttuale(segnalinoCavallo_2.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_2);

		SegnalinoCavallo segnalinoCavallo_3 = new SegnalinoCavallo(Colore.ROSSO);
		layeredPane.setLayer(segnalinoCavallo_3, 1);
		segnalinoCavallo_3.setBackground(Color.RED);
		segnalinoCavallo_3.setBounds(260, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_3);
		segnalinoCavallo_3.setCasellaIniziale(new Point2D.Float(0.5f, 0.8f));
		segnalinoCavallo_3.setCasellaFinale(new Point2D.Float(0.47226f, 0.19647f));
		segnalinoCavallo_3.getAnimatable().setPosizioneAttuale(segnalinoCavallo_3.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_3);

		SegnalinoCavallo segnalinoCavallo_4 = new SegnalinoCavallo(Colore.GIALLO);
		layeredPane.setLayer(segnalinoCavallo_4, 1);
		segnalinoCavallo_4.setBackground(Color.YELLOW);
		segnalinoCavallo_4.setBounds(362, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_4);
		segnalinoCavallo_4.setCasellaIniziale(new Point2D.Float(0.635f, 0.8f));
		segnalinoCavallo_4.setCasellaFinale(new Point2D.Float(0.53949f, 0.19647f));
		segnalinoCavallo_4.getAnimatable().setPosizioneAttuale(segnalinoCavallo_4.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_4);

		SegnalinoCavallo segnalinoCavallo_5 = new SegnalinoCavallo(Colore.BIANCO);
		layeredPane.setLayer(segnalinoCavallo_5, 1);
		segnalinoCavallo_5.setBackground(Color.WHITE);
		segnalinoCavallo_5.setBounds(435, 671, initialSize, initialSize);
		segnalini.add(segnalinoCavallo_5);
		segnalinoCavallo_5.setCasellaIniziale(new Point2D.Float(0.765f, 0.8f));
		segnalinoCavallo_5.setCasellaFinale(new Point2D.Float(0.61f, 0.19647f));
		segnalinoCavallo_5.getAnimatable().setPosizioneAttuale(segnalinoCavallo_5.getCasellaIniziale());
		layeredPane.add(segnalinoCavallo_5);
		layeredPane.setPreferredSize(imagePanel.getPreferredSize());

		SegnalinoQuotazione segnalinoQuotazione = new SegnalinoQuotazione(Colore.NERO);
		segnaliniQuotazione.add(segnalinoQuotazione);
		layeredPane.add(segnalinoQuotazione);

		SegnalinoQuotazione segnalinoQuotazione_1 = new SegnalinoQuotazione(Colore.BLU);
		segnaliniQuotazione.add(segnalinoQuotazione_1);
		layeredPane.add(segnalinoQuotazione_1);

		SegnalinoQuotazione segnalinoQuotazione_2 = new SegnalinoQuotazione(Colore.VERDE);
		segnaliniQuotazione.add(segnalinoQuotazione_2);
		layeredPane.add(segnalinoQuotazione_2);

		SegnalinoQuotazione segnalinoQuotazione_3 = new SegnalinoQuotazione(Colore.ROSSO);
		segnaliniQuotazione.add(segnalinoQuotazione_3);
		layeredPane.add(segnalinoQuotazione_3);

		SegnalinoQuotazione segnalinoQuotazione_4 = new SegnalinoQuotazione(Colore.GIALLO);
		segnaliniQuotazione.add(segnalinoQuotazione_4);
		layeredPane.add(segnalinoQuotazione_4);

		SegnalinoQuotazione segnalinoQuotazione_5 = new SegnalinoQuotazione(Colore.BIANCO);
		segnaliniQuotazione.add(segnalinoQuotazione_5);
		layeredPane.add(segnalinoQuotazione_5);

		segnalinoTurno = new SegnalinoTurno();
		segnalinoTurno.setBounds(141, 280, 10, 10);
		layeredPane.add(segnalinoTurno);

	}

	protected void selezionaScuderia(
			ScuderiaHighlighter scuderiaHighlighter) {
		if(!scuderiaHighlighter.isSelected()){
			scuderiaSelezionata.seleziona(false);
			scuderiaHighlighter.seleziona(true);
			scuderiaSelezionata = scuderiaHighlighter;
		}
		firePropertyChange("SELEZIONATO", null, scuderiaSelezionata.getColore());
	}



	private static Map<Colore, Integer> creaArrivi() {
		Map<Colore, Integer> out = new HashMap<Colore, Integer>();
		List<Integer> poolPosition = new LinkedList<Integer>();
		for(int i = 0; i<6; i++){
			poolPosition.add(i+1);
		}
		Collections.shuffle(poolPosition);
		for(Colore c : Colore.values()){
			if(Math.random()>0.5d){
				out.put(c, poolPosition.remove(0));
			}
		}
		return out;
	}

	private static Map<Colore, Integer> creaQuotatzioni() {
		Map<Colore, Integer> out = new HashMap<Colore, Integer>();
		out.put(Colore.NERO, r(2,7));
		out.put(Colore.BLU, r(2,7));
		out.put(Colore.VERDE, r(2,7));
		out.put(Colore.ROSSO, r(2,7));
		out.put(Colore.GIALLO, r(2,7));
		out.put(Colore.BIANCO, r(2,7));
		return out;
	}

	private static Integer r(int min, int max) {
		int out = new Random().nextInt(max-min+1);
		out += min;
		return out;
	}

	private static int r() {
		return (int) (Math.random()*19);
	}

	public void aggiornaClassifica(final List<Colore> classifica) {
		for(int i = 0; i < classifica.size(); i++){
			for(SegnalinoCavallo sC: segnalini){
				if(sC.getColore().equals(classifica.get(i))){
					sC.classifica(i+1);
				}
			}
		}
		ActionListener taskPerformer = new AnimationActionListener(segnalini);
		Timer timer = new Timer(animationDelay, taskPerformer);
		timers.put(taskPerformer, timer);
		timer.start();
	}

	public void aggiornaTurno(int nuovoTurno) {
		segnalinoTurno.setTurno(nuovoTurno);
		List<AnimatableComponent> aCL = new LinkedList<AnimatableComponent>();
		aCL.add(segnalinoTurno);
		ActionListener taskPerformer = new AnimationActionListener(aCL);
		Timer timer = new Timer(animationDelay, taskPerformer);
		timers.put(taskPerformer, timer);
		timer.start();
	}

	public void aggiornaQuotazioni(Map<Colore, Integer> nuoveQuotazioni){		
		for(SegnalinoQuotazione sQ : segnaliniQuotazione){
			sQ.setQuotazione(nuoveQuotazioni.get(sQ.getColore()));
		}
		ActionListener taskPerformer = new AnimationActionListener(segnaliniQuotazione);
		Timer timer = new Timer(animationDelay, taskPerformer);
		timers.put(taskPerformer, timer);
		timer.start();
	}

	public void aggiornaPosizioni(Map<Colore, Integer> nuovePosizioni) {
		for(SegnalinoCavallo sC : segnalini){
			if(nuovePosizioni.containsKey(sC.getColore())&&!sC.isArrivato()){
				sC.setPosizioneCorsa(nuovePosizioni.get(sC.getColore()));
			}
		}
		ActionListener taskPerformer = new AnimationActionListener(segnalini);
		Timer timer = new Timer(animationDelay, taskPerformer);
		timers.put(taskPerformer, timer);
		timer.start();
	}

	public void resetCorsa(){
		for(SegnalinoCavallo sC : segnalini){
			sC.reset();
		}
		ActionListener taskPerformer = new AnimationActionListener(segnalini);
		Timer timer = new Timer(animationDelay, taskPerformer);
		timers.put(taskPerformer, timer);
		timer.start();
	}

	private class AnimationActionListener implements ActionListener {
		final List<? extends AnimatableComponent> animatableComponents;
		private final long stepTime = 16666666;
		private long lastStep = System.nanoTime() - stepTime;
		private long timePool = 0;
		private boolean finished = false;

		private AnimationActionListener(List<? extends AnimatableComponent> animatableComponents){
			this.animatableComponents = animatableComponents;
		}

		public void actionPerformed(ActionEvent evt) {
			timePool += System.nanoTime() - lastStep;
			lastStep = System.nanoTime();

			while(timePool>stepTime&&!finished){
				for(AnimatableComponent aC : animatableComponents){
					if(aC.getAnimatable().step()){
						finished = true;
					}
				}
				timePool -= stepTime;
				timePool += System.nanoTime() - lastStep;
				lastStep = System.nanoTime();
			}
			if(finished){
				timers.get(this).stop();
				timers.remove(this);
				firePropertyChange("ANIMATION_STOPPED", null, null);
			}
			aggiorna();
		}
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
		int newX = (int) (getImagePanel().getWidth()*segnalinoTurno.getAnimatable().getPosizioneAttuale().x);
		int newY = (int) (getImagePanel().getHeight()*segnalinoTurno.getAnimatable().getPosizioneAttuale().y);
		int newSizeX = (int) (getImagePanel().getWidth()*segnalinoTurno.getAnimatable().getDimensione().x);
		int newSizeY = (int) (getImagePanel().getHeight()*segnalinoTurno.getAnimatable().getDimensione().y);
		segnalinoTurno.setBounds(newX-newSizeX/2, newY-newSizeY/2, newSizeX, newSizeY);

		for(SegnalinoCavallo sC: segnalini){
			newX = (int) (getImagePanel().getWidth()*sC.getAnimatable().getPosizioneAttuale().x);
			newY = (int) (getImagePanel().getHeight()*sC.getAnimatable().getPosizioneAttuale().y);
			newSizeX = (int) (getImagePanel().getWidth()*sC.getAnimatable().getDimensione().x);
			newSizeY = (int) (getImagePanel().getHeight()*sC.getAnimatable().getDimensione().y);
			sC.setBounds(newX-newSizeX/2, newY-newSizeY/2, newSizeX, newSizeY);
		}
		for(ScuderiaHighlighter s : scuderie){
			newX = (int) (getImagePanel().getWidth()*s.getPosizioneTabellone().x);
			newY = (int) (getImagePanel().getHeight()*s.getPosizioneTabellone().y);
			newSizeX = (int) (getImagePanel().getWidth()*s.getDimensione().x);
			newSizeY = (int) (getImagePanel().getHeight()*s.getDimensione().y);
			s.setBounds(newX, newY, newSizeX, newSizeY);
		}
		for(SegnalinoQuotazione sQ : segnaliniQuotazione){
			newX = (int) (getImagePanel().getWidth()*sQ.getAnimatable().getPosizioneAttuale().x);
			newY = (int) (getImagePanel().getHeight()*sQ.getAnimatable().getPosizioneAttuale().y);
			newSizeX = (int) (getImagePanel().getWidth()*sQ.getAnimatable().getDimensione().x);
			newSizeY = (int) (getImagePanel().getHeight()*sQ.getAnimatable().getDimensione().y);
			sQ.setBounds(newX-newSizeX/2, newY-newSizeY/2, newSizeX, newSizeY);
		}
		revalidate();
		repaint();
	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
	protected ImagePanel getImagePanel() {
		return imagePanel;
	}

	public Colore getScuderiaSelezionata() {
		return scuderiaSelezionata.getColore();
	}
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.containers;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.FormatoFileErratoException;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Personaggio;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.risorse.Risorse;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.lightweight.GiocatorePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class GiocatoriPanel extends JPanel implements MouseListener {
	private static final String proprietaSelezionato = "SELEZIONATO";
	private JPanel panel_1;
	private HashMap<GiocatoreView, GiocatorePanel> panelGiocatori = new HashMap<GiocatoreView, GiocatorePanel>();
	private GiocatoreView selezionato = null;

	private static class GiocatoriPanelCreator implements Runnable {
		private GiocatoriPanel panel;
		private JFrame temp;

		public void run() {
			temp = new JFrame();
			temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
			panel  = new GiocatoriPanel();
			temp.getContentPane().add(panel);
			temp.pack();
			temp.setVisible(true);
		}

		public GiocatoriPanel getGiocatorePanel(){
			return panel;
		}

		public void pack() {
			temp.pack();
		}


	}

	public GiocatoriPanel() {
		setMinimumSize(new Dimension(400, 10));
		setPreferredSize(new Dimension(400, 10));
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		addMouseListener(this);
		setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel = new JLabel("Giocatori");
		panel.add(lblNewLabel);
		Dimension newMaximumSize = panel.getPreferredSize();
		newMaximumSize.width = panel.getMaximumSize().width;
	}

	public void aggiorna(List<GiocatoreView> giocatori, GiocatoreView mioGiocatore) {	
		giocatori = MetodiDiSupporto.creaListaOrdinata(giocatori, mioGiocatore);
		List<GiocatoreView> toDelete = new LinkedList<GiocatoreView>();
		List<GiocatoreView> toAdd = new LinkedList<GiocatoreView>();
		List<GiocatoreView> toUpdate = new LinkedList<GiocatoreView>();
		for(GiocatoreView g: panelGiocatori.keySet()){
			if(!giocatori.contains(g)){
				toDelete.add(g);
			}
		}
		for(GiocatoreView g: giocatori){
			if(!panelGiocatori.containsKey(g)){
				toAdd.add(g);
			} else {
				panelGiocatori.put(g, panelGiocatori.get(g));
				toUpdate.add(g);
			}
		}
		for(GiocatoreView g: toDelete){
			if(g.equals(selezionato)){
				selezionato=null;
			}
			panel_1.remove(panelGiocatori.remove(g).getParent());
		}
		for(GiocatoreView g: toAdd){
			boolean isMioGiocatore = mioGiocatore.equals(g);
			panelGiocatori.put(g, new GiocatorePanel(g, isMioGiocatore));
			JPanel holder = new JPanel();
			holder.setOpaque(false);
			holder.add(panelGiocatori.get(g));
			Dimension newSize = panelGiocatori.get(g).getPreferredSize();
			newSize.width += 10;
			newSize.height += 10;
			holder.setPreferredSize(newSize);
			holder.setMaximumSize(newSize);
			panelGiocatori.get(g).addMouseListener(this);
			panel_1.add(holder);
		}
		for(GiocatoreView g: toUpdate){
			GiocatorePanel panelGiocatore = panelGiocatori.get(g);
			panelGiocatore.setGiocatoreAssociato(g);
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	public static void main(String[] args) throws FormatoFileErratoException, IOException, InvocationTargetException, InterruptedException{
		List<Scuderia> scuderie = new LinkedList<Scuderia>();
		scuderie.add(new Scuderia(Colore.BLU, 5));
		Personaggio p = Risorse.getInstance().getPersonaggi().get(0);
		GiocatoreView giocatore = new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco", 25, false);
		List<GiocatoreView> listGiocatori = new LinkedList<GiocatoreView>();
		listGiocatori.add(giocatore);
		GiocatoriPanelCreator gPC = new GiocatoriPanelCreator();
		SwingUtilities.invokeAndWait(gPC);
		while(true){
			long sleepTime = 1000;
			listGiocatori.add(new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco", 251, true));
			gPC.getGiocatorePanel().aggiorna(listGiocatori, giocatore);
			Thread.sleep(sleepTime);
			listGiocatori.add(new GiocatoreView(new Giocatore(2500, 2, scuderie, p), "Francesco", 252, true));
			gPC.getGiocatorePanel().aggiorna(listGiocatori, giocatore);
			Thread.sleep(sleepTime);
			listGiocatori.remove(listGiocatori.size()-1);
			gPC.getGiocatorePanel().aggiorna(listGiocatori, giocatore);
			Thread.sleep(sleepTime);
			listGiocatori.remove(listGiocatori.size()-1);
			gPC.getGiocatorePanel().aggiorna(listGiocatori, giocatore);
			Thread.sleep(sleepTime );
		}
	}

	protected JPanel getPanel() {
		return panel_1;
	}

	public void mousePressed(MouseEvent arg0) {
		if(arg0.getComponent() instanceof GiocatorePanel){
			GiocatorePanel pannelloCliccato = (GiocatorePanel) arg0.getComponent();
			if(selezionato!=null){
				panelGiocatori.get(selezionato).seleziona(false);
			}
			selezionato = pannelloCliccato.getGiocatoreAssociato();
			pannelloCliccato.seleziona(true);
			firePropertyChange(proprietaSelezionato, null,  selezionato);
		} else {
			if(selezionato!=null){
				panelGiocatori.get(selezionato).seleziona(false);				
				selezionato = null;
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) { }	public void mouseEntered(MouseEvent arg0) {	}	public void mouseExited(MouseEvent arg0) {	}	public void mouseReleased(MouseEvent arg0) {	}
}

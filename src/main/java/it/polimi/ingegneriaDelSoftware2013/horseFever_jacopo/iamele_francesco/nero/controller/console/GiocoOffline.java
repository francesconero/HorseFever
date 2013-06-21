package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.console;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.ControlloreUtenti;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.controller.gioco.ControlloreFasiGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.exception.GestoreEccezioni;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Giocatore;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.GiocatoreView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Mazziere;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.PosizionaCarta;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scommessa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Scuderia;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.StatoDelGioco;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.TipoFaseGiocoFamily;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte.CartaAzione;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.mosseCorsa.MossaCorsa;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils.MetodiDiSupporto;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.console.ConsoleView;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs.LauncherFrame;
import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.other.GraphicalConsole;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

public class GiocoOffline implements ControlloreUtenti, WindowListener{

	private final GraphicalConsole gC;
	private final ControlloreFasiGioco gioco;
	private final ConsoleView view;
	private Map<Giocatore, String> nomi = new LinkedHashMap<Giocatore, String>();
	private StatoDelGioco ultimoAggiornamento;

	public GiocoOffline(LauncherFrame lF){
		Thread.setDefaultUncaughtExceptionHandler(GestoreEccezioni.getInstance());
		gC = new GraphicalConsole(true);
		gC.getFrame().addWindowListener(this);
		gC.getFrame().addWindowListener(lF);
		gC.show();
		view = new ConsoleView(gC.getInputStream(), gC);
		int numeroGiocatori = view.chiediNumeroGiocatori();
		gioco = new ControlloreFasiGioco(numeroGiocatori, new Mazziere(), this);
	}

	public void inizia(){
		gioco.inizia();
	}

	public void accettaUtenti(List<Giocatore> giocatori) {
		List<String> listaNomi = view.chiediNomi(giocatori.size());
		assert(listaNomi.size() == giocatori.size());
		for(Giocatore g : giocatori){
			nomi.put(g, listaNomi.remove(0));
		}		
	}

	public Scommessa riceviScommessa(Giocatore giocatore) {
		String nomeGiocatore = nomi .get(giocatore);
		if(ultimoAggiornamento.getTipoFaseGiocoFamily().equals(TipoFaseGiocoFamily.F_S_ANTIORARIA)){
			if(!view.chiediConferma("Vuoi scommettere ancora?", nomeGiocatore)){
				return new Scommessa(null, 0, null);
			}
		}
		return view.chiediScommessa(nomeGiocatore);
	}

	public List<Colore> riceviSoluzioneConflitto(Giocatore giocatore, List<Scuderia> conflitto) {
		List<Colore> colori = new LinkedList<Colore>();
		for(Scuderia s : conflitto){
			colori.add(s.getColore());
		}
		return view.risolviConflitto(colori, nomi.get(giocatore));
	}

	public PosizionaCarta riceviPosizionaCarta(Giocatore giocatore) {
		CartaAzione daGiocare = view.chiediCartaAzione(nomi.get(giocatore), giocatore.getCarteAzione());
		Colore suCuiGiocare = view.chiediScuderia(nomi.get(giocatore));
		return new PosizionaCarta(daGiocare, ultimoAggiornamento.getScuderiaDalColore(suCuiGiocare));
	}

	public void aggiornaUtenti(StatoDelGioco statoDelGioco) {
		ultimoAggiornamento = statoDelGioco;
		switch(ultimoAggiornamento.getTipoFaseGiocoFamily()){
			case DISTRIBUZIONE_CARTE:
				view.scrivi("Siamo nel turno: "+ultimoAggiornamento.getNumTurno());
				view.scrivi("Ecco chi sta giocando: ");
				view.aCapo();
				for(Giocatore g: ultimoAggiornamento.getGiocatori()){
					GiocatoreView gV = new GiocatoreView(g, nomi.get(g), 0, true);
					view.mostraGiocatore(gV);
					view.aCapo();
				}
				view.aCapo();
				view.scrivi("Ecco le quotazioni delle scuderie: ");
				view.scrivi(stampaQuotazioni(ultimoAggiornamento));
				MetodiDiSupporto.dormi(3000);
				break;
			case ELIMINAZIONE_GIOCATORI:
				break;
			case FINETURNO:
				break;
			case F_C_CORSA:
				view.scrivi("Ecco le scommesse puntate: ");
				view.aCapo();
				for(Giocatore g: ultimoAggiornamento.getGiocatori()){
					for(Scommessa s: g.getScommesseEffettuate()){
						view.scrivi(s.toString());
					}
				}
				MetodiDiSupporto.dormi(3000);
				break;
			case F_C_PAGAMENTI_NUOVE_QUOTAZIONI:
				break;
			case F_C_SCOPRICARTAAZIONE:
				view.scrivi("Ecco quali carte azione sono state giocate: ");
				view.aCapo();
				for(Scuderia s: ultimoAggiornamento.getCorsie()){
					view.scrivi("Scuderia: "+s.getColore());
					for(CartaAzione cA: s.getCarteAzione()){
						view.scrivi(cA.toString());
					}
				}
				MetodiDiSupporto.dormi(2000);

				break;
			case F_S_ALTERAZIONE_GARA:
				break;
			case F_S_ANTIORARIA:
				break;
			case F_S_ORARIA:
				break;
			case PREPARAZIONE:
				break;
			case VITTORIA:
				view.scrivi("La partita e' finita: ");
				if(ultimoAggiornamento.getGiocatoreDiTurno()==null){
					view.scrivi("Avete perso tutti! Complimenti!");
				} else {
					view.scrivi("Ha vinto: "+nomi.get(statoDelGioco.getGiocatoreDiTurno()));
				}
				break;
			default:
				break;

		}
	}

	private String stampaQuotazioni(StatoDelGioco ultimoAggiornamento2) {
		Map<Integer,Scuderia> mapOut = new LinkedHashMap<Integer, Scuderia>();
		for(Scuderia s: ultimoAggiornamento2.getCorsie()){
			mapOut.put(s.getQuotazione(), s);
		}
		String out = "";
		for(Integer i : mapOut.keySet()){
			out += "Quotazione 1:"+i+" = scuderia "+mapOut.get(i).getColore();
			out += System.lineSeparator();
		}
		return out;
	}

	public void aggiornaUtenti(StatoDelGioco statoDelGioco,
			List<MossaCorsa> mosseCorsa) {
		ControlloreConsoleCorsa cCC = new ControlloreConsoleCorsa(statoDelGioco, view, nomi);

		for(MossaCorsa mC: mosseCorsa){
			mC.accept(cCC);
			view.scrivi("");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public void conferma(Giocatore giocatore) {

	}

	public void nega(Giocatore giocatore) {
		switch (ultimoAggiornamento.getTipoFaseGiocoFamily()) {
			case F_S_ORARIA:
				view.scrivi("Attenzione la tua scommessa non e' valida, riprova!");
				break;
			case F_S_ANTIORARIA:
				view.scrivi("Attenzione la tua scommessa non e' valida, riprova!");
				break;
			default:
				throw new IllegalStateException("Negazione fuori luogo");
		}
	}

	public void giocatoreEliminato(Giocatore giocatore) {
		view.scrivi("Mi dispiace "+nomi.get(giocatore)+", ma sei stato eliminato!");
	}

	public static void main(String[] args){
		GiocoOffline gO = new GiocoOffline(null);
		gO.inizia();
	}

	public void fine() {
		((GestoreEccezioni)GestoreEccezioni.getInstance()).setChiusuraUtente(true);
		gC.close();
	}

	public void avverti(Giocatore giocatore, String string) {
		view.scrivi(nomi.get(giocatore)+" "+string);
	}

	public JFrame getFrame() {
		return gC.getFrame();
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		fine();
	}

	public void windowClosing(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
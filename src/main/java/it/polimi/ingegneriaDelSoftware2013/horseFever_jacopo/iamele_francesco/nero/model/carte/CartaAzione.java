package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Rappresenta una carta azione presente in HorseFever. Ha una lista di
 * {@link EffettoAzione}, che la rende un po' più elastica della
 * carta azione originale. Classe immutabile
 * 
 * @author Francesco
 * 
 */
public class CartaAzione implements Carta, Serializable{

	private final String nome;
	private final char lettera;
	private final List<EffettoAzione> effetti;
	private final boolean coperta;
	private final boolean positiva;
	private final boolean negativa;

	/**
	 * @param nome	il nome della carta azione
	 * @param lettera	l'eventuale lettera 
	 * @param effetti		lista di effetti (lista unitaria nel caso del gioco originale)
	 */
	public CartaAzione(String nome, char lettera, List<EffettoAzione> effetti, boolean positiva, boolean negativa) {
		this.coperta = false;
		this.nome = nome;
		this.lettera = lettera;
		this.effetti = new LinkedList<EffettoAzione>(effetti);
		this.positiva=positiva;
		this.negativa=negativa;
	}

	public CartaAzione(String nome, List<EffettoAzione> effetti, boolean positiva, boolean negativa) {
		this(nome, (char) 0, effetti, positiva, negativa);
	}

	/**
	 * Costruttore speciale per la carta azione coperta.
	 */
	public CartaAzione() {
		this.coperta = true;
		this.nome = "";
		this.lettera = 0;
		this.effetti = new LinkedList<EffettoAzione>();
		this.positiva=false;
		this.negativa=false;
	}

	public String getNome() {
		return nome;
	}

	public char getLettera() {
		return lettera;
	}

	public List<EffettoAzione> getEffetti() {
		return new LinkedList<EffettoAzione>(effetti);
	}

	public boolean isCoperta() {
		return coperta;
	}
	public boolean isPositiva() {
		return positiva;
	}

	public boolean isNegativa() {
		return negativa;
	}

	@Override
	public String toString() {
		String out = "Carta Azione: " + nome;
		out += "\n";
		if (lettera != 0) {
			out += "Lettera: " + lettera;
			out += "\n";
		}
		out += "Effetti: ";
		out += "\n";
		for (EffettoAzione effetto : effetti) {
			out += effetto;
		}
		out += "\n";
		return out;
	}
}

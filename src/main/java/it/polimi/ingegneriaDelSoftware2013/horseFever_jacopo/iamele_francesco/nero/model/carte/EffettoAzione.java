package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte;


import java.util.LinkedList;
import java.util.List;

/**
 * Questa classe rappresenta uno dei possibili effetti che una {@link CartaAzione} può avere. Il tipo di effetto è definito da {@link TipoAzione}.
 * @author Francesco
 *
 */
public class EffettoAzione {

	private final TipoAzione tipo;
	private final List<Integer> valori;
	
	/**
	 * @param tipo
	 *            il tipo di effetto
	 * @param valori
	 *            lista di valori necessari a definire l'effetto
	 */
	public EffettoAzione(TipoAzione tipo, List<Integer> valori){
		this.tipo = tipo;
		this.valori = new LinkedList<Integer>(valori);
	}

	public TipoAzione getTipo() {
		return tipo;
	}

	public List<Integer> getValori() {
		return new LinkedList<Integer>(valori);
	}
	
	@Override
	public String toString() {
		String out = tipo.toString();
		out += "\n";
		out+="Valori: {";
		out += "\n";
		for(Integer valore : valori){
			out+=valore;
			out += "\n";
		}
		out+="}";
		return out;
	}
	
}

package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.carte;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Questa classe rappresenta uno dei possibili effetti che una {@link CartaAzione} può avere. Il tipo di effetto e' definito da {@link TipoAzione}.
 * @author Francesco
 *
 */
public class EffettoAzione implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valori == null) ? 0 : valori.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EffettoAzione other = (EffettoAzione) obj;
		if (tipo != other.tipo)
			return false;
		if (valori == null) {
			if (other.valori != null)
				return false;
		} else if (!valori.equals(other.valori))
			return false;
		return true;
	}

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

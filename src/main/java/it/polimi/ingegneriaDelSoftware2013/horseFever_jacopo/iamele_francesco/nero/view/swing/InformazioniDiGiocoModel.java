package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractSpinnerModel;

public class InformazioniDiGiocoModel extends AbstractSpinnerModel {
	
	private List<InformazioneGioco> informazioni = new LinkedList<InformazioneGioco>();
	private int index = 0;
	private int currentID = 0;

	public void addInformazione(String informazione){
		informazioni.add(new InformazioneGioco(informazione, getNextID()));
		index = informazioni.size() - 1;
		fireStateChanged();
	}
	
	private Integer getNextID() {
		return currentID ++;
	}

	public Object getNextValue() {
		if(index < informazioni.size() - 1){
			return informazioni.get(index + 1);
		} else {
			return null;
		}
	}

	public Object getPreviousValue() {
		if(index > 0){
			return informazioni.get(index - 1);
		} else {
			return null;
		}
	}

	public Object getValue() {
		return informazioni.get(index);
	}

	public void setValue(Object arg0) {
		if(informazioni.contains(arg0)){
			index = informazioni.indexOf(arg0);
			fireStateChanged();
		} else {
			throw new IllegalArgumentException("Informazione non trovata: "+arg0);
		}
	}
	
	private class InformazioneGioco {
		private String testo;
		private Integer ID;
		
		public InformazioneGioco(String testo, Integer ID){
			this.testo = testo;
			this.ID = ID;
		}
		
		@Override
		public String toString() {
			return testo;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((ID == null) ? 0 : ID.hashCode());
			result = prime * result + ((testo == null) ? 0 : testo.hashCode());
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
			InformazioneGioco other = (InformazioneGioco) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (ID == null) {
				if (other.ID != null)
					return false;
			} else if (!ID.equals(other.ID))
				return false;
			if (testo == null) {
				if (other.testo != null)
					return false;
			} else if (!testo.equals(other.testo))
				return false;
			return true;
		}

		private InformazioniDiGiocoModel getOuterType() {
			return InformazioniDiGiocoModel.this;
		}
		
		
		
	}

}

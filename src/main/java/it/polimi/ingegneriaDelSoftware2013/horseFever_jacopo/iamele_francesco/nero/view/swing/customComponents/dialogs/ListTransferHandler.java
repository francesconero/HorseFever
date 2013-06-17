package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.view.swing.customComponents.dialogs;

import it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.model.Colore;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class ListTransferHandler extends TransferHandler {
	private int[] indices = null;
	private int addIndex = -1;
	private int addCount = 0;

	public boolean canImport(TransferHandler.TransferSupport info) {
		if (!info.isDataFlavorSupported(new DataFlavor(Colore.class, "Colore"))) {
			return false;
		}
		return true;
	}

	private class ColoreTransferable implements Transferable{

		private Colore toTransfer;

		private ColoreTransferable(Colore toTransfer){
			this.toTransfer = toTransfer;
		}
		
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			return toTransfer;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[]{new DataFlavor(Colore.class, "Colore")};
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(new DataFlavor(Color.class, "Colore"));
		}
	}

	protected Transferable createTransferable(JComponent c) {
		JList<Colore> list = (JList<Colore>) c;
		indices = list.getSelectedIndices();
		return new ColoreTransferable((Colore) list.getSelectedValue());
	}

	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}

		JList list = (JList)info.getComponent();
		DefaultListModel listModel = (DefaultListModel)list.getModel();
		JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
		int index = dl.getIndex();
		boolean insert = dl.isInsert();

		Transferable t = info.getTransferable();
		Colore data;
		try {
			data = (Colore)t.getTransferData(new DataFlavor(Colore.class, "Colore"));
		}
		catch (Exception e) { return false; }

		if (insert) {
			listModel.add(index, data);
			addIndex = index;
			addCount++;
		} else {
			listModel.set(index, data);
		}
		return true;
	}

	protected void exportDone(JComponent c, Transferable data, int action) {
		cleanup(c, action == TransferHandler.MOVE);
	}

	protected void cleanup(JComponent c, boolean remove) {
		if (remove && indices != null) {
			JList source = (JList)c;
			DefaultListModel model  = (DefaultListModel)source.getModel();

			if (addCount > 0) {
				for (int i = 0; i < indices.length; i++) {
					if (indices[i] > addIndex) {
						indices[i] += addCount;
					}
				}
			}

			for (int i = indices.length - 1; i >= 0; i--) {
				model.remove(indices[i]);
			}
		}
		indices = null;
		addCount = 0;
		addIndex = -1;
	}
}
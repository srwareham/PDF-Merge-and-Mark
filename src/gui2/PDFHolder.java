package gui2;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class PDFHolder extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3170874323504735330L;

	public abstract int getSelectedIndex();

	public abstract void setSelectedIndex(int index);

	public abstract int[] getSelectedIndicies();

	public abstract boolean contains(String s);

	public abstract String getElementAt(int index);

	public abstract void insertElementAt(String s, int index);

	public abstract int getNumElements();

	public abstract void removeElementAt(int index);

	public abstract JScrollPane getScrollPane();
}

package gui2;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class PDFListHolder extends PDFHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5477386238344587655L;

	private JList<String> myPDFInputs;
	private DefaultListModel<String> myDefaultListModel;

	public PDFListHolder() {
		myPDFInputs = makePDFList();
		for (int i = 0; i < 10; i++) {
			myDefaultListModel.addElement("" + i);
		}
		myPDFInputs.setCellRenderer(new StripedCellRenderer());
		MyMouseAdaptor myMouseAdaptor = new MyMouseAdaptor();
		myPDFInputs.addMouseListener(myMouseAdaptor);
		myPDFInputs.addMouseMotionListener(myMouseAdaptor);

	}

	public JList<String> getList() {
		return myPDFInputs;
	}

	@Override
	public int getSelectedIndex() {
		return myPDFInputs.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int index) {
		myPDFInputs.setSelectedIndex(index);

	}

	@Override
	public int[] getSelectedIndicies() {
		return myPDFInputs.getSelectedIndices();
	}

	@Override
	public boolean contains(String s) {
		return myDefaultListModel.contains(s);
	}

	@Override
	public String getElementAt(int index) {
		return myDefaultListModel.getElementAt(index);
	}

	@Override
	public void insertElementAt(String s, int index) {
		myDefaultListModel.insertElementAt(s, index);

	}

	@Override
	public int getNumElements() {
		return myDefaultListModel.getSize();
	}

	@Override
	public void removeElementAt(int index) {
		myDefaultListModel.removeElementAt(index);

	}

	private JList<String> makePDFList() {
		myDefaultListModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(myDefaultListModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// list.addListSelectionListener(this);
		// TODO: add flexible dimensions for elements to be shown
		list.setVisibleRowCount(15);
		return list;
	}

	@Override
	public JScrollPane getScrollPane() {
		return new JScrollPane(myPDFInputs);
	}

	class StripedCellRenderer extends DefaultListCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1571630620816373783L;

		@Override
		public Component getListCellRendererComponent(
				@SuppressWarnings("rawtypes") JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
			if (index % 2 == 0) {
				if (isSelected == true) {
					// this is the same color as the default "Selected" for
					// JFileChooser
					label.setBackground(new Color(44, 93, 205));
				} else {
					label.setBackground(Color.LIGHT_GRAY);
				}
			}
			return label;
		}
	}

	private class MyMouseAdaptor extends MouseInputAdapter {
		private boolean mouseDragging = false;
		private int dragSourceIndex;
		private int sourceIndicies[];

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				dragSourceIndex = myPDFInputs.getSelectedIndex();
				sourceIndicies = myPDFInputs.getSelectedIndices();
				mouseDragging = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseDragging = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (mouseDragging) {
				int currentIndex = myPDFInputs.locationToIndex(e.getPoint());
				if (currentIndex != dragSourceIndex) {
					int dragTargetIndex = myPDFInputs.getSelectedIndex();
//					String dragElement = myDefaultListModel
//							.get(dragSourceIndex);
//					myDefaultListModel.remove(dragSourceIndex);
//					myDefaultListModel.add(dragTargetIndex, dragElement);
//					dragSourceIndex = currentIndex;
					//TODO: fix this
					for (int i= 0;i < sourceIndicies.length; i++){
						String dragElement = myDefaultListModel.get(dragSourceIndex + i);
						myDefaultListModel.remove(dragSourceIndex + i);
						myDefaultListModel.add(dragTargetIndex + i, dragElement);
						dragSourceIndex = currentIndex + i;
					}
				}
			}
		}
	}

}

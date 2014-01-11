package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import concatenating.ConcatenateRequestor;

public class SimpleView extends JPanel implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3365675997327788005L;
	private JList<String> myInputs;
	private DefaultListModel<String> myDefaultListModel;

	// TODO: add console. add outputName box. add better input selection /
	// additon
	// TODO: add look into JTree

	// TODO: add localization
	private static final String ADD = "Add URL";
	private static final String REMOVE = "Remove PDF";
	private static final String SUBMIT = "Submit!";
	private JButton myRemoveButton;
	private JButton mySubmitButton;
	private JTextField employeeName;
	private JTextField myOutputName;

	public SimpleView() {
		super(new BorderLayout());

		myInputs = makePDFList();
		myInputs.setCellRenderer(new StripedCellRenderer());
		JScrollPane aPDFListPane = new JScrollPane(myInputs);

		AddButton hireButton = new AddButton();

		myRemoveButton = makeRemoveButton();

		employeeName = new JTextField(25);
		myOutputName = new JTextField(25);
		myOutputName.setText("(filename)");
		employeeName.addActionListener(hireButton.getHireListener());
		employeeName.getDocument().addDocumentListener(
				hireButton.getHireListener());

		// Create a panel that uses BoxLayout.
		JPanel buttonsPane = makeButtonsPane(hireButton);

		add(aPDFListPane, BorderLayout.CENTER);
		add(buttonsPane, BorderLayout.PAGE_END);
	}

	private JButton makeFileChooser() {
		final PDFFileChooser chooser = new PDFFileChooser();

		JButton button = new JButton("Add File(s)");
		// JFileChooser chooser = c.getChooser();
		// button.addActionListener(chooser.getDoChooseListener());
		button.addActionListener(new ActionListener() {
			// TODO: this doesnt work
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = myInputs.getSelectedIndex(); // get selected
															// index
				if (index == -1) { // no selection, so insert at beginning
					index = 0;
				} else { // add after the selected item
					index++;
				}

				chooser.showOpenDialog(null);
				java.util.List<File> fs = new ArrayList<File>();
				for (File f : chooser.getSelectedFiles()) {
					if (f.isDirectory()) {
						// TODO: abstract out getDirContents and remove temp
						// call
						fs.addAll(chooser.getDirContents(f, chooser.TEMP()));
					} else if (f.isFile()) {
						fs.add(f);
					}
				}
				for (File f : fs) {
					System.out.println(f.getAbsolutePath());
				}

				if (fs.size() == 0) {
					// TODO: add localization
					System.out.println("NO PDFS SELECTED!");
				}
				for (File f : fs) {
					if (myDefaultListModel.contains(f.getAbsolutePath())) {
						continue;// TODO: make this better and add error message
					}
					myDefaultListModel.insertElementAt(f.getAbsolutePath(),
							index);

					myInputs.setSelectedIndex(index);
					myInputs.ensureIndexIsVisible(index);
					index++;
				}
				// TODO: add abstraction
				mySubmitButton.setEnabled(true);
			}

		});

		return button;
	}

	/*
	 * Deprecated private JButton makeFileChooserzzz() { // TODO: add
	 * localization JButton button = new JButton("Add File(s)"); final
	 * JFileChooser chooser = new JFileChooser();
	 * chooser.setCurrentDirectory(new File(SystemConfiguration
	 * .getWorkingDir())); chooser.setMultiSelectionEnabled(true);
	 * chooser.setDialogTitle("Choose PDF(s) To Merge!");
	 * 
	 * // FileNameExtensionFilter a = new FileNameExtensionFilter(); // TODO:
	 * replace with custom FilenameFilter but still want to somehow // have
	 * *.pdf maybe keep both but have one draw upon the other
	 * FileNameExtensionFilter fileFiler = new FileNameExtensionFilter( "*.pdf",
	 * "pdf"); chooser.setFileFilter(fileFiler);
	 * 
	 * chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	 * button.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) {
	 * chooser.showOpenDialog(null); java.util.List<File> fs = new
	 * ArrayList<File>(); for (File f : chooser.getSelectedFiles()) { if
	 * (f.isDirectory()) { fs.addAll(getAllFiles(f)); } else if (f.isFile()) {
	 * fs.add(f); } }
	 * 
	 * if (fs.size() == 0) { System.out.println("NO PDFS SELECTED!"); } for
	 * (File f : fs) { System.out.println(f.getAbsolutePath()); } } });
	 * 
	 * return button; }
	 * 
	 * 
	 * 
	 * // TODO: Clean this up like crazy! but hey, it works. Abstract as much
	 * out // as possible private java.util.List<File> getAllFiles(File dir) {
	 * if (!dir.isDirectory()) { return new ArrayList<File>(); } FileManager fm
	 * = new FileManager(); return fm.getDirContents(dir.getAbsolutePath(), new
	 * FilenameFilter() {
	 * 
	 * @Override public boolean accept(File dir, String name) { File f = new
	 * File(dir + SystemConfiguration.getFilePathDelimiter() + name); // return
	 * (f.isDirectory() || name.endsWith(".pdf")); }
	 * 
	 * // @Override // public String getDescription(){ // return "*pdf"; // }
	 * 
	 * }); }
	 */

	// TODO: abstract this out with multiple classes
	private void submitAndSendRequest() {

		if (myDefaultListModel.getSize() == 0) {
			// TODO: throw error and change methodology
			return;
		}
		for (int i = 0; i < myDefaultListModel.getSize(); i++) {
			String s = myDefaultListModel.get(i);
			System.out.println(s);
		}
		ConcatenateRequestor requestor = new ConcatenateRequestor();
		java.util.List<String> inputPaths = new ArrayList<String>();

		for (int i = 0; i < myDefaultListModel.getSize(); i++) {
			inputPaths.add(myDefaultListModel.getElementAt(i));
		}

		// TODO: add non-determinism
		// String outputLocation =
		// "/Users/srwareham/workspace/PDFMergeAndMark/testOutput/test.pdf";
		requestor.makeRequest(inputPaths, myOutputName.getText(), false);
	}

	// TODO: add error handling
	private JButton makeSubmitButton() {
		JButton submitButton = new JButton(SUBMIT);
		submitButton.setEnabled(false);
		mySubmitButton = submitButton;
		// submitButton.setEnabled(false);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitAndSendRequest();
			}
		});
		return submitButton;
	}

	private JPanel makeButtonsPane(JButton hireButton) {
		JPanel topLevel = new JPanel();
		topLevel.setLayout(new BoxLayout(topLevel, BoxLayout.Y_AXIS));
		JPanel managePDFsPanel = new JPanel();
		managePDFsPanel.setLayout(new BoxLayout(managePDFsPanel,
				BoxLayout.LINE_AXIS));
		managePDFsPanel.add(myRemoveButton);
		// buttonPane.add(Box.createHorizontalStrut(1));
		// buttonPane.add(Box.createHorizontalStrut(10));
		managePDFsPanel.add(employeeName);
		managePDFsPanel.add(hireButton);
		managePDFsPanel.add(makeFileChooser());
		managePDFsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		topLevel.add(managePDFsPanel);
		JButton submitButton = makeSubmitButton();
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		topLevel.add(new JSeparator(SwingConstants.HORIZONTAL));

		JPanel submitPanel = new JPanel();
		// submitPanel.add(employeeName);
		submitPanel.add(new JLabel("Desired Filename:"));
		submitPanel.add(myOutputName);
		submitPanel.add(submitButton);

		topLevel.add(submitPanel);
		return topLevel;
	}

	private JList<String> makePDFList() {
		myDefaultListModel = new DefaultListModel<String>();

		// Create the list and put it in a scroll pane.
		JList<String> list = new JList<String>(myDefaultListModel);
		// list.
		// Allow moving items in blocks.
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addListSelectionListener(this);
		// TODO: add flexible dimensions for elements to be shown
		list.setVisibleRowCount(15);
		return list;
	}

	private JButton makeRemoveButton() {
		JButton removeButton = new JButton(REMOVE);
		removeButton.setActionCommand(REMOVE);
		removeButton.addActionListener(new FireListener());
		// Don't want to be able to remove from an empty initial list.
		removeButton.setEnabled(false);
		return removeButton;
	}

	private class FireListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// This method can be called only if
			// there's a valid selection
			// so go ahead and remove whatever's selected.
			// TODO: indicies shift at every removal. need to account for this.
			// Because get selected Indicies is in order, this will work
			int[] stuff = myInputs.getSelectedIndices();
			int last = stuff.length - 1;
			for (int i = last; i >= 0; i--) {
				int index = stuff[(i)];
				myDefaultListModel.remove(index);
				int size = myDefaultListModel.getSize();
				if (size == 0) { // Nobody's left, disable firing.
					myRemoveButton.setEnabled(false);

				} else { // Select an index.
					if (index == myDefaultListModel.getSize()) {
						// removed item in last position
						index--;
					}

					myInputs.setSelectedIndex(index);
					myInputs.ensureIndexIsVisible(index);
				}
			}
			// TODO: add abstraction
			if (myDefaultListModel.getSize() == 0) {
				mySubmitButton.setEnabled(false);
			}
		}
	}

	// class AddFromChooser implements ActionListener {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// int index = myInputs.getSelectedIndex(); // get selected index
	// if (index == -1) { // no selection, so insert at beginning
	// index = 0;
	// } else { // add after the selected item
	// index++;
	// }
	//
	// myDefaultListModel.insertElementAt(employeeName.getText(), index);
	// // If we just wanted to add to the end, we'd do this:
	// // listModel.addElement(employeeName.getText());
	//
	// // Reset the text field.
	// employeeName.requestFocusInWindow();
	// employeeName.setText("");
	//
	// // Select the new item and make it visible.
	// myInputs.setSelectedIndex(index);
	// myInputs.ensureIndexIsVisible(index);
	// // TODO: add abstraction
	// mySubmitButton.setEnabled(true);
	// System.out.println("ENABLED");
	//
	// }
	//
	// }

	// This listener is shared by the text field and the hire button.
	class HireListener implements ActionListener, DocumentListener {
		private boolean alreadyEnabled = false;
		private JButton button;

		public HireListener(JButton button) {
			this.button = button;
		}

		public HireListener() {

		}

		public void setButton(JButton button) {
			this.button = button;
		}

		// Required by ActionListener.
		public void actionPerformed(ActionEvent e) {
			String name = employeeName.getText();

			// User didn't type in a unique name...
			if (name.equals("") || alreadyInList(name)) {
				Toolkit.getDefaultToolkit().beep();
				employeeName.requestFocusInWindow();
				employeeName.selectAll();
				return;
			}

			int index = myInputs.getSelectedIndex(); // get selected index
			if (index == -1) { // no selection, so insert at beginning
				index = 0;
			} else { // add after the selected item
				index++;
			}

			myDefaultListModel.insertElementAt(employeeName.getText(), index);
			// If we just wanted to add to the end, we'd do this:
			// listModel.addElement(employeeName.getText());

			// Reset the text field.
			employeeName.requestFocusInWindow();
			employeeName.setText("");

			// Select the new item and make it visible.
			myInputs.setSelectedIndex(index);
			myInputs.ensureIndexIsVisible(index);

			// TODO: add abstraction
			mySubmitButton.setEnabled(true);
		}

		// This method tests for string equality. You could certainly
		// get more sophisticated about the algorithm. For example,
		// you might want to ignore white space and capitalization.
		protected boolean alreadyInList(String name) {
			return myDefaultListModel.contains(name);
		}

		// Required by DocumentListener.
		public void insertUpdate(DocumentEvent e) {
			enableButton();
		}

		// Required by DocumentListener.
		public void removeUpdate(DocumentEvent e) {
			handleEmptyTextField(e);
		}

		// Required by DocumentListener.
		public void changedUpdate(DocumentEvent e) {
			if (!handleEmptyTextField(e)) {
				enableButton();
			}
		}

		private void enableButton() {
			if (!alreadyEnabled) {
				button.setEnabled(true);
			}
		}

		private boolean handleEmptyTextField(DocumentEvent e) {
			if (e.getDocument().getLength() <= 0) {
				button.setEnabled(false);
				alreadyEnabled = false;
				return true;
			}
			return false;
		}
	}

	class AddButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5569831733971435238L;
		HireListener myListener;

		public AddButton() {
			super(ADD);
			myListener = new HireListener(this);
			setActionCommand(ADD);
			addActionListener(myListener);
			setEnabled(false);
		}

		public HireListener getHireListener() {
			return myListener;
		}
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

	// This method is required by ListSelectionListener.
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (myInputs.getSelectedIndex() == -1) {
				// No selection, disable fire button.
				myRemoveButton.setEnabled(false);

			} else {
				// Selection, enable the fire button.
				myRemoveButton.setEnabled(true);
			}
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("SimpleView");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new SimpleView();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
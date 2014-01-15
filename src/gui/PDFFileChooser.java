package gui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import util.SystemConfiguration;

public class PDFFileChooser extends JFileChooser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 165182830705946372L;

	private List<File> mySelectedFiles = new ArrayList<File>();

	public PDFFileChooser() {
		// setCurrentDirectory(new File(SystemConfiguration.getWorkingDir()));
		setCurrentDirectory(new File(System.getProperty("user.home")));
		setMultiSelectionEnabled(true);
		setDialogTitle("Choose PDF(s) To Merge!");
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setAcceptAllFileFilterUsed(false);
		setFileFilter(new PDFFileFilter());
		this.setApproveButtonText("Add PDFs");//TODO: add localization
//		this.setSelectedFile(new File(System.getProperty("user.home")));//TODO: remove redundancy if works
		this.changeToParentDirectory();
//		this.approveSelection();
	}

	public List<File> getSelectedPDFs() {
		return mySelectedFiles;
	}

	// TODO: probably give the gitDirContents call its own thread and interrupt
	// if takes too long. perhaps give an error message that directory is too
	// deep
	// public ActionListener getDoChooseListener() {
	// mySelectedFiles = new ArrayList<File>();
	// return new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// showOpenDialog(null);
	// java.util.List<File> fs = new ArrayList<File>();
	// for (File f : getSelectedFiles()) {
	// if (f.isDirectory()) {
	// fs.addAll(getDirContents(f, new PDFFileFilter()));
	// } else if (f.isFile()) {
	// fs.add(f);
	// }
	// }
	// for (File f : fs) {
	// System.out.println(f.getAbsolutePath());
	// }
	//
	// if (fs.size() == 0) {
	// // TODO: add localization
	// System.out.println("NO PDFS SELECTED!");
	// }
	// mySelectedFiles.addAll(fs);
	// }
	// };
	// }

	public List<File> getDirContents(File dir, FilenameFilter filenameFilter) {
		List<File> contents = new ArrayList<File>();
		if (dir.isDirectory()) {
			contents = addFiles(contents, dir, filenameFilter);

		}
		return contents;
	}

	private List<File> addFiles(List<File> files, File dir, FilenameFilter ff) {
		if (!dir.isDirectory()) {
			files.add(dir);// then is file
			return files;
		}
		for (File file : dir.listFiles(ff)) {
			addFiles(files, file, ff);
		}
		return files;
	}

	public FilenameFilter TEMP() {
		return new PDFFileFilter();
	}

	public FileFilter TEMPFF() {
		return new PDFFileFilter();
	}

	/**
	 * Class used for determining which java.io.File's are relevant. Functions
	 * both with File.listFiles(FilenameFilter) and
	 * JFileChooser.setFileFilter(File.Filter)
	 * 
	 * @author srwareham
	 * 
	 */
	class PDFFileFilter extends FileFilter implements FilenameFilter {

		private static final String DESIRED_EXTENSION = ".pdf";

		@Override
		public boolean accept(File f) {
			return (isAcceptable(f));
		}

		@Override
		public String getDescription() {
			return "*" + DESIRED_EXTENSION;
		}

		@Override
		public boolean accept(File dir, String name) {
			File f = new File(dir + SystemConfiguration.getFilePathDelimiter()
					+ name);
			return (isAcceptable(f));
		}

		private boolean isCorrectFileType(File f) {
			return f.getName().endsWith(DESIRED_EXTENSION);
		}

		private boolean isAcceptable(File f) {
			// would like to show only relevant sub-directories, but too
			// resource intensive
			return ((f.isDirectory() /* && getDirContents(f, this).size() > 0 */) || isCorrectFileType(f));
		}

	}

	public static void main(String[] args) {
		PDFFileChooser c = new PDFFileChooser();
		List<File> files = c.getDirContents(
				new File("/Users/srwareham/Desktop"), c.TEMP());
		for (File f : files) {
			System.out.println(f.getAbsolutePath());
		}
	}
}

package concatenating;

import java.util.List;
//import java.util.TreeMap;

import pdf.PDF;

public class ConcatenateRequest {
	private List<PDF> myInputs;
	private PDF myOutputPDF;
	private boolean myBookmarkify;
	// private TreeMap<Integer, PDF> myPDFs;
	int myCounter;

	/**
	 * Creates a concatenate request with a series of inputs in their desired
	 * output order, the PDF object that should be written to, and whether or
	 * not bookmarks should be added at concatenation junctions.
	 * 
	 * @param inputs
	 * @param output
	 * @param bookmarkify
	 */
	public ConcatenateRequest(List<PDF> inputs, PDF output, boolean bookmarkify) {
		myInputs = inputs;
		myOutputPDF = output;
		myBookmarkify = bookmarkify;
		// myPDFs = new TreeMap<Integer, PDF>();
		myCounter = 0;
	}

	/**
	 * Retrieve this request's input PDFs in the order they are to be
	 * concatenated in.
	 * 
	 * @return
	 */
	public List<PDF> getInputs() {
		return myInputs;
	}

	/**
	 * Establish what PDFs are to be concatenated--in the order listed.
	 * 
	 * @param inputs
	 */
	public void setInputs(List<PDF> inputs) {
		myInputs = inputs;
	}

	public boolean getBookmarkify() {
		return myBookmarkify;
	}

	public void setBookmarkify(boolean bool) {
		myBookmarkify = bool;
	}

	// TODO: create heurisitic for adding in an individual pdf with some sort of
	// ordering
	// information. Perhaps a method for a series of pdfs and a shared nesting
	// public void addPDF(PDF pdf, int absoluteNumber){
	// myPDFs.
	// }

	public PDF getOutputPDF() {
		return myOutputPDF;
	}

	public void setOutputPDF(PDF out) {
		myOutputPDF = out;
	}

}

package concatenating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import logging.MessageLogger;

import pdf.PDF;
import util.SystemConfiguration;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.itextpdf.text.pdf.SimpleNamedDestination;

public class Concatenator {
	private static final String CREATING_DOCUMENT_EXCEPTION = "documentException";
	private static final String CREATING_IO_EXCEPTION = "ioException";

	public void concatenateAndBookmark(ConcatenateRequest cr) {
		try {
			cab(cr);
		} catch (DocumentException e) {
			e.printStackTrace();
			creationError(CREATING_DOCUMENT_EXCEPTION, cr);
		} catch (IOException e) {
			e.printStackTrace();
			creationError(CREATING_IO_EXCEPTION, cr);
		} 
	}

	// helper function whose purpose is to make error handling more readable.
	private void cab(ConcatenateRequest cr) throws DocumentException,
			IOException, InvalidPdfException {
		List<PDF> inputs = cr.getInputs();
		PDF output = cr.getOutputPDF();
		boolean bookmarkify = cr.getBookmarkify();
		int totalPages = 0;
		Document document = new Document();
		PdfCopy copy = new PdfCopy(document, output.getOutputStream());
		document.open();
		PdfReader reader;
		ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<HashMap<String, Object>>();
		for (PDF p : inputs) {
			try {
			reader = new PdfReader(p.getInputStream());

			// Build Bookmarks
			List<HashMap<String, Object>> oldBookmarks = SimpleBookmark
					.getBookmark(reader);
			// if we want to add junction bookmarks and
			// there are multiple documents, add a bookmark for this junction
			if (bookmarkify && inputs.size() > 1) {
				bookmarks
						.add(createJunctionBookmark(totalPages, (p.getTitle())));
			}
			// if there were old bookmarks, add them into new document
			if (oldBookmarks != null) {
				SimpleBookmark.shiftPageNumbers(oldBookmarks, totalPages, null);
				bookmarks.addAll(oldBookmarks);
			}

			// Concatenate PDFs
			int numPages = reader.getNumberOfPages();
			// p.setNumPages(numPages);// probably not worth doing
			// add in each page to the new document
			for (int page = 1; page <= numPages; page++) {
				copy.addPage(copy.getImportedPage(reader, page));
			}
			// add references to named destinations with totalpage offset
			copy.addNamedDestinations(
					SimpleNamedDestination.getNamedDestination(reader, false),
					totalPages);

			copy.freeReader(reader);
			reader.close();
			totalPages += numPages;
			} catch (InvalidPdfException e){
				//TODO: add localization
				MessageLogger.getLogger().log(Level.SEVERE, p.getTitle() +" is not a valid PDF!");
			}
		}
		// set bookmarks
		copy.setOutlines(bookmarks);
		document.close();
		copy.close();
	}

	private HashMap<String, Object> createJunctionBookmark(int pageNumber,
			String title) {
		// TODO: known bug! For some reason page 0 does not work so this is one
		// off of page 0.
		if (pageNumber == 0) {
			pageNumber = 1;
		}
		HashMap<String, Object> junctionBookmark = new HashMap<String, Object>();
		junctionBookmark.put("Action", "GoTo");
		// location is x =0, y=0, z= undefined
		junctionBookmark.put("Page", pageNumber + " XYZ 0 0 null");
		junctionBookmark.put("Title", title);
		return junctionBookmark;
	}

	private void creationError(String errorKey, ConcatenateRequest cr) {
		String errorMessage = SystemConfiguration.getLocalizedString(errorKey);
		MessageLogger.getLogger().log(Level.SEVERE,
				errorMessage + ": " + cr.getOutputPDF().getTitle());
	}
}

package concatenating;

import io.FileManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pdf.PDF;
import util.StringMethods;

public class ConcatenateRequestor {

	public void makeRequest(List<String> inputPaths, String outputLocation,
			boolean bookmarkify) {
		System.out.println("makeRequest: " + inputPaths.toString());

		FileManager fm = new FileManager();

		// TODO: add check if is file or url
		List<PDF> pdfs = new ArrayList<PDF>();
		int id = 0;
		for (String inputPath : inputPaths) {
			InputStream in = fm.fetchInputStream(inputPath);
			String title = StringMethods.getJustFilename(inputPath);
			pdfs.add(new PDF(title, id, in));
			id++;
		}

		OutputStream os = fm.fetchOutputStream(outputLocation);

		boolean isValid = true;
		if (inputPaths.size() == 0 || os == null) {
			isValid = false;
		}

		if (isValid) {
			PDF output = new PDF(StringMethods.getJustFilename(outputLocation),
					id, os);
			Concatenator c = new Concatenator();
			c.concatenateAndBookmark(new ConcatenateRequest(pdfs, output,
					bookmarkify));
		} else {
			// TODO:implement error logging. perhaps switch to cleaner approach
		}

	}
}

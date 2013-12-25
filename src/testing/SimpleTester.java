package testing;

import io.FileManager;
import io.InputStreamFetcher;
import io.OutputStreamFetcher;
import io.URLManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pdf.PDF;
import util.StringMethods;
import concatenating.ConcatenateRequest;
import concatenating.Concatenator;

public class SimpleTester {
	private static final String inputDir = "/Users/srwareham/workspace/PDFMergeAndMark/inputFiles";
	private static final String outputDir = "/Users/srwareham/workspace/PDFMergeAndMark/testOutput/";
	private static final String testInputURL = "http://bitcoin.org/bitcoin.pdf";
	private static final String outputName = "simpleTest.pdf";

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		Concatenator c = new Concatenator();
		FileManager fm = new FileManager();
		InputStreamFetcher isf = fm;
		OutputStreamFetcher osf = fm;
		InputStreamFetcher urlfetch = new URLManager();

		List<PDF> inputs = new ArrayList<PDF>();
		for (String fullPath : fm.getDirContents(inputDir)) {
			if (fullPath.contains(".pdf")) {
				String name = StringMethods.getJustFilename(fullPath);
				InputStream ins = isf.fetchInputStream(fullPath);
				PDF pdf = new PDF(name, 0, ins);
				inputs.add(pdf);

			}
		}

		inputs.add(new PDF(StringMethods.getJustFilename(testInputURL), 0,
				urlfetch.fetchInputStream(testInputURL)));

		OutputStream os = osf.fetchOutputStream(outputDir + outputName);
		PDF output = new PDF(StringMethods.getJustFilename(outputName), 1, os);
		boolean bookmarkify = true;
		ConcatenateRequest cr = new ConcatenateRequest(inputs, output,
				bookmarkify);
		c.concatenateAndBookmark(cr);

	}
}

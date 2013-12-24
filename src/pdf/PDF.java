package pdf;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Class that represents a PDF. Either will have an InputStream or an
 * OutputStream representing the source file to be read or the desired output
 * destination.
 * 
 * @author srwareham
 * 
 */
public class PDF {
	private String myTitle;
	private int myID;
	// 345-399 599-699
	private String myNestIDRange;
	private InputStream myInputStream;
	private OutputStream myOutputStream;

	/**
	 * Create a PDF with a title. Will need to have an input or output stream.
	 * 
	 * @param title
	 */
	public PDF(String title, int ID) {
		myTitle = title;
		myID = ID;
	}

	/**
	 * Creates a PDF to be read.
	 * 
	 * @param title
	 * @param inStream
	 */
	public PDF(String title, int ID, InputStream inStream) {
		this(title, ID);
		myInputStream = inStream;
	}

	/**
	 * Creates a PDF to be written.
	 * 
	 * @param title
	 * @param outStream
	 */
	public PDF(String title, int ID, OutputStream outStream) {
		this(title, ID);
		myOutputStream = outStream;
	}

	public int getID() {
		return myID;
	}

	public void setID(int ID) {
		myID = ID;
	}

	public String getNestIDRange() {
		return myNestIDRange;
	}

	/**
	 * Set the range of Ids that share this sublevel. Entries are delimited by
	 * whitespace. If they have a "-" between values the lowerbound will be
	 * included and the upper bound will be excluded single values are taken
	 * alone.
	 * 
	 * @param input
	 *            ex: 400-405 200-300 2 3 4 5
	 */
	public void setNestIDRange(String input) {
		myNestIDRange = input;
	}

	/**
	 * Returns true if these two PDF's share an ID Nest range.
	 * 
	 * TODO:Clean this up significantly. Perhaps create a class for PDF
	 * precedence data.
	 * 
	 * @param other
	 * @return
	 */
	public boolean shareIDNest(PDF other) {
		String unParsed = other.getNestIDRange();
		if (unParsed == null) {
			return false;
		}
		String[] ranges = unParsed.split(" ");
		for (String s : ranges) {
			if (s.contains("-")) {
				String[] bounds = s.split("-");
				int low = Integer.parseInt(bounds[0]);
				int high = Integer.parseInt(bounds[1]);
				if (myID >= low && myID < high) {
					return true;
				}
			} else {
				if (myID == Integer.parseInt(s)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns whether or not this PDF is linked to an InputStream.
	 * 
	 * @return
	 */
	public boolean isInput() {
		return myInputStream != null;
	}

	/**
	 * Returns whether or not this PDF is linked to an OutputStream.
	 * 
	 * @return
	 */
	public boolean isOutput() {
		return myOutputStream != null;
	}

	/**
	 * Returns the name of the PDF.
	 * 
	 * @return
	 */
	public String getTitle() {
		return myTitle;
	}

	public void setTitle(String title) {
		myTitle = title;
	}

	/**
	 * Get this PDF's InputStream.
	 * 
	 * @return
	 */
	public InputStream getInputStream() {
		return myInputStream;
	}

	/**
	 * Set this PDF's InputStream.
	 * 
	 * @param in
	 */
	public void setInputStream(InputStream in) {
		myInputStream = in;
	}

	/**
	 * Get this PDF's OutPutStream.
	 * 
	 * @return
	 */
	public OutputStream getOutputStream() {
		return myOutputStream;
	}

	/**
	 * Set this PDF's OutputStream.
	 * 
	 * @param out
	 */
	public void setOutputStream(OutputStream out) {
		myOutputStream = out;
	}
}

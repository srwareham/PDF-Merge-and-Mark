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
	private InputStream myInputStream;
	private OutputStream myOutputStream;

	/**
	 * Create a PDF with a title. Will need to have an input or output stream.
	 * 
	 * @param title
	 */
	public PDF(String title) {
		myTitle = title;
	}

	/**
	 * Creates a PDF to be read.
	 * 
	 * @param title
	 * @param inStream
	 */
	public PDF(String title, InputStream inStream) {
		this(title);
		myInputStream = inStream;
	}

	/**
	 * Creates a PDF to be written.
	 * 
	 * @param title
	 * @param outStream
	 */
	public PDF(String title, OutputStream outStream) {
		this(title);
		myOutputStream = outStream;
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

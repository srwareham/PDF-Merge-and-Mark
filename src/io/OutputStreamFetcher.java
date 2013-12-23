package io;

import java.io.OutputStream;
/**
 * Interface defining behavior for a class that can obtain an OututStream.
 * @author srwareham
 *
 */
public interface OutputStreamFetcher {
	/**
	 * Returns an OutputStream based on a location. Can be extended to be a URL
	 * or local file path, etc.
	 * 
	 * @param location
	 * @return
	 */
	public OutputStream fetchOutputStream(String location);
}

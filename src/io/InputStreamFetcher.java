package io;

import java.io.InputStream;

/**
 * Interface defining behavior for a class that can obtain an InputStream.
 * 
 * @author srwareham
 * 
 */
public interface InputStreamFetcher {
	/**
	 * Returns an Input based on a location. Can be extended to be a URL or
	 * local file path, etc.
	 * 
	 * @param location
	 * @return
	 */
	public InputStream fetchInputStream(String location);
}

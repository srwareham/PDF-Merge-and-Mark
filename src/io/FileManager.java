package io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


import util.SystemConfiguration;

import logging.MessageLogger;

/**
 * Class to interact with the local filesystem.
 * 
 * @author srwareham
 * 
 */
public class FileManager implements InputStreamFetcher, OutputStreamFetcher {
	/**
	 * Returns an InputStream for a file of the given path.
	 */
	@Override
	public InputStream fetchInputStream(String inputFilePath) {
		InputStream in = null;
		try {
			in = new FileInputStream(inputFilePath);
			MessageLogger.getLogger().log(Level.INFO,
					"Loaded FileInputStream for: " + inputFilePath);

		} catch (FileNotFoundException e) {
			MessageLogger.getLogger().log(Level.SEVERE,
					"Could not find file: " + inputFilePath);
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * Returns an OutputStream for a file of the given path.
	 */
	@Override
	public OutputStream fetchOutputStream(String desiredFilePath) {
		OutputStream out = null;
		try {
			File f = new File(desiredFilePath);
			boolean succ = f.createNewFile();
			if (succ) {
				out = new BufferedOutputStream(new FileOutputStream(f));
				MessageLogger.getLogger().log(Level.INFO,
						"Wrote File: " + desiredFilePath);
			} else {
				MessageLogger.getLogger().log(Level.INFO,
						"OverWrote File: " + desiredFilePath);
				out = new BufferedOutputStream(new FileOutputStream(f));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return out;
		}
		return out;
	}

	/**
	 * Method that returns true if a string is successfully written to file.
	 * 
	 * @param input
	 *            String to write
	 * @param fullFilePath
	 *            absolute path of new file
	 * @return Success or not
	 */
	public boolean writeFileString(String input, String fullFilePath) {
		OutputStream o = fetchOutputStream(fullFilePath);
		try {
			o.write(input.getBytes());
			o.flush();
			o.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			MessageLogger.getLogger().log(Level.SEVERE,
					"Could not write to file: " + fullFilePath);
			return false;
		}
	}

	/**
	 * Returns a list of every file with its full path (including from all
	 * sub-directories)
	 * 
	 * NOTE: this includes .DS_Store's etc. The order of this list is by
	 * sublevel. The first files are from the root in OS-specific order, then
	 * the next subdir and each subsquent nested directory.
	 * 
	 * @param fullPath
	 * @return
	 */
	public List<String> getDirContents(String fullPath) {
		List<String> contents = new ArrayList<String>();
		File dir = new File(fullPath);
		if (dir.isDirectory()) {
			contents = addFiles(contents, fullPath);

		} else {
			MessageLogger.getLogger().log(Level.SEVERE,
					"Path: " + fullPath + " is not a local directory");
		}
		// System.out.println(contents.toString());
		return contents;

	}

	/**
	 * Recursive helper function for getting contents of all sub-directories.
	 * 
	 * @param files
	 * @param fullPath
	 * @return
	 */
	private List<String> addFiles(List<String> files, String fullPath) {
		;
		File dir = new File(fullPath);
		if (!dir.isDirectory()) {
			files.add(fullPath);
			return files;
		}

		for (String file : dir.list()) {
			addFiles(files, fullPath + SystemConfiguration.getFilePathDelimiter() + file);
		}
		return files;
	}
	
	
	
	//TODO:!!!!! Replace Deprecated String version (perhaps) also add docs about filefilter
	/**
	 * Returns a list of every file with its full path (including from all
	 * sub-directories)
	 * 
	 * NOTE: this includes .DS_Store's etc. The order of this list is by
	 * sublevel. The first files are from the root in OS-specific order, then
	 * the next subdir and each subsquent nested directory.
	 * 
	 * @param fullPath
	 * @return
	 */
	public List<File> getDirContents(String fullPath, FilenameFilter ff) {
		List<File> contents = new ArrayList<File>();
		File dir = new File(fullPath);
		if (dir.isDirectory()) {
			contents = adddFiles(contents, fullPath, ff);

		} 
		// System.out.println(contents.toString());
		return contents;

	}
	//for all files
	public List<File> getDirrContents(String fullPath) {
		return getDirContents(fullPath, new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return true;
			}
			
		});
	}

	/**
	 * Recursive helper function for getting contents of all sub-directories.
	 * 
	 * @param files
	 * @param fullPath
	 * @return
	 */
	private List<File> adddFiles(List<File> files, String fullPath, FilenameFilter ff) {
		;
		File dir = new File(fullPath);
		if (!dir.isDirectory()) {
			files.add(dir);//then is file
			System.out.println(dir.getAbsolutePath());
			return files;
		}

		for (String file : dir.list(ff)) {
			adddFiles(files, fullPath + SystemConfiguration.getFilePathDelimiter() + file, ff);
		}
		return files;
	}
}

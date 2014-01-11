package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import util.SystemConfiguration;

public class FileFilterByExtension extends FileFilter implements FilenameFilter {
	private List<String> myAllowableExtensions;

	public FileFilterByExtension(List<String> allowableExtensions) {
		myAllowableExtensions = allowableExtensions;
	}

	@Override
	public boolean accept(File f) {
		return (isAcceptable(f));
	}

	@Override
	public String getDescription() {
		String description = "*";
		for (String s : myAllowableExtensions) {
			description += s + " ";
		}
		return description.trim();
	}

	@Override
	public boolean accept(File dir, String name) {
		File f = new File(dir + SystemConfiguration.getFilePathDelimiter()
				+ name);
		return (isAcceptable(f));
	}

	private boolean isCorrectFileType(File f) {
		for (String s : myAllowableExtensions) {
			if (f.getName().endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAcceptable(File f) {
		// would like to show only relevant sub-directories, but too
		// resource intensive
		return ((f.isDirectory() /* && getDirContents(f, this).size() > 0 */) || isCorrectFileType(f));
	}

	// private String getAllowables(String seperator) {
	// String allowables = "";
	// for (String s : myAllowableExtensions) {
	// allowables += s + seperator;
	// }
	// return allowables.substring(0, allowables.length() - seperator.length());
	// }
}

package util;

public class StringMethods {
	/**
	 * Takes an input String and returns a capitalized version. Only the first
	 * letter of each word is capitalized. ex: "this is a Sentence" becomes
	 * "This Is A Sentence" TODO: implement this
	 * 
	 * @param input
	 * @return answer
	 */
	public static String capitalizeAllWords(String input) {
		String answer = "";
		if (input.length() < 1) {
			return answer;
		}
		answer += input.substring(0, 1).toUpperCase();

		for (int i = 1; i < input.length(); i++) {
			String previous = input.substring(i - 1, i);
			String letter = input.substring(i, i + 1);
			if (previous.equals(" ")) {
				answer += letter.toUpperCase();
			} else {
				answer += letter.toLowerCase();
			}
		}
		return answer;
	}

	/**
	 * Provided an input array of Strings, returns a String composed of each
	 * entry delimited by whitespace.
	 * 
	 * @param args
	 * @return
	 */
	public static String arrayToString(String[] args) {
		String out = "";
		for (String s : args) {
			out += s + " ";
		}
		return out.trim();
	}

	/**
	 * Input of "/Users/user/test.pdf" returns "test.pdf"
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String localPathToFileName(String fullPath) {

		String[] split = fullPath.split(SystemConfiguration.getSplitBy());
		return split[split.length - 1];
	}

	/**
	 * Input of either "/Users/user/test.pdf" or "test.pdf" returns ".pdf"
	 * 
	 * @param fileNameOrPath
	 * @return
	 */
	public static String getFileExtension(String fileNameOrPath) {
		int index = fileNameOrPath.indexOf(".");
		return fileNameOrPath.substring(index, fileNameOrPath.length());
	}

	/**
	 * Returns just a filename. No path, no extension. Works for local paths and
	 * URLs.
	 * 
	 * @param fileNameOrPath
	 * @return
	 */
	public static String getJustFilename(String fileNameOrPath) {
		int startIndex = 0;
		// then this is a full path
		if (!(fileNameOrPath.indexOf(SystemConfiguration.getSplitBy()) == -1)) {
			String[] split = fileNameOrPath.split(SystemConfiguration
					.getSplitBy());
			startIndex = fileNameOrPath.indexOf(split[split.length - 1]);
		}
		// the last found index of "." defaults to include up until last
		// character
		int stopIndex = fileNameOrPath.length();
		for (int i = 0; i < fileNameOrPath.length(); i++) {
			String sub = fileNameOrPath.substring(i, i + 1);
			if (sub.equals(".")) {
				stopIndex = i;
			}
		}
		return fileNameOrPath.substring(startIndex, stopIndex);

	}
}

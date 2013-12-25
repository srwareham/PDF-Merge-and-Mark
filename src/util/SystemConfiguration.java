package util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

public class SystemConfiguration {
	private static final String LANGUAGE_RESOURCE_PACKAGE_LOCATION = "resources.languages.";
	private static final String ENGLISH = "en";
	private static String WORKING_LANGUAGE;

	/**
	 * Returns the language to be used by this program.
	 * 
	 * @return The language code of the working language. ex: English = en
	 */
	@Test
	public static String getWorkingLanguage() {
		if (WORKING_LANGUAGE == null) {
			setWorkingLanguageToDefault();
		}
		return WORKING_LANGUAGE;
	}

	/**
	 * Set the working language of the program. Whatever string is passed should
	 * be the naming scheme of the included ResourceBundle. Is recommended to
	 * use standard language codes.
	 * 
	 * @param language
	 */
	@Test
	public static void setWorkingLanguage(String language) {
		WORKING_LANGUAGE = language;
	}

	/**
	 * Returns the ResourceBundle for the current working language. In instances
	 * of error, English is used by default.
	 * 
	 * @return
	 */
	public static ResourceBundle getLanguageBundle() {
		if (WORKING_LANGUAGE == null) {
			setWorkingLanguageToDefault();
		}
		try {
			return ResourceBundle.getBundle(LANGUAGE_RESOURCE_PACKAGE_LOCATION
					+ WORKING_LANGUAGE);
			// Default language is English
		} catch (MissingResourceException e) {
			return ResourceBundle.getBundle(LANGUAGE_RESOURCE_PACKAGE_LOCATION
					+ ENGLISH);
		}
	}

	/**
	 * Function to determine what the filename delimiter should be. Assumes all
	 * non windows systems use "/".
	 * 
	 * @return
	 */
	public static String getSplitBy() {
		String os = System.getProperty("os.name");
		os = os.toLowerCase();
		String splitBy = "/";
		if (os.contains("windows")) {
			splitBy = "\\";
		}
		return splitBy;
	}

	/**
	 * Sets the current working language to the system default (or English if
	 * the default cannot be determined).
	 */
	private static void setWorkingLanguageToDefault() {
		setWorkingLanguage(getDefaultSystemLanguage());
	}

	/**
	 * Returns the ISO 639 code of the language of the system. If the system
	 * language cannot be determined, English (en) is used by default.
	 * 
	 * @return
	 */
	private static String getDefaultSystemLanguage() {
		String defaultLang = Locale.getDefault().getLanguage();
		return ("".equals(defaultLang) ? ENGLISH : defaultLang);
	}
}

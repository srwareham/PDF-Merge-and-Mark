package testing;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import util.SystemConfiguration;

/**
 * Class ensures a well-formed English resource file.
 * 
 * @author srwareham
 * 
 */
public class EnglishResourceTester {
	@BeforeClass
	public static void initialize() {
		SystemConfiguration.setWorkingLanguage("en");
	}

	@Test
	public void testConcatenatorResources() {
		assertEquals("documentException Resource Mismatch",
				SystemConfiguration.getLocalizedString("documentException"),
				"Document Exception Creating");
		assertEquals("ioException Resource Mismatch",
				SystemConfiguration.getLocalizedString("ioException"),
				"I/O Exception Creating");

	}

	@Test
	public void testURLManagerResources() {
		assertEquals("requesting Resource Mismatch",
				SystemConfiguration.getLocalizedString("requesting"),
				"Requesting");

		assertEquals("obtainedStreamFrom Resource Mismatch",
				SystemConfiguration.getLocalizedString("obtainedStreamFrom"),
				"Obtained Stream From");
		assertEquals("errorDownloading Resource Mismatch",
				SystemConfiguration.getLocalizedString("errorDownloading"),
				"Error Downloading");
	}

}

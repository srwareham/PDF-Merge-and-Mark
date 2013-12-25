package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import util.SystemConfiguration;

/**
 * Class ensures a well-formed English resource file.
 * 
 * @author srwareham
 * 
 */
public class EnglishResourceTester {

	@Test
	public void testEnglish() {
		assertEquals(
				"documentException Resource Mismatch",
				SystemConfiguration.getLanguageBundle().getString(
						"documentException"), "Document Exception Creating");
		assertEquals(
				"ioException Resource Mismatch",
				SystemConfiguration.getLanguageBundle().getString(
						"ioException"), "I/O Exception Creating");
	}

}

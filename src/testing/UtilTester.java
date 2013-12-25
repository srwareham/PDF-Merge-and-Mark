package testing;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import util.StringMethods;
import util.SystemConfiguration;

/**
 * Test Utilities Package members.
 * 
 * @author srwareham
 * 
 */
public class UtilTester {

	/*
	 * Test SystemConfiguration
	 */

	@Test
	public void testDefaultLang() {
		assertEquals(Locale.getDefault().getLanguage(),
				SystemConfiguration.getWorkingLanguage());
	}

	@Test
	public void testGetAndSetWorkingLanguage() {
		SystemConfiguration.setWorkingLanguage(Locale.FRENCH.getLanguage());
		assertEquals("getWorkingLanguage Mismatch",
				Locale.FRENCH.getLanguage(),
				SystemConfiguration.getWorkingLanguage());

		SystemConfiguration.setWorkingLanguage(Locale.ENGLISH.getLanguage());
		assertEquals("getWorkingLanguage Mismatch",
				Locale.ENGLISH.getLanguage(),
				SystemConfiguration.getWorkingLanguage());
	}

	/*
	 * Test StringMethods
	 */
	@Test
	public void testArrayToString() {
		String[] args = { "/test/file/path", "/test/dir/path/" };
		assertEquals(StringMethods.arrayToString(args),
				"/test/file/path /test/dir/path/");
	}

	@Test
	public void testGetFileExtension() {
		assertTrue("File Extension From Full Path Mismatch",
				".pdf".equals(StringMethods
						.getFileExtension("/Users/user/dir/name.pdf")));

		assertTrue("File Extension From Name Mismatch",
				".pdf".equals(StringMethods.getFileExtension("test.pdf")));
	}

	@Test
	public void testLocalPathToFilename() {
		assertTrue("Local Path To Filename Mismatch",
				"test.pdf".equals(StringMethods
						.localPathToFileName("/Users/user/test.pdf")));
	}

	@Test
	public void testJustFileName() {
		assertTrue("Just Filename from Path Mismatch",
				"test".equals(StringMethods
						.getJustFilename("/Users/user/test.pdf")));

		assertTrue("Just Filename from \"Filename.extension\" Mismatch",
				"test".equals(StringMethods.getJustFilename("test.pdf")));
	}

	@Test
	public void testCapitalizeAllWords() {
		assertTrue("Capitalize All Words Mismatch",
				"This Is The End".equals(StringMethods
						.capitalizeAllWords("this is The end")));
	}
}

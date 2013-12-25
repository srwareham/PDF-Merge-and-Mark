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
		assertEquals("File Extension From Full Path Mismatch", ".pdf",
				StringMethods.getFileExtension("/Users/user/dir/name.pdf"));

		assertEquals("File Extension From Name Mismatch", ".pdf",
				StringMethods.getFileExtension("test.pdf"));
	}

	@Test
	public void testLocalPathToFilename() {
		assertEquals("Local Path To Filename Mismatch", "test.pdf",
				StringMethods.localPathToFileName("/Users/user/test.pdf"));
	}

	@Test
	public void testJustFileName() {
		assertEquals("Just Filename from Path Mismatch", "test",
				StringMethods.getJustFilename("/Users/user/test.pdf"));

		assertEquals("Just Filename from \"Filename.extension\" Mismatch",
				"test", StringMethods.getJustFilename("test.pdf"));
	}

	@Test
	public void testCapitalizeAllWords() {
		assertEquals("Capitalize All Words Mismatch", "This Is The End",
				(StringMethods.capitalizeAllWords("this is The end")));
	}
}

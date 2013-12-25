package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UtilTester.class, EnglishResourceTester.class })
/**
 * Class to run all tests at once.
 * @author srwareham
 *
 */
public class MasterTester {
	// Add comma separated classes in SuiteClasses to run multiple tests at
	// once.
}

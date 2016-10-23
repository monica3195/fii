package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Madalina Pastrav
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ PrintRootNameTest.class, ReturnFolderTest.class,
		ReturnIdConceptTest.class, TestingClass.class })
public class AllTests {

}

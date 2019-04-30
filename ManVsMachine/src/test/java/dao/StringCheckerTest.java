
package dao;

import mvsm.dao.StringChecker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class StringCheckerTest {
    
    private StringChecker checker;

    public StringCheckerTest() {
        this.checker = new StringChecker();
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void checkUsernameRightReturnValues() {
        int ret = checker.checkUserNameUpdate("test", "test");
        ret += checker.checkUserNameUpdate("test", "te");
        ret += checker.checkUserNameUpdate("test", "testtesttesttesttesttetstets");
        ret += checker.checkUserNameUpdate("test", "tes t");
        ret += checker.checkUserNameUpdate("test", "Teppo");
        ret += checker.checkUserNameUpdate("test", " r");
        ret += checker.checkUserNameUpdate("test", "srrrrrrrrrrrrrrrrrrrrrr rrrrrrrrrrrrr");
        ret += checker.checkUserNameUpdate("test", "Tulppu");
        assertTrue(ret == 33);
    }
    
    @Test
    public void checkPasswordRigthReturnValues() {
        int ret = checker.checkPassword("Testpassword1", "Testpassword1");
        ret += checker.checkPassword("TestP455word1", "TestP455word1");
        ret += checker.checkPassword("Testpassword1", "Testpasswold1");
        ret += checker.checkPassword("Tespassword1", "Testpassword1");
        ret += checker.checkPassword("testpassword1", "testpassword1");
        ret += checker.checkPassword("Testpassword", "Testpassword");
        ret += checker.checkPassword("TESTPASSWORD1", "TESTPASSWORD1");
        ret += checker.checkPassword("Test1", "Test1");
        ret += checker.checkPassword("TestpasswordTesting1", "TestpasswordTesting1");
        ret += checker.checkPassword("Testpassword#1", "Testpassword#1");
        ret += checker.checkPassword("P assword#1", "P assword#1");
        ret += checker.checkPassword("P assword1", "P assword1");
        ret += checker.checkPassword("P_assword#1", "P_assword#1");
        ret += checker.checkPassword("12345678", "12345678");
        assertTrue(ret == 48);
    }
}

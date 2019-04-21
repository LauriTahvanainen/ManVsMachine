package dao;

import java.io.File;
import java.sql.SQLException;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DatabaseUserDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    DatabaseUserDao dao;
    File database;

    public DatabaseUserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        database = testFolder.newFile("testDatabase.db");
        dao = new DatabaseUserDao(new Connector(database.getAbsolutePath()));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void rightUserIsAdded() throws SQLException {
        this.dao.create("Teppo");
        User teppo = this.dao.read("Teppo");
        if (teppo == null) {
            fail("Dao returns null when it should return an user");
        }
        if (teppo.getUsername().equals("Teppo") && teppo.getColor().equals(Color.rgb(255, 0, 0))) {
            return;
        }
        fail("The right user was not added!");
    }

    @Test
    public void addingANewUserReturns1() throws SQLException {
        int ret = this.dao.create("Teppo");
        User teppo = this.dao.read("Teppo");
        if (ret == 1) {
            return;
        }
        fail("The return value should be 1, but it was: " + ret);
    }

    @Test
    public void defaultUserColorIsRed() throws SQLException {
        this.dao.create("Teppo");
        User teppo = this.dao.read("Teppo");
        if (teppo.getColor().equals(Color.rgb(255, 0, 0))) {
            return;
        }
        fail("The user has a wrong color. Should have been 255:0:0 but was: " + (int) teppo.getColor().getRed() * 255 + ":" + (int) teppo.getColor().getGreen() * 255 + ":" + (int) teppo.getColor().getBlue() * 255 + ":");
    }

    @Test
    public void ifUserDoesntExistNullReturned() throws SQLException {
        this.dao.create("Seppo");
        this.dao.create("Teppo");
        this.dao.create("Matti");
        this.dao.create("Ilmari");
        User ret = this.dao.read("Antti");
        if (ret == null) {
            return;
        }
        fail("The dao should return a null object!");
    }

    @Test
    public void usernameTooLongReturns16() throws SQLException {
        int ret = this.dao.create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa");
        ret += this.dao.create("sdfghkfkkv");
        ret += this.dao.create("AAAAAAAAAAAAAAAAAAAA");
        ret += this.dao.create("ttttttttttttttttt");
        ret += this.dao.create("ttttttttttttttttt");
        if (ret == 65) {
            return;
        }
        fail("The return value should be 65, now it was " + ret);
    }

    @Test
    public void usernameTooShortReturns4() throws SQLException {
        int ret = this.dao.create("aaa");
        ret += this.dao.create("a");
        ret += this.dao.create("aa");
        ret += this.dao.create("");
        ret += this.dao.create("aaaaaa");
        if (ret == 17) {
            return;
        }
        fail("The return value should be 17, now it was " + ret);
    }

    @Test
    public void usernameContainsSpaceReturns3EvenIfTooShortOrLong() throws SQLException {
        int ret = this.dao.create("AAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAaaa");
        ret += this.dao.create("A ");
        ret += this.dao.create("A asdsdds");
        ret += this.dao.create("As s df f");
        if (ret == 12) {
            return;
        }
        fail("The return value should have been 3, now it was " + ret);
    }

    @Test
    public void addingADuplicateReturns0() throws SQLException {
        int ret = this.dao.create("Teppo");
        ret += this.dao.create("Teppo");
        ret += this.dao.create("teppo");
        ret += this.dao.create("teppo");
        if (ret == 2) {
            return;
        }
        fail("ret should be 2, but is now: " + ret);
    }

    @Test
    public void usernameUpdatedTest1() throws SQLException {
        this.dao.create("TeppoTest");
        this.dao.update("TeppoTest", "Teppo");
        User ret = this.dao.read("TeppoTest");
        if (ret == null) {
            return;
        }
        fail("Username not updated correctly!");
    }
    
    @Test
    public void usernameUpdatedTest2() throws SQLException {
        this.dao.create("TeppoTest");
        this.dao.update("TeppoTest", "Teppo");
        User ret = this.dao.read("Teppo");
        if (ret != null) {
            return;
        }
        fail("Username not updated correctly!");
    }

    @Test
    public void updatingToOldUsernameReturns2() throws SQLException {
        this.dao.create("Teppo");
        this.dao.create("Test");
        int ret = this.dao.update("Teppo", "Teppo");
        ret += this.dao.update("Test", "Test");
        if (ret == 4) {
            return;
        }
        fail("ret should be 4, but was: " + ret);
    }

    @Test
    public void updatingToExistingUsernameDoesNotWork() throws SQLException {
        this.dao.create("Teppo");
        this.dao.create("Test");
        this.dao.update("Teppo", "Test");
        User ret = this.dao.read("Teppo");
        if (ret != null) {
            return;
        }
        fail("It should be impossible to update to an existing username but now the username was updated!");
    }

    @Test
    public void updateColorTest() throws SQLException {
        this.dao.create("Teppo");
        this.dao.updateColor("Teppo", 244, 34, 1);
        Color ret1 = this.dao.read("Teppo").getColor();;
        this.dao.updateColor("Teppo", 0, 0, 0);
        Color ret2 = this.dao.read("Teppo").getColor();
        if ((int) (ret1.getRed() * 255) == 244 && (int) (ret1.getGreen() * 255) == 34 && (int) (ret1.getBlue() * 255) == 1 && ret2.equals(Color.BLACK)) {
            return;
        }
        fail("UpdateColor does not work as intended!");
    }

    @Test
    public void usernameUpdateUsernameCheckReturnsRightValues() throws SQLException {
        this.dao.create("Teppo");
        int ret = this.dao.update("Teppo", "Tep ppo");
        ret += this.dao.update("Teppo", "Teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeppo");
        ret += this.dao.update("Teppo", "Tep");
        ret += this.dao.update("Teppo", "Teppo");
        ret += this.dao.update("Teppo", "Tintti");
        if (ret == 2 + 3 + 4 + 16 + 1) {
            return;
        }
        fail("Given return values are not correct, ret should have been 26, but was: " + ret);
    }

    @Test
    public void updateColorDoesNotUpdateWrongColors() throws SQLException {
        this.dao.create("Teppo");
        this.dao.updateColor("Teppo", 0, 5, 2555);
        User ret = this.dao.read("Teppo");
        assertTrue(ret.getColor().equals(Color.RED));
    }

    @Test
    public void checkColorsTest() throws SQLException {
        this.dao.create("Teppo");
        this.dao.updateColor("Teppo", 0, 256, 0);
        this.dao.updateColor("Teppo", 0, -2, 0);
        this.dao.updateColor("Teppo", -2, 0, 0);
        this.dao.updateColor("Teppo", 2677, 25, 0);
        this.dao.updateColor("Teppo", 0, 2, -46);
        this.dao.updateColor("Teppo", 0, 25, 7777);
        User ret = this.dao.read("Teppo");
        assertTrue(ret.getColor().equals(Color.RED));
    }
}

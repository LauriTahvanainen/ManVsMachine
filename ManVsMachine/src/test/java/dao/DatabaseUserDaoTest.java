package dao;

import java.io.File;
import java.io.IOException;
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
        dao = new DatabaseUserDao(database.getAbsolutePath());
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
    public void usernameUpdated() throws SQLException {
        this.dao.create("TeppoTest");
        this.dao.update("TeppoTest", "Teppo");
        User ret = this.dao.read("TeppoTest");
        if (ret == null) {
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
        fail("It should be impossible to update to an existing username but now it was possible!");
    }

}

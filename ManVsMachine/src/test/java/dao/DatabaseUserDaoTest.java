package dao;

import mvsm.dao.HighScoreUser;
import mvsm.dao.Connector;
import mvsm.dao.DatabaseUserDao;
import mvsm.dao.ScoreDao;
import mvsm.dao.DatabaseScoreDao;
import mvsm.dao.User;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DatabaseUserDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    DatabaseUserDao dao;
    ScoreDao scoreDao;
    File database;
    
    @Before
    public void setUp() throws Exception {
        database = testFolder.newFile("testDatabase.db");
        Connector conn = new Connector(database.getAbsolutePath());
        dao = new DatabaseUserDao(conn);
        dao.initDatabase();
        scoreDao = new DatabaseScoreDao(conn);
    }

    @Test
    public void connectorDoesNotReturnNull() {
        Connector conn = new Connector(database.getAbsolutePath());
        Connection ret = conn.openConnection();
        assertTrue(ret != null);
    }

    @Test
    public void rightUserIsAdded() throws SQLException {
        this.dao.create("Teppo", "-");
        User teppo = this.dao.read("Teppo");
        if (teppo == null) {
            fail("Dao returns null when it should return an user");
        }
        if (teppo.getUsername().equals("Teppo") && teppo.getPortalColor().equals(Color.rgb(255, 0, 0))) {
            return;
        }
        fail("The right user was not added!");
    }

    @Test
    public void addingANewUserReturns1() throws SQLException {
        int ret = this.dao.create("Teppo", "-");
        User teppo = this.dao.read("Teppo");
        if (ret == 1) {
            return;
        }
        fail("The return value should be 1, but it was: " + ret);
    }

    @Test
    public void defaultUserColorIsRed() throws SQLException {
        this.dao.create("Teppo", "-");
        User teppo = this.dao.read("Teppo");
        if (teppo.getPortalColor().equals(Color.rgb(255, 0, 0))) {
            return;
        }
        fail("The user has a wrong color. Should have been 255:0:0 but was: " + (int) teppo.getPortalColor().getRed() * 255 + ":" + (int) teppo.getPortalColor().getGreen() * 255 + ":" + (int) teppo.getPortalColor().getBlue() * 255 + ":");
    }

    @Test
    public void ifUserDoesntExistNullReturned() throws SQLException {
        this.dao.create("Seppo", "_");
        this.dao.create("Teppo", "-");
        this.dao.create("Matti", "-");
        this.dao.create("Ilmari", "-");
        User ret = this.dao.read("Antti");
        if (ret == null) {
            return;
        }
        fail("The dao should return a null object!");
    }

    @Test
    public void usernameTooLongReturns16() throws SQLException {
        int ret = this.dao.create("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaa", "-");
        ret += this.dao.create("sdfghkfkkv", "-");
        ret += this.dao.create("AAAAAAAAAAAAAAAAAAAA", "-");
        ret += this.dao.create("ttttttttttttttttt", "-");
        ret += this.dao.create("ttttttttttttttttt", "-");
        if (ret == 65) {
            return;
        }
        fail("The return value should be 65, now it was " + ret);
    }

    @Test
    public void usernameTooShortReturns4() throws SQLException {
        int ret = this.dao.create("aaa", "-");
        ret += this.dao.create("a", "-");
        ret += this.dao.create("aa", "-");
        ret += this.dao.create("", "-");
        ret += this.dao.create("aaaaaa", "-");
        if (ret == 17) {
            return;
        }
        fail("The return value should be 17, now it was " + ret);
    }

    @Test
    public void usernameContainsSpaceReturns3EvenIfTooShortOrLong() throws SQLException {
        int ret = this.dao.create("AAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAaaa", "-");
        ret += this.dao.create("A ", "-");
        ret += this.dao.create("A asdsdds", "-");
        ret += this.dao.create("As s df f", "-");
        if (ret == 12) {
            return;
        }
        fail("The return value should have been 3, now it was " + ret);
    }

    @Test
    public void addingADuplicateReturns0() throws SQLException {
        int ret = this.dao.create("Teppo", "-");
        ret += this.dao.create("Teppo", "-");
        ret += this.dao.create("teppo", "-");
        ret += this.dao.create("teppo", "-");
        if (ret == 2) {
            return;
        }
        fail("ret should be 2, but is now: " + ret);
    }

    @Test
    public void usernameUpdatedTest1() throws SQLException {
        this.dao.create("TeppoTest", "-");
        this.dao.update("TeppoTest", "Teppo");
        User ret = this.dao.read("TeppoTest");
        if (ret == null) {
            return;
        }
        fail("Username not updated correctly!");
    }

    @Test
    public void usernameUpdatedTest2() throws SQLException {
        this.dao.create("TeppoTest", "-");
        this.dao.update("TeppoTest", "Teppo");
        User ret = this.dao.read("Teppo");
        if (ret != null) {
            return;
        }
        fail("Username not updated correctly!");
    }

    @Test
    public void updatingToOldUsernameReturns2() throws SQLException {
        this.dao.create("Teppo", "-");
        this.dao.create("Test", "-");
        int ret = this.dao.update("Teppo", "Teppo");
        ret += this.dao.update("Test", "Test");
        if (ret == 4) {
            return;
        }
        fail("ret should be 4, but was: " + ret);
    }

    @Test
    public void updatingToExistingUsernameDoesNotWork() throws SQLException {
        this.dao.create("Teppo", "-");
        this.dao.create("Test", "-");
        this.dao.update("Teppo", "Test");
        User ret = this.dao.read("Teppo");
        if (ret != null) {
            return;
        }
        fail("It should be impossible to update to an existing username but now the username was updated!");
    }

    @Test
    public void updateColorTest() throws SQLException {
        this.dao.create("Teppo", "-");
        this.dao.updateColor("Teppo", 244, 34, 1);
        Color ret1 = this.dao.read("Teppo").getPortalColor();;
        this.dao.updateColor("Teppo", 0, 0, 0);
        Color ret2 = this.dao.read("Teppo").getPortalColor();
        if ((int) (ret1.getRed() * 255) == 244 && (int) (ret1.getGreen() * 255) == 34 && (int) (ret1.getBlue() * 255) == 1 && ret2.equals(Color.BLACK)) {
            return;
        }
        fail("UpdateColor does not work as intended!");
    }

    @Test
    public void usernameUpdateUsernameCheckReturnsRightValues() throws SQLException {
        this.dao.create("Teppo", "-");
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
        this.dao.create("Teppo", "-");
        this.dao.updateColor("Teppo", 0, 5, 2555);
        User ret = this.dao.read("Teppo");
        assertTrue(ret.getPortalColor().equals(Color.RED));
    }

    @Test
    public void checkColorsTest() throws SQLException {
        this.dao.create("Teppo", "-");
        this.dao.updateColor("Teppo", 0, 256, 0);
        this.dao.updateColor("Teppo", 0, -2, 0);
        this.dao.updateColor("Teppo", -2, 0, 0);
        this.dao.updateColor("Teppo", 2677, 25, 0);
        this.dao.updateColor("Teppo", 0, 2, -46);
        this.dao.updateColor("Teppo", 0, 25, 7777);
        User ret = this.dao.read("Teppo");
        assertTrue(ret.getPortalColor().equals(Color.RED));
    }

    @Test
    public void updateUserNameAlsoUpdatesScoreTables() throws SQLException {
        this.dao.create("Teppo", "-");
        this.dao.create("Testi", "-");
        this.scoreDao.createDefault("Teppo", "BFS");
        this.scoreDao.createDefault("Testi", "BFS");
        this.scoreDao.updateScore("BFS", "Teppo", "map1", 1000);
        this.scoreDao.updateScore("BFS", "Testi", "map1", 2000);
        this.dao.update("Teppo", "Seppo");
        ArrayList<HighScoreUser> list = this.scoreDao.listAllSorted("BFS", "map1");
        HighScoreUser user = this.scoreDao.listUser("BFS", "Teppo");
        if (list.size() == 2 && user == null && list.get(1).getName().equals("Seppo") && list.get(1).getScore("map1") == 1000) {
            return;
        }
        fail("Updating an username does not update scoretables correctly");
    }

    @Test
    public void passwordUpdates() throws SQLException {
        this.dao.create("Test", "TestPassword2");
        this.dao.create("Test2", "Password123");
        String u1 = "Updated123";
        String u2 = "321drowssaP";
        this.dao.updatePassword("Test", u1);
        this.dao.updatePassword("Test2", u2);
        if (this.dao.read("Test").getPassword() == u1.hashCode() && this.dao.read("Test2").getPassword() == u2.hashCode()) {
            return;
        }
        fail("Passwords are not updated properly.");
    }

    @Test
    public void passwordAndUsernameUpdateWorkTogether() throws SQLException {
        this.dao.create("Test", "TestPassword2");
        this.dao.create("Test2", "Password123");
        String u1 = "Updated123";
        String u2 = "321drowssaP";
        this.dao.update("Test", "TestTest");
        this.dao.updatePassword("TestTest", u1);
        this.dao.update("TestTest", "Test");
        this.dao.updatePassword("Test2", u2);
        this.dao.update("Test2", "TestTest");
        if (this.dao.read("TestTest").getPassword() == u2.hashCode() && this.dao.read("Test").getPassword() == u1.hashCode() && this.dao.read("Test2") == null) {
            return;
        }
        fail("Updating both the password and username does not work properly.");
    }
}

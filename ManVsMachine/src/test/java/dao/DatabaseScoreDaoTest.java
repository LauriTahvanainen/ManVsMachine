/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import mvsm.dao.HighScoreUser;
import mvsm.dao.DatabaseUserDao;
import mvsm.dao.Connector;
import mvsm.dao.ScoreDao;
import mvsm.dao.DatabaseScoreDao;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author coronatu5
 */
public class DatabaseScoreDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    ScoreDao scoreDao;
    DatabaseUserDao userDao;
    File database;

    public DatabaseScoreDaoTest() {

    }

    @Before
    public void setUp() throws IOException, Exception {
        database = testFolder.newFile("testDatabase.db");
        Connector conn = new Connector(database.getAbsolutePath());
        userDao = new DatabaseUserDao(conn);
        userDao.initDatabase();
        scoreDao = new DatabaseScoreDao(conn);
        this.scoreDao.createDefault("Test1", "BFS");
        this.scoreDao.createDefault("Test2", "BFS");
        this.scoreDao.createDefault("Test3", "BFS");
        this.scoreDao.createDefault("Test4", "BFS");
        this.scoreDao.updateScore("BFS", "Test1", "map1", 1000);
        this.scoreDao.updateScore("BFS", "Test1", "map2", 1500);
        this.scoreDao.updateScore("BFS", "Test1", "map3", 5000);
        this.scoreDao.updateScore("BFS", "Test2", "map1", 1400);
        this.scoreDao.updateScore("BFS", "Test2", "map2", 2000);
        this.scoreDao.updateScore("BFS", "Test2", "map3", 3000);
    }

    @Test
    public void createDefaultTest() throws SQLException {
        int ret = this.scoreDao.createDefault("Testi", "BFS");
        HighScoreUser user = this.scoreDao.listUser("BFS", "Testi");
        if (ret == 1 && user.getScore("map1") == 0 && user.getScore("map2") == 0 && user.getScore("map3") == 0) {
            return;
        }
        fail("Default values were not set right. Excepted that the values would be 0,0,0, but was: " + user.getScore("map1") + "," + user.getScore("map2") + "," + user.getScore("map3"));
    }

    @Test
    public void createDefaultReturns0IfExceptionCaught() throws SQLException {
        int ret = this.scoreDao.createDefault("Test", "sfffff");
        assertTrue(ret == 0);
    }

    @Test
    public void listUserTest() throws SQLException {
        HighScoreUser user = this.scoreDao.listUser("BFS", "Test1");
        HighScoreUser user2 = this.scoreDao.listUser("BFS", "Test2");

        if (user.getScore("map1") == 1000 && user.getScore("map2") == 1500 && user.getScore("map3") == 5000 && user2.getScore("map1") == 1400 && user2.getScore("map2") == 2000 && user2.getScore("map3") == 3000) {
            return;
        }
        fail("Scores not read right");
    }

    @Test
    public void readTest() throws SQLException {
        int score = this.scoreDao.read("BFS", "Test1", "map1");
        score += this.scoreDao.read("BFS", "Test2", "map2");
        if (score == 3000) {
            return;
        }
        fail("Score was supposed to be 3000, but was: " + score);
    }

    @Test
    public void readInvalidParametersTest() throws SQLException {
        int score = this.scoreDao.read("BFS", "Test11", "map1");
        score += this.scoreDao.read("BFS", "Test2", "map111");
        if (score == -2) {
            return;
        }
        fail("Score was supposed to be -2, but was: " + score);
    }

    @Test
    public void listAllSortedTest() throws SQLException {
        this.scoreDao.updateScore("BFS", "Test3", "map1", 50);
        this.scoreDao.updateScore("BFS", "Test4", "map1", 150);
        ArrayList<HighScoreUser> list = this.scoreDao.listAllSorted("BFS", "map1");
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getScore("map1") > list.get(i - 1).getScore("map1")) {
                fail("Scores are not sorted in the right order. " + list.get(i).getScore("map1") + " should be before " + list.get(i - 1).getScore("map1"));
            }
        }
    }

    @Test
    public void listAllSortedSizeTest() throws SQLException {
        this.scoreDao.updateScore("BFS", "Test3", "map1", 50);
        this.scoreDao.updateScore("BFS", "Test4", "map1", 150);
        ArrayList<HighScoreUser> list = this.scoreDao.listAllSorted("BFS", "map1");
        if (list.size() == 4) {
            return;
        }
        fail("List size should be 4, but was " + list.size());
    }

    @Test
    public void updateScoreReturnFalseWhenExceptionCaughtTest() throws SQLException {
        assertFalse(this.scoreDao.updateScore("bsf", "te", "map1", 50000000));
    }

    @Test
    public void updateScoreTest() throws SQLException {
        for (int i = 1; i < 5; i++) {
            this.scoreDao.updateScore("BFS", "Test" + i, "map1", i * 1000);
        }
        int val = 0;
        for (int i = 1; i < 5; i++) {
            val += this.scoreDao.read("BFS", "Test" + i, "map1");
        }
        if (val == 10000) {
            return;
        }
        fail("Update does not update properly. Value should be 10000, but was " + val);
    }
}

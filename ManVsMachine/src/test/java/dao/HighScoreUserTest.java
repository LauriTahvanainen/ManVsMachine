package dao;

import mvsm.dao.HighScoreUser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HighScoreUserTest {

    private HighScoreUser user;

    @Before
    public void setUp() {
        this.user = new HighScoreUser("Test");
    }

    @Test
    public void addScoreTest() {
        this.user.addScore("map1", 500);
        this.user.addScore("map2", 500);
        this.user.addScore("map3", 900);
        this.user.addScore("map4", 800);
        this.user.addScore("map5", 700);
        int ret = 0;
        for (int i = 1; i < 6; i++) {
            ret += this.user.getScore("map" + i);
        }
        if (ret == 3400) {
            return;
        }
        fail("Adding scores does not work!");
    }
    
    @Test
    public void updateScoreTest() {
        this.user.addScore("map1", 500);
        this.user.addScore("map2", 500);
        this.user.addScore("map3", 900);
        this.user.addScore("map4", 800);
        this.user.addScore("map5", 700);
        this.user.updateScore("map1", 1000);
        this.user.updateScore("map2", 1000);
        this.user.updateScore("map3", 1000);
        this.user.updateScore("map4", 1000);
        this.user.updateScore("map5", 1000);
        int ret = 0;
        for (int i = 1; i < 6; i++) {
            ret += this.user.getScore("map" + i);
        }
        if (ret == 5000) {
            return;
        }
        fail("Adding scores does not work!");
    }
    
    @Test
    public void updateScoreWillInsertIfMapDoesNotExistTest() {
        this.user.updateScore("map1", 500);
        this.user.updateScore("map2", 500);
        this.user.updateScore("map3", 900);
        this.user.updateScore("map4", 800);
        this.user.updateScore("map5", 700);
        int ret = 0;
        for (int i = 1; i < 6; i++) {
            ret += this.user.getScore("map" + i);
        }
        if (ret == 3400) {
            return;
        }
        fail("Adding scores does not work!");
    }
}

package algorithm;

import java.util.ArrayDeque;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sprite.Machine;

public class BFSTest {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    //coordniates of the row have a -1 offset in the route that the algorithm gives
    private int lastX = this.map[0].length - 3;
    private int lastY = this.map.length - 3;
    private Machine machine;
    private BFS search;

    public BFSTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.search = new BFS(this.map);
        this.machine = new Machine(Color.BLACK, 20, 20, this.search);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getRouteNotNullTest() {
        ArrayDeque<Vertex> ret = this.search.getRoute();
        assertTrue(ret != null);
    }

    @Test
    public void routeNotEmpty() {
        assertTrue(!this.search.getRoute().isEmpty());
    }

    @Test
    public void lastOnRouteIsGoal() {
        ArrayDeque<Vertex> ret = this.search.getRoute();
        Vertex v = ret.pollLast();
        if (v.getColumn() == this.lastX * 40 && v.getRow() == this.lastY * 40) {
            return;
        }
        fail("The end to the route does not point to the goal: " + this.lastX * 40 + ":" + this.lastY * 40 + ",but: " + v.getColumn() + ":" + v.getRow());
    }

    @Test
    public void routeFoundToGoal() {
        while (!this.search.getRoute().isEmpty()) {
            this.machine.takeStep();
        }
        if (this.machine.getForm().getTranslateX() == this.lastX * 40 && this.machine.getForm().getTranslateY() == this.lastY * 40) {
            return;
        }
        fail("Your machine doesn't end up in its goal: (" + (this.lastX * 40) + ":" + (this.lastY * 40) + ") It ends at: (" + this.machine.getForm().getTranslateX() + ":" + this.machine.getForm().getTranslateY());
    }
    
    @Test
    public void scanFoundToGoal() {
        while (!this.search.getRoute().isEmpty()) {
            this.machine.scanNext();
        }
        if (this.machine.getForm().getTranslateX() == this.lastX * 40 && this.machine.getForm().getTranslateY() == this.lastY * 40) {
            return;
        }
        fail("Your machine doesn't end up in its goal: (" + (this.lastX * 40) + ":" + (this.lastY * 40) + ") It ends at: (" + this.machine.getForm().getTranslateX() + ":" + this.machine.getForm().getTranslateY());
    }

    @Test
    public void scanLastOnScanIsGoal() {
        ArrayDeque<Vertex> ret = this.search.getMapScan();
        Vertex v = ret.pollLast();
        if (v.getColumn() == this.lastX * 40 && v.getRow() == this.lastY * 40) {
            return;
        }
        fail("The end to the scan does not point to the goal: " + this.lastX * 40 + ":" + this.lastY * 40 + ",but: " + v.getColumn() + ":" + v.getRow());
    }

}

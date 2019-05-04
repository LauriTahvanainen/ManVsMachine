package algorithm;

import mvsm.algorithm.BFS;
import mvsm.algorithm.Vertex;
import java.util.ArrayDeque;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BFSTest {

    private final int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2, 1},
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
        {1, 3, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 5, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    //coordniates of the row have a -1 offset in the route that the algorithm gives
    private final int lastX = this.map[0].length - 3;
    private final int lastY = this.map.length - 3;
    private BFS search;

    @Before
    public void setUp() {
        this.search = new BFS();
        this.search.setUpAlgorithm(map, 1, 1);
        this.search.calculateRoute(16, 28);
    }

    @Test
    public void getRouteNotNullTest() {
        ArrayDeque<Vertex> ret = this.search.getRoute();
        assertTrue(ret != null);
    }

    @Test
    public void getScanRouteNotNullTest() {
        ArrayDeque<Vertex> ret = this.search.getMapScan();
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
    public void scanLastOnScanIsGoal() {
        ArrayDeque<Vertex> ret = this.search.getMapScan();
        Vertex v = ret.pollLast();
        if (v.getColumn() == this.lastX * 40 && v.getRow() == this.lastY * 40) {
            return;
        }
        fail("The end to the scan does not point to the goal: " + this.lastX * 40 + ":" + this.lastY * 40 + ",but: " + v.getColumn() + ":" + v.getRow());
    }

    @Test
    public void firstOnRouteNotStartingPosition() {
        assertFalse(this.search.getRoute().contains(new Vertex(40, 40)));
    }

    @Test
    public void routeLengthIsRight() {
        if (this.search.getRoute().size() == 46) {
            return;
        }
        fail("Route length should be 46, but is: " + this.search.getRoute().size());
    }

    @Test
    public void noWallsOnRoute() {
        while (!this.search.getRoute().isEmpty()) {
            Vertex v = this.search.getRoute().pop();
            if (map[v.getRow() / 40 + 1][v.getColumn() / 40 + 1] == 1) {
                fail("There is a wall on the route at coordinates: " + v.getRow() + ":" + v.getColumn());
            }
        }
    }

    @Test
    public void noWallsOnScanRoute() {
        while (!this.search.getMapScan().isEmpty()) {
            Vertex v = this.search.getMapScan().pop();
            if (map[v.getRow() / 40 + 1][v.getColumn() / 40 + 1] == 1) {
                fail("There is a wall on the route at coordinates: " + v.getRow() + ":" + v.getColumn());
            }
        }
    }

}

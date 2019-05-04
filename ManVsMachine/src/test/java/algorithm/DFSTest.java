package algorithm;

import java.util.ArrayDeque;
import mvsm.algorithm.DFS;
import mvsm.algorithm.Vertex;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class DFSTest {

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
    private DFS search;

    @Before
    public void setUp() {
        this.search = new DFS();
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
    public void routeLengthIsRandom() {
        DFS search2 = new DFS();
        search2.setUpAlgorithm(map, 1, 1);
        search2.calculateRoute(16, 28);
        DFS search3 = new DFS();
        search3.setUpAlgorithm(map, 1, 1);
        search3.calculateRoute(16, 28);
        DFS search4 = new DFS();
        search4.setUpAlgorithm(map, 1, 1);
        search4.calculateRoute(16, 28);
        DFS search5 = new DFS();
        search5.setUpAlgorithm(map, 1, 1);
        search5.calculateRoute(16, 28);
        DFS search6 = new DFS();
        search6.setUpAlgorithm(map, 1, 1);
        search6.calculateRoute(16, 28);
        DFS search7 = new DFS();
        search7.setUpAlgorithm(map, 1, 1);
        search7.calculateRoute(16, 28);
        if (this.search.getRoute().size() != search2.getRoute().size()
                || this.search.getRoute().size() != search3.getRoute().size()
                || this.search.getRoute().size() != search4.getRoute().size()
                || this.search.getRoute().size() != search5.getRoute().size()
                || this.search.getRoute().size() != search6.getRoute().size()
                || this.search.getRoute().size() != search7.getRoute().size()) {
            return;
        }
        fail("Route length should be random, but now all the routes are the same size");
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

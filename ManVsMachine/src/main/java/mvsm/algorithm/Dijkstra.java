package mvsm.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Class that implements the Algorithm interface with Dijkstras algorithm. Can
 * find the shortest path on any map.
 *
 * @see mvsm.algorithm.Algorithm
 */
public class Dijkstra extends Algorithm {

    private ArrayList<Edge>[][] neighbours;
    private ArrayDeque<Vertex> route;
    private ArrayDeque<Vertex> scanRoute;
    private Vertex[][] routeMap;
    private boolean[][] visited;
    private int[][] distance;
    private PriorityQueue<DistanceNode> heap;
    private int startX;
    private int startY;

    /**
     * Calculate the shortest route to the goal with the use of Dijkstras
     * algorithm.
     *
     * @param goalX X-coordinate To calculate the route to.
     * @param goalY Y-coordinate to calculate the route to.
     */
    @Override
    public void calculateRoute(int goalX, int goalY) {
        while (!heap.isEmpty()) {
            DistanceNode next = heap.poll();
            Vertex nextVer = next.getVertex();
            scanRoute.add(nextVer.scaleOffset(40, 0, 0));
            if (!visited[nextVer.getRow()][nextVer.getColumn()]) {
                this.visited[nextVer.getRow()][nextVer.getColumn()] = true;
                for (Edge edge : neighbours[nextVer.getRow()][nextVer.getColumn()]) {
                    Vertex end = edge.getEnd();
                    if (distance[end.getRow()][end.getColumn()] > distance[nextVer.getRow()][nextVer.getColumn()] + edge.getWeight()) {
                        distance[end.getRow()][end.getColumn()] = distance[nextVer.getRow()][nextVer.getColumn()] + edge.getWeight();
                        routeMap[end.getRow()][end.getColumn()] = nextVer;
                        heap.add(new DistanceNode(end, distance[end.getRow()][end.getColumn()]));
                    }
                }
            }
        }
        buildRoute(goalX, goalY);
    }

    @Override
    public ArrayDeque<Vertex> getRoute() {
        return this.route;
    }

    @Override
    protected void buildRoute(int goalX, int goalY) {
        Vertex v = this.routeMap[goalX][goalY];
        this.route.add(v.scaleOffset(40, 0, 0));
        this.route.add(new Vertex(40 * (goalX - 1), 40 * (goalY - 1)));
        while (v.getRow() != startY || v.getColumn() != startX) {
            v = this.routeMap[v.getRow()][v.getColumn()];
            this.route.addFirst(v.scaleOffset(40, 0, 0));
        }
    }

    @Override
    public ArrayDeque<Vertex> getMapScan() {
        return this.scanRoute;
    }

    @Override
    public void setUpAlgorithm(int[][] map, int startY, int startX) {
        this.startY = startY;
        this.startX = startX;
        visited = new boolean[map.length][map[0].length];
        distance = new int[map.length][map[0].length];
        routeMap = new Vertex[map.length][map[0].length];
        heap = new PriorityQueue<>();
        route = new ArrayDeque<>();
        scanRoute = new ArrayDeque<>();
        initNeighbours(map);
        fillNeighbours(map);
        fillInfDist();
        distance[startY][startX] = 0;
        heap.add(new DistanceNode(new Vertex(startY, startX), 0));
    }

    @Override
    public String getName() {
        return "DIJKSTRA";
    }

    private void fillInfDist() {
        for (int i = 0; i < this.distance.length; i++) {
            for (int j = 0; j < this.distance[0].length; j++) {
                this.distance[i][j] = 999999;
            }
        }
    }

    private void initNeighbours(int[][] map) {
        this.neighbours = new ArrayList[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 1) {
                    this.neighbours[i][j] = new ArrayList<>();
                }
            }
        }
    }

    private void fillNeighbours(int[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] != 1) {
                    if (map[row - 1][col] != 1) {
                        addEdge(row, col, map[row - 1][col], -1, 0);
                    }
                    if (map[row + 1][col] != 1) {
                        addEdge(row, col, map[row + 1][col], 1, 0);
                    }
                    if (map[row][col - 1] != 1) {
                        addEdge(row, col, map[row][col - 1], 0, -1);
                    }
                    if (map[row][col + 1] != 1) {
                        addEdge(row, col, map[row][col + 1], 0, 1);
                    }
                }
            }
        }
    }

    private void addEdge(int row, int col, int value, int rowOffset, int colOffset) {
        if (value == 6) {
            this.neighbours[row][col].add(new Edge(new Vertex(row + rowOffset, col + colOffset), 2));
        } else if (value == 7) {
            this.neighbours[row][col].add(new Edge(new Vertex(row + rowOffset, col + colOffset), 4));
        } else {
            this.neighbours[row][col].add(new Edge(new Vertex(row + rowOffset, col + colOffset), 1));
        }
    }

}

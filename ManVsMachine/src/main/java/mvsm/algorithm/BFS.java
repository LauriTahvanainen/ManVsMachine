package mvsm.algorithm;

import java.util.ArrayDeque;

/**
 * Breadth First Search algorithm extending the abstract Algorithm class. Can
 * not calculate the shortest path on all maps.
 *
 * @see mvsm.algorithm.Algorithm
 */
public class BFS extends Algorithm {

    private int[][] map;
    private ArrayDeque<Vertex> queue;
    private boolean[][] visited;
    private ArrayDeque<Vertex> mapScan;
    private Vertex[][] routeMap;
    private int startY;
    private int startX;

    /**
     * Calculate the shortest route to the goal using Breadth First Search
     * algorithm.
     *
     * @param goalX X-coordinate of the goal..
     * @param goalY Y-coordinate of the goal.
     */
    @Override
    public void calculateRoute(int goalX, int goalY) {
        while (!this.queue.isEmpty() && !this.visited[goalX][goalY]) {
            Vertex v = this.queue.poll();
            checkTile(v, 1, 0);
            checkTile(v, 0, 1);
            checkTile(v, -1, 0);
            checkTile(v, 0, -1);
        }
        buildRoute(goalX, goalY);
    }

    private void checkTile(Vertex vertex, int xOffset, int yOffset) {
        if (this.map[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] != 1 && !this.visited[vertex.getRow() + yOffset][vertex.getColumn() + xOffset]) {
            this.visited[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] = true;
            this.queue.add(new Vertex(vertex.getRow() + yOffset, vertex.getColumn() + xOffset));
            this.mapScan.add(vertex.scaleOffset(40, yOffset, xOffset));
            this.routeMap[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] = vertex;
        }
    }

    @Override
    protected void buildRoute(int goalX, int goalY) {
        this.queue.clear();
        Vertex v = this.routeMap[goalX][goalY];
        this.queue.add(v.scaleOffset(40, 0, 0));
        this.queue.add(new Vertex(40 * (goalX - 1), 40 * (goalY - 1)));
        while (v.getRow() != startY || v.getColumn() != startX) {
            v = this.routeMap[v.getRow()][v.getColumn()];
            this.queue.addFirst(v.scaleOffset(40, 0, 0));
        }
    }

    @Override
    public ArrayDeque<Vertex> getRoute() {
        return this.queue;
    }

    @Override
    public ArrayDeque<Vertex> getMapScan() {
        return this.mapScan;
    }

    @Override
    public void setUpAlgorithm(int[][] map, int startY, int startX) {
        this.map = map;
        this.queue = new ArrayDeque<>();
        this.visited = new boolean[map.length][map[0].length];
        this.visited[startY][startX] = true;
        this.queue.add(new Vertex(startY, startX));
        this.mapScan = new ArrayDeque<>();
        this.routeMap = new Vertex[this.map.length][this.map[0].length];
        this.startY = startY;
        this.startX = startX;
    }

    @Override
    public String getName() {
        return "BFS";
    }

}

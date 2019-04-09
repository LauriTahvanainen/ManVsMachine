package algorithm;

import java.util.ArrayDeque;

public class BFS extends Algorithm {

    private int[][] map;
    private ArrayDeque<Vertex> queue;
    private boolean[][] visited;
    private ArrayDeque<Vertex> mapScan;
    private Vertex[][] routeMap;

    public BFS(int[][] map) {
        this.map = map;
        this.queue = new ArrayDeque<>();
        this.visited = new boolean[map.length][map[0].length];
        this.visited[1][1] = true;
        this.queue.add(new Vertex(1, 1));
        this.mapScan = new ArrayDeque<>();
        this.routeMap = new Vertex[this.map.length][this.map[0].length];
    }

    @Override
    public void calculateRoute() {
        while (!this.queue.isEmpty() && !this.visited[16][28]) {
            Vertex v = this.queue.poll();
            checkTile(v,1,0);
            checkTile(v,0,1);
            checkTile(v,-1,0);
            checkTile(v,0,-1);
//            if (this.map[v.getRow() + 1][v.getColumn()] != 1 && !this.visited[v.getRow() + 1][v.getColumn()]) {
//                this.visited[v.getRow() + 1][v.getColumn()] = true;
//                this.queue.add(new Vertex(v.getRow() + 1, v.getColumn()));
//                this.mapScan.add(v.scaleOffset(40, 1, 0));
//                //v to become successor of this vertex v
//                this.routeMap[v.getRow() + 1][v.getColumn()] = v;
//            }
//            if (this.map[v.getRow() - 1][v.getColumn()] != 1 && !this.visited[v.getRow() - 1][v.getColumn()]) {
//                this.visited[v.getRow() - 1][v.getColumn()] = true;
//                this.queue.add(new Vertex(v.getRow() - 1, v.getColumn()));
//                this.mapScan.add(v.scaleOffset(40, -1, 0));
//                this.routeMap[v.getRow() - 1][v.getColumn()] = v;
//            }
//            if (this.map[v.getRow()][v.getColumn() + 1] != 1 && !this.visited[v.getRow()][v.getColumn() + 1]) {
//                this.visited[v.getRow()][v.getColumn() + 1] = true;
//                this.queue.add(new Vertex(v.getRow(), v.getColumn() + 1));
//                this.mapScan.add(v.scaleOffset(40, 0, 1));
//                this.routeMap[v.getRow()][v.getColumn() + 1] = v;
//            }
//            if (this.map[v.getRow()][v.getColumn() - 1] != 1 && !this.visited[v.getRow()][v.getColumn() - 1]) {
//                this.visited[v.getRow()][v.getColumn() - 1] = true;
//                this.queue.add(new Vertex(v.getRow(), v.getColumn() - 1));
//                this.mapScan.add(v.scaleOffset(40, 0, -1));
//                this.routeMap[v.getRow()][v.getColumn() - 1] = v;
//
//            }
        }
        buildRoute();
    }

    private void checkTile(Vertex vertex, int xOffset, int yOffset) {
        if (this.map[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] != 1 && !this.visited[vertex.getRow() + yOffset][vertex.getColumn() + xOffset]) {
            this.visited[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] = true;
            this.queue.add(new Vertex(vertex.getRow() + yOffset, vertex.getColumn() + xOffset));
            this.mapScan.add(vertex.scaleOffset(40, yOffset, xOffset));
            //v to become successor of this vertex v
            this.routeMap[vertex.getRow() + yOffset][vertex.getColumn() + xOffset] = vertex;
        }
    }

    @Override
    protected void buildRoute() {
        //build the route backwards from the goal
        //because the movement is done with the translate of the sprite, there is a -1 x and y offset in the route, so the
        //last coordinate in the route is not the coordinate map[map.length - 2][map[0].length - 2], but -3
        this.queue.clear();
        Vertex v = this.routeMap[16][28];
        this.queue.add(new Vertex(40 * 15, 40 * 27));
        while (v.getRow() != 1 && v.getColumn() != 1) {
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

}

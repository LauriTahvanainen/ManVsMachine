package algorithm;

import sprite.Sprite;
import java.util.ArrayDeque;
import java.util.ArrayList;
import javafx.geometry.Point2D;

public class BFS extends Algorithm {

    private int[][] map;
    private Sprite machine;
    private ArrayDeque<Vertex> queue;
    private boolean[][] visited;
    private ArrayDeque<Vertex> routeMapScan;
    private Vertex[][] routeMap;
    
    public BFS(int[][] map, Sprite machine) {
        this.map = map;
        this.machine = machine;
        this.queue = new ArrayDeque<>();
        this.visited = new boolean[map.length][map[0].length];
        this.visited[1][1] = true;
        this.queue.add(new Vertex(1, 1));
        this.routeMapScan = new ArrayDeque<>();
        this.routeMap = new Vertex[this.map.length][this.map[0].length];
    }

    @Override
    public void setMachine(Sprite machine) {
        this.machine = machine;
    }

    @Override
    public void setMap(int[][] map) {
        this.map = map;
        this.visited = new boolean[map.length][map[0].length];
    }

    @Override
    public void calculateRoute() {
        while (!this.queue.isEmpty() && !this.visited[16][28]) {
            Vertex v = this.queue.poll();
            if (this.map[v.getRow() + 1][v.getColumn()] != 1 && !this.visited[v.getRow() + 1][v.getColumn()]) {
                this.visited[v.getRow() + 1][v.getColumn()] = true;
                this.queue.add(new Vertex(v.getRow() + 1, v.getColumn()));
                this.routeMapScan.add(v.scaleOffset(40, 1, 0));
                this.routeMap[v.getRow() + 1][v.getColumn()] = v;
            }
            if (this.map[v.getRow() - 1][v.getColumn()] != 1 && !this.visited[v.getRow() - 1][v.getColumn()]) {
                this.visited[v.getRow() - 1][v.getColumn()] = true;
                
                this.queue.add(new Vertex(v.getRow() - 1, v.getColumn()));
                this.routeMapScan.add(v.scaleOffset(40, -1, 0));
                this.routeMap[v.getRow() - 1][v.getColumn()] = v;
                
            }
            if (this.map[v.getRow()][v.getColumn() + 1] != 1 && !this.visited[v.getRow()][v.getColumn() + 1]) {
                this.visited[v.getRow()][v.getColumn() + 1] = true;
                
                this.queue.add(new Vertex(v.getRow(), v.getColumn() + 1));
                this.routeMapScan.add(v.scaleOffset(40, 0, 1));
                this.routeMap[v.getRow()][v.getColumn() + 1] = v;
                
            }
            if (this.map[v.getRow()][v.getColumn() - 1] != 1 && !this.visited[v.getRow()][v.getColumn() - 1]) {
                this.visited[v.getRow()][v.getColumn() - 1] = true;
                
                this.queue.add(new Vertex(v.getRow(), v.getColumn() - 1));
                this.routeMapScan.add(v.scaleOffset(40, 0, -1));
                this.routeMap[v.getRow()][v.getColumn() - 1] = v;
                
            }
        }
    }

    @Override
    public void takeStep() {
        Vertex v = this.queue.peekFirst();
        if (this.machine.getForm().getTranslateY() == v.getRow() && this.machine.getForm().getTranslateX() == v.getColumn()) {
            this.queue.pop();
        }
        this.machine.moveAlong(new Point2D(v.getColumn() - this.machine.getForm().getTranslateX(), v.getRow() - this.machine.getForm().getTranslateY()));
    }

    
    @Override
    public void buildRoute() {
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

    //TODO
    @Override
    public void scanTile() {
        
        
    }

}

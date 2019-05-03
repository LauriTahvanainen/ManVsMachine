 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvsm.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Depth First Search algorithm extending the abstract Algorithm class. Every
 * new DFS instance calculates a different route.
 *
 * @see mvsm.algorithm.Algorithm
 */
public class DFS extends Algorithm {

    private ArrayList<Vertex>[][] neighbours;
    private boolean visited[][];
    private int[][] map;
    private ArrayDeque<Vertex> route;
    private ArrayDeque<Vertex> mapScan;
    private Vertex[][] routeMap;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    private boolean calculated;

    @Override
    public void calculateRoute(int goalX, int goalY) {
        this.goalX = goalX;
        this.goalY = goalY;
        search(new Vertex(startY, startX));
        buildRoute(goalX, goalY);
    }

    @Override
    public ArrayDeque<Vertex> getRoute() {
        return route;
    }

    @Override
    protected void buildRoute(int goalX, int goalY) {
        Vertex v = this.routeMap[goalX][goalY];
        this.route.add(new Vertex(40 * (goalX - 1), 40 * (goalY - 1)));
        while (v.getRow() != startY || v.getColumn() != startX) {
            v = this.routeMap[v.getRow()][v.getColumn()];
            this.route.addFirst(v.scaleOffset(40, 0, 0));
        }
    }

    @Override
    public ArrayDeque<Vertex> getMapScan() {
        return mapScan;
    }

    @Override
    public void setUpAlgorithm(int[][] map, int startY, int startX) {
        this.map = map;
        this.visited = new boolean[map.length][map[0].length];
        this.route = new ArrayDeque<>();
        this.mapScan = new ArrayDeque<>();
        this.neighbours = new ArrayList[map.length][map[0].length];
        this.routeMap = new Vertex[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 1) {
                    this.neighbours[i][j] = new ArrayList<>();
                }
            }
        }
        this.startX = startX;
        this.startY = startY;
        buildNeighbours();
    }

    private void search(Vertex u) {
        this.visited[u.getRow()][u.getColumn()] = true;
        if (u.getColumn() == goalY && u.getRow() == goalX) {
            this.calculated = true;
        }
        this.mapScan.add(u.scaleOffset(40, 0, 0));
        for (Vertex v : this.neighbours[u.getRow()][u.getColumn()]) {
            if (!visited[v.getRow()][v.getColumn()]) {
                this.routeMap[v.getRow()][v.getColumn()] = u;
                if (!calculated) {
                    search(v);
                }
            }
        }
    }

    private void buildNeighbours() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] != 1) {
                    addNeighbours(row, col);
                    Collections.shuffle(this.neighbours[row][col]);
                }
            }
        }
    }

    private void addNeighbours(int row, int col) {
        if (map[row - 1][col] != 1) {
            this.neighbours[row][col].add(new Vertex(row - 1, col));
        }
        if (map[row + 1][col] != 1) {
            this.neighbours[row][col].add(new Vertex(row + 1, col));
        }
        if (map[row][col - 1] != 1) {
            this.neighbours[row][col].add(new Vertex(row, col - 1));
        }
        if (map[row][col + 1] != 1) {
            this.neighbours[row][col].add(new Vertex(row, col + 1));
        }
    }

    @Override
    public String getName() {
        return "DFS";
    }

}

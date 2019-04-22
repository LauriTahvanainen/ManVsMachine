
package algorithm;

import java.util.ArrayDeque;


public abstract class Algorithm {
    
    public abstract void calculateRoute(int goalX, int goalY);
    public abstract ArrayDeque<Vertex> getRoute();
    protected abstract void buildRoute(int goalX, int goalY);
    public abstract ArrayDeque<Vertex> getMapScan();
    public abstract void setUpAlgorithm(int[][] map, int startX, int startY);
}

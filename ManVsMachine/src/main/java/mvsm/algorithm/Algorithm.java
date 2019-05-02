package mvsm.algorithm;

import java.util.ArrayDeque;

/**
 * Abstract class for defining the methods of every algorithm in the game. Every
 * implementation of an algorithm is different.
 */
public abstract class Algorithm {

    /**
     * Calculates the route to the goal.
     *
     * @param goalX X-Coordinate of the goal.
     * @param goalY Y-Coordinate of the goal.
     */
    public abstract void calculateRoute(int goalX, int goalY);

    /**
     * Get the calculated route.
     *
     * @return The route calculated by the algorithm as an Vertex ArrayDeque.
     */
    public abstract ArrayDeque<Vertex> getRoute();

    /**
     * Build the route in the right form, depending on the algorithm to be
     * implemented.
     *
     * @param goalX X-Coordinate of the goal
     * @param goalY Y-Coordinate of the goal
     */
    protected abstract void buildRoute(int goalX, int goalY);

    /**
     * Get the scanned route as an Vertex ArrayDeque.
     *
     * @return The the scanned route as an Vertex ArrayDeque.
     */
    public abstract ArrayDeque<Vertex> getMapScan();

    /**
     * Set up the algorithm so that a route can be calculated.
     *
     * @param map The map to run the algorithm in.
     * @param startY Y-Coordinate of the starting point of the algorithm.
     * @param startX X-Coordinate of the starting point of the algorithm.
     */
    public abstract void setUpAlgorithm(int[][] map, int startY, int startX);
    
    public abstract String getName();
}

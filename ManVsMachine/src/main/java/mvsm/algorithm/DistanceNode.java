package mvsm.algorithm;

/**
 * Data structure for Dijkstras algorithm and other algorithms that need to use
 * this specific structure. Has the coordinate information of the node as an
 * Vertex, and a distance to the node as variables. Implements comparable.
 *
 * @see mvsm.algorithm.Dijkstra
 * @see mvsm.algorithm.Vertex
 */
public class DistanceNode implements Comparable {

    private final Vertex vertex;
    private final int distance;

    public DistanceNode(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public int compareTo(Object o) {
        DistanceNode comp = (DistanceNode) o;
        return this.distance - comp.distance;
    }

}

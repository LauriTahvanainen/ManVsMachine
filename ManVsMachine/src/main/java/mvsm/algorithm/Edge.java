package mvsm.algorithm;

/**
 * Data structure for Dijkstras algorithm and other algorithms that use weighted
 * graphs and need to handle edges. End of the edge represented as an
 * Vertex-object, weight on the edge as an integer.
 *
 * @see mvsm.algorithm.Dijkstra
 * @see mvsm.algorithm.Vertex
 */
public class Edge {

    private final Vertex end;
    private final int weight;

    public Edge(Vertex end, int weight) {
        this.end = end;
        this.weight = weight;
    }

    public Vertex getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

}

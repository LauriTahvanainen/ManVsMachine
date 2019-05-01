package mvsm.algorithm;

/**
 * A data structure for the algorithms. Used in saving the routes calculated by
 * the algorithm in the form that the Machine can use the route given by the
 * algorithm in its movement.
 */
public class Vertex {

    private final int row;
    private final int column;

    public Vertex(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * An utility method used in scaling the coordinates used by the algorithm
     * to fit the coordinates of a GridPane, the map-background. Offsets the
     * coordinates by -1, the given offsets and scales them by the size of a
     * game-tile. Returns a new Vertex. Both coordinates are subtracted by one
     * because the machine is always placed in to the GridPane coordinate 1:1
     * and the route should be built to the coordinate system of the machine.
     *
     * @param scale By what Integer to multiply the coordinates by. Usually the
     * size of a game-tile.
     * @param yOffSet An Integer offSet to the Y-coordinate of the Vertex.
     * @param xOffSet An Integer offSet to the X-coordinate of the Vertex.
     * @return A new new Vertex scaled with the given parameters.
     */
    public Vertex scaleOffset(int scale, int yOffSet, int xOffSet) {
        return new Vertex((this.row - 1 + yOffSet) * scale, (this.column - 1 + xOffSet) * scale);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.column;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.column != other.column) {
            return false;
        }
        return true;
    }

}

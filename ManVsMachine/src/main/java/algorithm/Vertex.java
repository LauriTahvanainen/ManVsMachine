
package algorithm;


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
    
    public Vertex scaleOffset(int scale, int yOffSet, int xOffSet) {
        return new Vertex((this.row - 1 + yOffSet) * 40, (this.column - 1 + xOffSet) * 40);
    }
}


package Sprites.algorithmlogic;


public class Vertex {
    private int row;
    private int column;

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
    
    public Vertex scaleOffset(int scale, int xOffSet, int yOffSet) {
        return new Vertex((this.row - 1 + xOffSet) * 40, (this.column - 1 + yOffSet) * 40);
    }
}

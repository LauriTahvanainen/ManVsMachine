package mvsm.game;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Enum for different types of Tiles for the map.
 */
public enum Tile {
    WALL(40, 40, Color.BLACK),
    SCAN_TILE(40, 40, Color.rgb(255, 25, 25, 0.2)),
    SAND(40, 40, Color.WHEAT),
    FLOOR(40, 40, Color.BURLYWOOD),
    WATER(40, 40, Color.AQUA);

    private final int height;
    private final int width;
    private final Color color;

    private Tile(int height, int width, Color color) {
        this.height = height;
        this.width = width;
        this.color = color;
    }

    /**
     *
     * @return a new tile specified by the constructor and the selected Tile.
     */
    public Rectangle getTile() {
        return new Rectangle(this.height, this.width, this.color);
    }

    /**
     *
     * @param node to check equals with
     * @return True, if the node equals this Tile, false otherwise.
     */
    public boolean nodeEqualsTile(Node node) {
        Rectangle rectangle;
        try {
            rectangle = (Rectangle) node;
        } catch (Exception e) {
            return false;
        }
        if (this.height != rectangle.getHeight()) {
            return false;
        }
        if (this.width != rectangle.getWidth()) {
            return false;
        }
        return this.color.equals((Color) rectangle.getFill());
    }

}

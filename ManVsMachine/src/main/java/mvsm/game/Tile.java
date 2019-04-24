package mvsm.game;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    public Rectangle getTile() {
        return new Rectangle(this.height, this.width, this.color);
    }

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

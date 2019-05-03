package mvsm.game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Enum for different types of Tiles for the map.
 */
public enum Tile {
    WALL(40, 40, new ImagePattern(new Image(Tile.class.getResourceAsStream("/textures/wallpane.png")))),
    SCAN_TILE(40, 40, Color.rgb(255, 25, 25, 0.2)),
    SAND(40, 40, new ImagePattern(new Image(Tile.class.getResourceAsStream("/textures/sand.png")))),
    FLOOR(40, 40, new ImagePattern(new Image(Tile.class.getResourceAsStream("/textures/floor.png")))),
    WATER(40, 40, new ImagePattern(new Image(Tile.class.getResourceAsStream("/textures/water.png"))));

    private final int height;
    private final int width;
    private final Paint texture;

    private Tile(int height, int width, Color color) {
        this.height = height;
        this.width = width;
        this.texture = color;
    }
    
    private Tile(int height, int width, ImagePattern texture) {
        this.height = height;
        this.width = width;
        this.texture = texture;
    }

    /**
     *
     * @return a new tile specified by the constructor and the selected Tile.
     */
    public Rectangle getTile() {
        return new Rectangle(this.height, this.width, this.texture);
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
        return this.texture.equals(rectangle.getFill());
    }

}

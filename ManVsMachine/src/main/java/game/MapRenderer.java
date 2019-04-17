package game;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapRenderer {

    public GridPane renderMap(int[][] map) {
        GridPane background = new GridPane();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    background.add(Tile.WALL.getTile(), j, i);
                    continue;
                }
                if (map[i][j] == 2) {
                    background.add(Tile.SAND.getTile(), j, i);
                    continue;
                }
                background.add(Tile.FLOOR.getTile(), j, i);
            }
        }
        return background;
    }
}

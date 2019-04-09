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
                    background.add(new Rectangle(40, 40, Color.BLACK), j, i);
                }
            }
        }
        return background;
    }
}

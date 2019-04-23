package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import sprite.Machine;
import sprite.Sprite;

public class MapRenderer {

    public GridPane renderMap(int[][] map) {
        GridPane background = new GridPane();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int value = map[i][j];
                if (value == 1) {
                    background.add(Tile.WALL.getTile(), j, i);
                    continue;
                }
                if (value == 6) {
                    background.add(Tile.SAND.getTile(), j, i);
                    continue;
                }
                if (value == 7) {
                    background.add(Tile.WATER.getTile(), j, i);
                }
                background.add(Tile.FLOOR.getTile(), j, i);
            }
        }
        return background;
    }

    public int[][] formArrayMap(String mapName) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(MapRenderer.class.getResourceAsStream("/maps/" + mapName)));
        int[][] ret = new int[18][30];
        int row = 0, column = 0;
        try {
            while (reader.ready()) {
                char c = (char) reader.read();
                if (c == 32) {
                    continue;
                }
                if (c == 10) {
                    row++;
                    column = 0;
                    continue;
                }
                ret[row][column] = Character.getNumericValue(c);
                column++;
            }
        } catch (IOException e) {
            return new int[1][1];
        }
        return ret;
    }

    //Machine and Sprite goal and form coordinates are put into the return array. Machine coordinates 0-3, Sprite 4-7.
    public int[] getMachineCoordinates(int[][] map) {
        int[] ret = new int[8];
        for (int row = 1; row < map.length - 1; row++) {
            for (int column = 1; column < map[1].length - 1; column++) {
                checkValue(ret, row, column, map[row][column]);
            }
        }
        return ret;
    }

    public void placeSpritesOnMap(int[] coordinates, GridPane background, Sprite player, Machine machine, Rectangle playerGoal, Rectangle machineGoal) {
        background.add(player.getForm(), coordinates[5], coordinates[4]);
        background.add(playerGoal, coordinates[7], coordinates[6]);
        background.add(machine.getForm(), coordinates[1], coordinates[0]);
        background.add(machine.getScanner().getScannerHead(), coordinates[1], coordinates[0]);
        background.add(machineGoal, coordinates[3], coordinates[2]);
    }

    private void checkValue(int[] values, int row, int column, int value) {
        if (value == 4) {
            values[0] = row;
            values[1] = column;
        }
        if (value == 5) {
            values[2] = row;
            values[3] = column;
        }
        if (value == 2) {
            values[4] = row;
            values[5] = column;
        }
        if (value == 3) {
            values[6] = row;
            values[7] = column;
        }
    }
}

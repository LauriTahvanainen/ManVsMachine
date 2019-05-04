package mvsm.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import mvsm.sprite.Machine;
import mvsm.sprite.Sprite;

/**
 * An utility class for rendering maps. Used by the PlayinState in:
 *
 * @see mvsm.ui.PlayingState#restore()
 */
public class MapRenderer {

    /**
     * Draws the map to a GridPane using the 2-dimensional array given as a
     * parameter. Drawing is done using tiles.
     *
     * @param map as an 2-dimensional integer array.
     * @return a new GridPane with a drawn map.
     * @see mvsm.game.Tile
     */
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
                    continue;
                }
                background.add(Tile.FLOOR.getTile(), j, i);
            }
        }
        return background;
    }

    /**
     * Form a 2-dimensional Integer array from a text file.
     *
     * @param mapName name of the map file to read the array from.
     * @return 2-dimensional Integer array. Blueprint for rendering.
     */
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

    /**
     * get an integer array that contains the coordinates for where to place the
     * Sprites and the goals on the map.
     *
     * @param map the map from where to get the coordinates. Player start is
     * marked with the number 2, goal with the number 3. 4 and 5 for Machine.
     * @return Integer array with the coordinates. 0-3 are for Machine start and
     * goal coordinates. 4-7 are for Player coordinates.
     */
    public int[] getSpriteCoordinates(int[][] map) {
        int[] ret = new int[8];
        for (int row = 1; row < map.length - 1; row++) {
            for (int column = 1; column < map[1].length - 1; column++) {
                checkValue(ret, row, column, map[row][column]);
            }
        }
        return ret;
    }

    /**
     * Places Sprites and goals on the GridPane background after the map has
     * been rendered.
     *
     * @param coordinates for placing the Sprites and goals.
     * @param background the GridPane map
     * @param player the player's Sprite
     * @param machine the machine's Sprite
     * @param playerGoal player's goal
     * @param machineGoal machine's goal
     */
    public void placeSpritesOnMap(int[] coordinates, GridPane background, Sprite player, Machine machine, Rectangle playerGoal, Rectangle machineGoal) {
        background.add(player.getForm(), coordinates[5], coordinates[4]);
        background.add(playerGoal, coordinates[7], coordinates[6]);
        background.add(machine.getForm(), 1, 1);
        machine.setTranslates((coordinates[1] - 1) * 40, (coordinates[0] - 1) * 40);
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

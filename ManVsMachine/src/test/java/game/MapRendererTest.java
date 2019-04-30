/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import mvsm.game.MapRenderer;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mvsm.algorithm.BFS;
import mvsm.game.Tile;
import mvsm.sprite.Machine;
import mvsm.sprite.Sprite;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapRendererTest {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 4, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 3, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 5, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private MapRenderer renderer;

    public MapRendererTest() {
        this.renderer = new MapRenderer();
    }

    @Test
    public void rendererDoesntReturnNull() {
        GridPane ret = this.renderer.renderMap(map);
        assertTrue(ret != null);
    }

    @Test
    public void mapDrawnFull() {
        GridPane ret = this.renderer.renderMap(map);
        assertTrue(ret.getChildren().size() == 540);
    }

    @Test
    public void mapReadFromFileRight() {
        int[][] ret = this.renderer.formArrayMap("map1");
        for (int row = 0; row < ret.length; row++) {
            for (int col = 0; col < ret[0].length; col++) {
                if (map[row][col] != ret[row][col]) {
                    fail("The map was not read right, the value at: " + row + ":" + col + " should be " + map[row][col] + ", but was " + ret[row][col]);
                }
            }
        }
    }

    @Test
    public void mapRenderedProperly() {
        int[][] ret = this.renderer.formArrayMap("map1");
        GridPane background = this.renderer.renderMap(ret);
        for (int i = 0; i < ret.length; i++) {
            for (int j = 0; j < ret[0].length; j++) {
                if (ret[i][j] == 1) {
                    if (!Tile.WALL.nodeEqualsTile(background.getChildren().get(i * 30 + j))) {
                        fail("Walls not drawn right");
                    }
                } else if (ret[i][j] == 0) {
                    if (!Tile.FLOOR.nodeEqualsTile(background.getChildren().get(i * 30 + j))) {
                        fail("Floor not drawn right");
                    }
                } else if (ret[i][j] == 6) {
                    if (!Tile.SAND.nodeEqualsTile(background.getChildren().get(i * 30 + j))) {
                        fail("Sand not drawn right");
                    }
                } else if (ret[i][j] == 7) {
                    if (!Tile.WATER.nodeEqualsTile(background.getChildren().get(i * 30 + j))) {
                        fail("Water not drawn right");
                    }
                }
            }
        }
    }

    @Test
    public void getSpriteCoordinatesReturnsRightCoordinates() {
        int[][] ret = this.renderer.formArrayMap("map1");
        int[] coord = this.renderer.getSpriteCoordinates(ret);
        if (!(coord[0] == 1 && coord[1] == 1 && coord[2] == 16 && coord[3] == 28 && coord[4] == 1 && coord[5] == 28 && coord[6] == 16 && coord[7] == 1)) {
            fail("Coordinates are wrong! Should be: 1:1  16:28  1:28  16:1" + ", but was: " + coord[0] + ":" + coord[1] + "  " + coord[2] + ":" + coord[3] + "  " + coord[4] + ":" + coord[5] + "  " + coord[6] + ":" + coord[7]);
        }
        ret = this.renderer.formArrayMap(("map3"));
        coord = this.renderer.getSpriteCoordinates(ret);
        if (!(coord[0] == 9 && coord[1] == 15 && coord[2] == 1 && coord[3] == 1 && coord[4] == 8 && coord[5] == 14 && coord[6] == 16 && coord[7] == 28)) {
            fail("Coordinates are wrong! Should be: 9:15  1:1  8:14  16:28 " + ", but was: " + coord[0] + ":" + coord[1] + "  " + coord[2] + ":" + coord[3] + "  " + coord[4] + ":" + coord[5] + "  " + coord[6] + ":" + coord[7]);
        }
    }

    @Test
    public void machineIsAlwaysPlacedLeftCorner() {
        int[][] m1 = this.renderer.formArrayMap("map1");
        int[][] m3 = this.renderer.formArrayMap("map3");
        GridPane backG = this.renderer.renderMap(m1);
        int[] coords = this.renderer.getSpriteCoordinates(m1);
        Sprite player = new Sprite(Color.RED, 20, 20);
        Machine machine = new Machine(Color.BLUE, 20, 20, new BFS());
        machine.getAlgorithm().setUpAlgorithm(m1, coords[0], coords[1]);
        machine.calculateRoute(coords[2], coords[3]);
        Rectangle machineGoal = new Rectangle(40, 40, Color.BLUE);
        Rectangle playerGoal = new Rectangle(40, 40, Color.RED);
        this.renderer.placeSpritesOnMap(coords, backG, player, machine, playerGoal, machineGoal);
//        if (!) {
//            fail("The machine was not placed in the right coordinates!");
//        }
    }
}

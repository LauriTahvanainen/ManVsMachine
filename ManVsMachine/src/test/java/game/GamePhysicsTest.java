package game;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mvsm.algorithm.Algorithm;
import mvsm.algorithm.BFS;
import mvsm.eventhandling.KeyEventHandler;
import mvsm.game.GamePhysics;
import mvsm.game.MapRenderer;
import mvsm.game.Tile;
import mvsm.sprite.Machine;
import mvsm.sprite.Sprite;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class GamePhysicsTest {

    private int[][] map;
    private GamePhysics testPhysics;
    private MapRenderer renderer;
    private Text timeScore;
    private Text lengthScore;
    private KeyEventHandler testHandler;
    private Scene scene;
    private GridPane background;
    private Sprite player;
    private Machine machine;
    private Rectangle machineGoal;
    private Rectangle playerGoal;
    private Algorithm bfs;
    int[] coords;

    @Before
    public void setUp() {
        final JFXPanel fxPanel = new JFXPanel();
        this.renderer = new MapRenderer();
        this.player = new Sprite(20, 20);
        this.playerGoal = new Rectangle(40, 40, Color.RED);
        this.machineGoal = new Rectangle(40, 40, Color.BLUE);
        this.map = this.renderer.formArrayMap("map3");
        this.background = this.renderer.renderMap(map);
        this.coords = this.renderer.getSpriteCoordinates(map);
        this.bfs = new BFS();
        bfs.setUpAlgorithm(map, coords[0], coords[1]);
        this.machine = new Machine(20, 20, bfs);
        this.machine.calculateRoute(coords[2], coords[3]);
        this.machine.getScanner().setBackground(background);
        this.scene = new Scene(this.background);
        this.timeScore = new Text();
        this.lengthScore = new Text();
        this.testHandler = new KeyEventHandler();
        this.renderer.placeSpritesOnMap(coords, background, player, machine, playerGoal, machineGoal);
        this.testPhysics = new GamePhysics(testHandler, timeScore, lengthScore);
        this.testPhysics.setUpPhysicsWorld(background, player, machine, playerGoal, machineGoal, coords[1], coords[0]);
    }

    @Test
    public void physicsWordlSetUpProperly() {
        if (this.testPhysics.getBackground().equals(this.background)) {
            return;
        }
        if (this.testPhysics.getHandler().equals(this.testHandler)) {
            return;
        }
        if (this.testPhysics.getMachine().equals(this.machine)) {
            return;
        }
        if (this.testPhysics.getPlayer().equals(this.player)) {
            return;
        }
        if (this.testPhysics.getPlayerGoal().equals(this.playerGoal)) {
            return;
        }
        if (this.testPhysics.getMachineGoal().equals(this.machineGoal)) {
            return;
        }
        if (this.testPhysics.getLengthScoreText().equals(this.lengthScore)) {
            return;
        }
        if (this.testPhysics.getTimeScoreText().equals(this.timeScore)) {
            return;
        }
        if (this.testPhysics.getMachineRestoreX() == 560 && this.testPhysics.getMachineRestoreY() == 320) {
            return;
        } else {
            fail("The restore coordinates for the machine were not set up rigth!");
        }
        fail("Physics not set up properly!");
    }

    @Test
    public void restoreTest() {
        while (!this.machine.getRoute().isEmpty()) {
            this.testPhysics.updateGameWorld();
        }
        assertTrue(!this.timeScore.getText().equals("Time Score: 2000") && this.lengthScore.getText().equals("Route Length: 0"));
        assertTrue(this.machine.getForm().getTranslateX() != 560 && this.machine.getForm().getTranslateY() != 320 && this.machine.getRoute().isEmpty() && this.machine.getScanRoute().isEmpty());
        this.testPhysics.restoreLevel();
        this.testPhysics.updateGameWorld();
        assertTrue(this.timeScore.getText().equals("Time Score: 2000") && this.lengthScore.getText().equals("Route Length: 0"));
        assertTrue(this.machine.getForm().getTranslateX() == 560 && this.machine.getForm().getTranslateY() == 320 && this.machine.getRoute().size() == 87 && this.machine.getScanRoute().size() == 116);
    }

    @Test
    public void ifEscPressedReturnMinusTwo() {
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.ESCAPE, false, false, false, false));
        assertTrue(this.testPhysics.updateGameWorld() == -2);
    }

    @Test
    public void restoreClearsScanFromScreen() {
        for (int i = 0; i < 20; i++) {
            this.testPhysics.updateGameWorld();
        }
        this.testPhysics.restoreLevel();
        for (Node node : this.background.getChildren()) {
            if (Tile.SCAN_TILE.equals(node)) {
                fail("There should be no scantiles on the map!");
            }
        }
    }

    @Test
    public void machineFindsToGoal() {
        while (!this.machine.getRoute().isEmpty()) {
            this.testPhysics.updateGameWorld();
        }
        if (machine.getTranslateX() == (this.coords[2] - 1) * 40 && machine.getTranslateY() == (this.coords[3] - 1) * 40) {
            return;
        }
        fail("Machine does not end up at the goal!");
    }

    @Test
    public void scannerScansAndClearsScan() {
        for (int i = 0; i < 600; i++) {
            this.testPhysics.updateGameWorld();
        }
        assertTrue(this.machine.getScanRoute().isEmpty());
        for (Node node : this.background.getChildren()) {
            if (Tile.SCAN_TILE.equals(node)) {
                fail("There should be no SCAN_TILEs on the map!");
            }
        }
    }

}

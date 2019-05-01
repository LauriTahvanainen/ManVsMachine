package game;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import mvsm.sprite.Machine;
import mvsm.sprite.Sprite;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GamePhysicsTest {

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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        final JFXPanel fxPanel = new JFXPanel();
        this.renderer = new MapRenderer();
        this.player = new Sprite(Color.RED, 20, 20);
        this.playerGoal = new Rectangle(40,40,Color.RED);
        this.machineGoal = new Rectangle(40,40,Color.BLUE);
        this.map = this.renderer.formArrayMap("map3");
        this.background = this.renderer.renderMap(map);
        int[] coords = this.renderer.getSpriteCoordinates(map);
        this.bfs = new BFS();
        bfs.setUpAlgorithm(map, coords[0], coords[1]);
        this.machine = new Machine(Color.BLUE, 20, 20, bfs);
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

    @After
    public void tearDown() {
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
        for (int i = 0; i < 600; i++) {
            this.testPhysics.updateGameWorld();
        }
        assertTrue(!this.timeScore.getText().equals("Time Score: 2000") && this.lengthScore.getText().equals("Route Length: 0"));
        assertTrue(this.machine.getForm().getTranslateX() != 560 && this.machine.getForm().getTranslateY() != 320);
        this.testPhysics.restoreLevel();
        this.testPhysics.updateGameWorld();
        assertTrue(this.timeScore.getText().equals("Time Score: 2000") && this.lengthScore.getText().equals("Route Length: 0"));
        assertTrue(this.machine.getForm().getTranslateX() == 560 && this.machine.getForm().getTranslateY() == 320);        
    }
    
    @Test
    public void playerMovementTest() {
        this.machine.getScanRoute().clear();
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        System.out.println(this.player.getForm().getTranslateX() + ":" + this.player.getForm().getTranslateY());
        System.out.println("");
        //assertTrue(this.player.getForm().getTranslateX() == -1 && this.player.getForm().getTranslateY() == 0);
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        System.out.println(this.player.getForm().getTranslateX() + ":" + this.player.getForm().getTranslateY());
        System.out.println("");
        //assertTrue(this.player.getForm().getTranslateX() == -1 && this.player.getForm().getTranslateY() == -1);
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        System.out.println(this.player.getForm().getTranslateX() + ":" + this.player.getForm().getTranslateY());
        System.out.println("");
        //assertTrue(this.player.getForm().getTranslateX() == 1 && this.player.getForm().getTranslateY() == -1);
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.DOWN, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        this.testPhysics.updateGameWorld();
        System.out.println(this.player.getForm().getTranslateX() + ":" + this.player.getForm().getTranslateY());
        System.out.println("");
        if (this.player.getForm().getTranslateX() == 1 && this.player.getForm().getTranslateY() == -1) {
            return;
        }
        fail("The player ends up in wrong coordinates, should be x:y, but was "+ this.player.getForm().getTranslateX() + ":" + this.player.getForm().getTranslateY());
    }
    
}

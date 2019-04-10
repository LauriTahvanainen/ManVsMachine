package game;

import algorithm.BFS;
import eventhandling.KeyEventHandler;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sprite.Machine;
import sprite.Sprite;

public class GamePhysicsTest {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private GamePhysics testPhysics;
    private Machine testMachine;
    private MapRenderer testRenderer;
    private Sprite testPlayer;
    private GridPane testBackground;
    private static final Rectangle PLAYERGOAL = new Rectangle(40, 40, Color.RED);
    private static final Rectangle MACHINEGOAL = new Rectangle(40, 40, Color.BLUE);
    private final KeyEventHandler testHandler;
    private Scene scene;

    public GamePhysicsTest() {
        this.testHandler = new KeyEventHandler();
        this.testPhysics = new GamePhysics(testHandler, PLAYERGOAL, MACHINEGOAL);
        this.testPlayer = new Sprite(Color.ALICEBLUE, 20, 20);
        this.testPlayer.clearTranslate();
        this.testMachine = new Machine(Color.BLANCHEDALMOND, 20, 20, new BFS(this.map));
        this.testRenderer = new MapRenderer();
        this.testBackground = this.testRenderer.renderMap(map);
        this.testBackground.add(this.testPlayer.getForm(), 28, 1);
        this.testBackground.add(this.testMachine.getForm(), 1, 1);
        this.testBackground.add(MACHINEGOAL, 28, 16);
        this.testBackground.add(PLAYERGOAL, 1, 16);
        GridPane.setHalignment(this.testPlayer.getForm(), HPos.CENTER);
        GridPane.setHalignment(this.testMachine.getForm(), HPos.CENTER);
        this.testPhysics.setUpPhysicsWorld(testBackground, testPlayer, testMachine);
        this.scene = new Scene(this.testBackground, 1200, 720);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void playerMovementTest() {
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
//        for (int i = 0; i < 100; i++) {
//            this.testPhysics.updateGameWorld();
//        }
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.DOWN, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
//        this.testPhysics.updateGameWorld();
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
//        this.testPhysics.updateGameWorld();
//        this.testPhysics.updateGameWorld();
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
//        this.testPhysics.updateGameWorld();
//        if (this.testPlayer.getTranslateX() == 0 && this.testPlayer.getTranslateY() == 0.5) {
//            return;
//        }
//        fail("The player ends up in the wrong coordinates. The player should be at 0.0:0.5 but is at: " + this.testPlayer.getTranslateX() + ":" + this.testPlayer.getTranslateY());
//    }

}

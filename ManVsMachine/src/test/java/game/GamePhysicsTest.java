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
import statemanagement.StateManager;
import ui.Main;

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
    private Sprite testPlayer;
    private GridPane testBackground;
    private static final Rectangle PLAYERGOAL = new Rectangle(40, 40, Color.RED);
    private static final Rectangle MACHINEGOAL = new Rectangle(40, 40, Color.BLUE);
    private final KeyEventHandler testHandler;
    private Scene scene;

    public GamePhysicsTest() {
        this.testHandler = new KeyEventHandler();
        this.testPhysics = new GamePhysics(testHandler);
        this.testPlayer = new Sprite(Color.ALICEBLUE, 20, 20);
        this.testMachine = new Machine(Color.BLANCHEDALMOND, 20, 20, new BFS(this.map));
        this.testBackground = new GridPane();
        this.testBackground.add(MACHINEGOAL, 4, 6);
        this.testBackground.add(PLAYERGOAL, 1, 1);
        this.testBackground.add(new Rectangle(40,40,Color.BLACK), 2, 3);  
        this.testBackground.add(new Rectangle(40,40,Color.BLACK), 3, 2);  
        this.testBackground.add(this.testPlayer.getForm(), 14, 14);
        this.testBackground.add(this.testMachine.getForm(), 4, 4);
        GridPane.setHalignment(this.testPlayer.getForm(), HPos.CENTER);
        GridPane.setHalignment(this.testMachine.getForm(), HPos.CENTER);
        this.testPhysics.setUpPhysicsWorld(testBackground, testPlayer, testMachine, PLAYERGOAL, MACHINEGOAL);
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
        this.testPlayer.getForm().getBoundsInParent();
        this.testPlayer.getForm().getBoundsInParent();
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void playerMovementTest() {
//        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.LEFT, false, false, false, false));
//        for (int i = 0; i < 100; i++) {
//            System.out.println(this.testPlayer.getForm().getBoundsInLocal().equals(this.testPlayer.getForm().getBoundsInParent()));
//            this.testPhysics.updateGameWorld();
//            this.testBackground.getChildren().forEach(x -> System.out.println(x.getBoundsInParent()));
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

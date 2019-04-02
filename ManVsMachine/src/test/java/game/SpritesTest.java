
package game;

import Sprites.Sprite;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SpritesTest {
    private Sprite testSprite;
    
    public SpritesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.testSprite = new Sprite(Color.BLACK, 10, 10);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMoveUp() {
        this.testSprite.moveUp();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0 && this.testSprite.getForm().getTranslateY() == -0.5);
    }
    @Test
    public void testMoveDown() {
        this.testSprite.moveDown();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0 && this.testSprite.getForm().getTranslateY() == 0.5);
    }
    @Test
    public void testMoveLeft() {
        this.testSprite.moveLeft();
        assertTrue(this.testSprite.getForm().getTranslateX() == -0.5 && this.testSprite.getForm().getTranslateY() == 0);
    }
    @Test
    public void testMoveRight() {
        this.testSprite.moveRight();
        assertTrue(this.testSprite.getForm().getTranslateX() == 0.5 && this.testSprite.getForm().getTranslateY() == 0);
    }
    
    @Test
    public void checkCollisionDoesntCollideWithItself() {
        assertFalse(this.testSprite.checkCollision(this.testSprite.getForm()));
    }
}

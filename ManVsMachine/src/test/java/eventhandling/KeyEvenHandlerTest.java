package eventhandling;

import mvsm.eventhandling.KeyEventHandler;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyEvenHandlerTest {

    private KeyEventHandler testHandler;
    private ArrayList<KeyCode> keycodes;

    public KeyEvenHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.testHandler = new KeyEventHandler();
        this.keycodes = this.testHandler.getKeyCodes();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void keyCodesNotNull() {
        assertTrue(this.testHandler.getKeyCodes() != null);
    }

    @Test
    public void keyCodeAddedOnKeyPressed() throws Exception {
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
        if (this.keycodes.contains(KeyCode.UP) && this.keycodes.contains(KeyCode.RIGHT)) {
            return;
        }
        fail("KeyListener doesn't register keypress, KeyCodeList size should be 2 but is: " + this.testHandler.getKeyCodes().size());
    }

    @Test
    public void keyCodeRemovedOnKeyPressed() throws Exception {
        
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_RELEASED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
        if (!this.keycodes.contains(KeyCode.UP) && this.keycodes.contains(KeyCode.RIGHT) && !this.keycodes.contains(KeyCode.W)) {
            return;
        }
        fail("KeyListener doesn't remove KeyCodes on key released. KeyCodeList size should be 1 but was: " + this.keycodes.size());
    }
    
    @Test
    public void noIdenticalKeyCodesAllowedTest() {
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.UP, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.RIGHT, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        this.testHandler.handle(new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.CHAR_UNDEFINED, KeyEvent.CHAR_UNDEFINED, KeyCode.W, false, false, false, false));
        if (this.keycodes.size() == 3) {
            return;
        }
        fail("Listener allows duplicates!");
    }
}

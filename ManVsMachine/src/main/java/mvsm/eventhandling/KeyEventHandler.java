package mvsm.eventhandling;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Class for handling key events. Stores the keys being pressed in an ArrayList.
 * Used mainly by the GamePhysics class.
 *
 * @see mvsm.game.GamePhysics
 */
public class KeyEventHandler implements EventHandler<KeyEvent> {

    private final ArrayList<KeyCode> keyCodes;

    public KeyEventHandler() {
        this.keyCodes = new ArrayList<>();
    }

    @Override
    public void handle(KeyEvent t) {
        if (t.getEventType() == KeyEvent.KEY_PRESSED) {
            if (!this.keyCodes.contains(t.getCode())) {
                this.keyCodes.add(t.getCode());
            }
        }
        if (t.getEventType() == KeyEvent.KEY_RELEASED) {
            this.keyCodes.remove(t.getCode());
        }
    }

    public ArrayList<KeyCode> getKeyCodes() {
        return keyCodes;
    }

}

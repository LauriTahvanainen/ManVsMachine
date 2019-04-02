
package manvsmachine.game;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.event.HyperlinkEvent;


public class KeyEventHandler implements EventHandler<KeyEvent>{
    private ArrayList<KeyCode> keyCodes;
    

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

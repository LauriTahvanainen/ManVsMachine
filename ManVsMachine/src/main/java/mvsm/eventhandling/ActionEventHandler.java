package mvsm.eventhandling;

import mvsm.statemanagement.StateManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Class for handling ActionEvents, mainly button-presses. Ignores presses on
 * Text- or PasswordFields. Always relays the ActionEvent to the current state
 * in the game with the help of the StateManager.
 *
 * @see mvsm.statemanagement.State#handleAction(javafx.event.ActionEvent)
 */
public class ActionEventHandler implements EventHandler<ActionEvent> {

    private final StateManager gameStateManager;

    public ActionEventHandler(StateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handle(ActionEvent t) {
        if (!t.getTarget().getClass().equals(TextField.class) && !t.getTarget().getClass().equals(PasswordField.class)) {
            this.gameStateManager.getCurrentState().handleAction(t);
        }
    }

}

package mvsm.eventhandling;

import mvsm.statemanagement.StateManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ActionEventHandler implements EventHandler<ActionEvent> {

    private StateManager gameStateManager;

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


package eventhandling;

import stateManagement.StateManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ActionEventHandler implements EventHandler<ActionEvent> {
    private StateManager gameStateManager;

    public ActionEventHandler(StateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handle(ActionEvent t) {
        this.gameStateManager.getCurrentState().handleAction(t);
    }

}

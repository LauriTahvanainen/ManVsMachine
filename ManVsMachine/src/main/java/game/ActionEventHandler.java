
package manvsmachine.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ActionEventHandler implements EventHandler<ActionEvent> {
    private GameStateManager gameStateManager;

    public ActionEventHandler(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handle(ActionEvent t) {
        this.gameStateManager.getCurrentState().handleAction(t);
    }

}


package stateManagement.game;

import javafx.animation.AnimationTimer;
import stateManagement.StateManager;


public class GameLoop extends AnimationTimer {
    private StateManager gameStatemanager;

    public GameLoop(StateManager gameStatemanager) {
        this.gameStatemanager = gameStatemanager;
    }
    
    @Override
    public void handle(long l) {
        this.gameStatemanager.stateUpdate();
    }
    
}

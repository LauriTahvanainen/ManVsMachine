
package manvsmachine.uilogic;

import javafx.animation.AnimationTimer;
import manvsmachine.uilogic.GameStateManager;


public class GameLoop extends AnimationTimer {
    private GameStateManager gameStatemanager;

    public GameLoop(GameStateManager gameStatemanager) {
        this.gameStatemanager = gameStatemanager;
    }
    
    @Override
    public void handle(long l) {
        this.gameStatemanager.stateUpdate();
    }
    
}

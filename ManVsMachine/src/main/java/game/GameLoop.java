
package game;

import javafx.animation.AnimationTimer;
import game.GameStateManager;


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


package manvsmachine.uilogic;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;
    private Scene scene;
    private GameLoop gl;

    public GameStateManager() {
        this.gameStates = new ArrayList<>();
        this.currentState = 0;
    }
    
    public void setGameLoop(GameLoop gl) {
        this.gl = gl;
    }
    
    public void stateUpdate() {
        this.gameStates.get(currentState).update();
    }
    
    public void addState(GameState state) {
        this.gameStates.add(state);
    }
    
    public GameState getCurrentState() {
        return this.gameStates.get(currentState);
    }
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setSceneRoot(Pane pane) {
        this.scene.setRoot(pane);
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    public void setCurrentState(int s) {
        this.currentState = s;
    }
            
    public void startLoop() {
        this.gl.start();
    }
    
    public void stopLoop() {
        this.gl.stop();
    }
    
}


package statemanagement;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import statemanagement.game.GameLoop;

public class StateManager {
    private final ArrayList<State> gameStates;
    private int currentState;
    private Scene scene;
    private GameLoop gl;
    private String currentUser;

    public StateManager() {
        this.gameStates = new ArrayList<>();
        this.currentState = 0;
        this.currentUser = "";
    }
    
    public void setGameLoop(GameLoop gl) {
        this.gl = gl;
    }
    
    public void stateUpdate() {
        this.gameStates.get(currentState).update();
    }
    
    public void addState(State state) {
        this.gameStates.add(state);
    }
    
    public State getCurrentState() {
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

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    
    
}

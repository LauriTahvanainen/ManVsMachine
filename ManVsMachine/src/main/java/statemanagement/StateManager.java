
package statemanagement;

import dao.User;
import java.util.ArrayList;
import java.util.Properties;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class StateManager extends AnimationTimer {
    private final ArrayList<State> gameStates;
    private int currentState;
    private Scene scene;
    private User currentUser;
    private Properties properties;

    public StateManager(Properties properties) {
        this.gameStates = new ArrayList<>();
        this.currentState = 0;
        this.currentUser = null;
        this.properties = properties;
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
        this.start();
    }
    
    public void stopLoop() {
        this.stop();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void handle(long l) {
        stateUpdate();
    }
    
    
}

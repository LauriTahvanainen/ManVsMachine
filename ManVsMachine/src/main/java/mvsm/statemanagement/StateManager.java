package mvsm.statemanagement;

import java.util.ArrayList;
import java.util.Properties;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import mvsm.dao.User;

/**
 * Handles the management and updating of different GameStates. Extends
 * AnimationTimer.
 */
public class StateManager extends AnimationTimer {

    private final ArrayList<State> gameStates;
    private int currentState;
    private Scene scene;
    private User currentUser;
    private Properties properties;

    /**
     * The constructor takes the properties downloaded from the
     * config.properties file as an argument. Creates an empty State-ArrayList
     * and sets the current state to be at index 0, and the current user to be
     * null.
     *
     * @param properties user customizable properties e.g. database path.
     */
    public StateManager(Properties properties) {
        this.gameStates = new ArrayList<>();
        this.currentState = 0;
        this.currentUser = null;
        this.properties = properties;
    }

    /**
     * Adds the state given as parameter to the ArrayList gameStates.
     *
     * @param state to add to gameStates.
     */
    public void addState(State state) {
        this.gameStates.add(state);
    }

    /**
     * Gets the current state.
     *
     * @return The current state determined by the index-variable currentState.
     */
    public State getCurrentState() {
        return this.gameStates.get(currentState);
    }

    /**
     * Sets the Scene that's root the manager will change with, the
     * setSceneRoot(Pane pane) method.
     *
     * @param scene to be handled by the manager.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the root of the scene. Completes a state change.
     *
     * @param pane to be set as the Scenes root.
     */
    public void setSceneRoot(Pane pane) {
        this.scene.setRoot(pane);
    }

    public Scene getScene() {
        return this.scene;
    }

    /**
     * Set the current state-index.
     *
     * @param s index pointing to the scene wanted to be the current scene.
     */
    public void setCurrentState(int s) {
        this.currentState = s;
    }

    /**
     * Calls the AnimationTimer method start();
     *
     * @see javafx.animation.AnimationTimer
     */
    public void startLoop() {
        this.start();
    }

    /**
     * Calls the AnimationTimer method stop();
     *
     * @see javafx.animation.AnimationTimer
     */
    public void stopLoop() {
        this.stop();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Overridden method of AnimationTimer. When AnimationTimer is active, this
     * method is called in every frame.
     *
     * Gets the current State from the gameStates ArrayList determined by the
     * index-variable currentState. Then calls the update method of the given
     * state. See for example:
     *
     * @see mvsm.ui.PlayingState#update()
     *
     * @param l
     */
    @Override
    public void handle(long l) {
        this.gameStates.get(currentState).update();
    }

}

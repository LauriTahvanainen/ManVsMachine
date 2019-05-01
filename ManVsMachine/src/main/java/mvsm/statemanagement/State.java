package mvsm.statemanagement;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import mvsm.algorithm.Algorithm;

/**
 * Abstract class for defining the different states in the game. States are
 * handled by the StateManager class. States define the UI of the game.
 *
 * @see mvsm.statemanagement.StateManager
 */
public abstract class State {

    /**
     * Returns the id of the current state. For management purposes.
     *
     * @return The Integer id of the state.
     */
    public abstract int getStateId();

    /**
     * Return the current Pane of the state that should be visible. The changing
     * of visuals to the player is done in the game by changing the root-pane of
     * one scene object.
     *
     * @return The current Pane of the state. E.g. the background GridPane in
     * the case of the PlayingState.
     * @see mvsm.ui.Main
     */
    public abstract Pane getCurrent();

    /**
     * Used to update the State with the repeated calls by the StateManagers
     * handle method.
     *
     * @see mvsm.statemanagement.StateManager#handle(long)
     */
    public abstract void update();

    /**
     * Initializes the building blocks of each State, e.g. set the style of
     * buttons.
     */
    public abstract void initPane();

    /**
     * Handle ActionEvents that happened in the State while the State was the
     * current State of the StateManager.
     *
     * @param t The ActionEvent to handle.
     * @see
     * mvsm.eventhandling.ActionEventHandler#handle(javafx.event.ActionEvent)
     */
    public abstract void handleAction(ActionEvent t);

    /**
     * Restore the State to the state specified in the code. For example this
     * method is called in the PlayingState when the game is restarted.
     *
     * @see mvsm.ui.PlayingState#restore()
     */
    public abstract void restore();

    /**
     * Used only by the PlayingState in starting a new game.
     *
     * @param a The Algorithm to play against.
     * @param mapName The name of the map to load.
     * @see mvsm.ui.PlayingState#restore(mvsm.algorithm.Algorithm,
     * java.lang.String)
     */
    public abstract void restore(Algorithm a, String mapName);

}

package statemanagement;

import algorithm.Algorithm;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;

public abstract class State {

    public abstract int getStateId();

    public abstract Pane getCurrent();

    public abstract void update();

    public abstract void initPane();

    public abstract void handleAction(ActionEvent t);

    public abstract void restore();

    public abstract void restore(Algorithm a, String mapName);

}

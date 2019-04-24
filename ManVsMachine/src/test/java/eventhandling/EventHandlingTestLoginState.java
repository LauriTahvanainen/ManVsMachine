package eventhandling;

import mvsm.algorithm.Algorithm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;

public class EventHandlingTestLoginState extends State {

    private GridPane pane;
    private Button button;
    private TextField field;
    private final StateManager gsm;

    public EventHandlingTestLoginState(Button button, TextField field, StateManager sm) {
        this.gsm = sm;
        this.button = button;
        this.field = field;
        this.pane = new GridPane();
        this.pane.add(field, 0, 0);
        this.pane.add(button, 0, 1);
    }

    @Override
    public int getStateId() {
        return 0;
    }

    @Override
    public Pane getCurrent() {
        return this.pane;
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {

    }

    @Override
    public void handleAction(ActionEvent t) {
        if (t.getTarget().getClass().equals(TextField.class)) {
            this.button.setText("Texfield press got through");
        }
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign in")) {
            this.gsm.setCurrentState(1);
        }
    }

    @Override
    public void restore() {

    }

    @Override
    public void restore(Algorithm a, String map) {

    }

}

package eventhandling;

import algorithm.Algorithm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import statemanagement.State;
import statemanagement.StateManager;

public class EventHandlingTestMenuState extends State {
    private GridPane pane;
    private Button button;
    private TextField field;
    private final StateManager gsm;

    public EventHandlingTestMenuState(Button button, TextField field, StateManager sm) {
        this.gsm = sm;
        this.button = button;
        this.field = field;
        this.pane = new GridPane();
        this.pane.add(field, 0, 0);
        this.pane.add(button, 0, 1);
    }
    @Override
    public int getStateId() {
        return 1;
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
        this.field.setText("This should not happen");
        if (t.getTarget().getClass().equals(TextField.class)) {
            this.button.setText("Textfield press got through");
        }
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign out")) {
            this.gsm.setCurrentState(0);
        }
    }

    @Override
    public void restore() {

    }

    @Override
    public void restore(Algorithm a, int[][] map) {

    }

}

package helpers;

import mvsm.algorithm.Algorithm;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;

public class FakeLoginState extends State {

    private GridPane pane;
    private Button button;
    private TextField field;
    private PasswordField pwField;
    private final StateManager gsm;
    private long number;

    public FakeLoginState(Button button, TextField field, StateManager sm, PasswordField pwF) {
        this.gsm = sm;
        this.button = button;
        this.field = field;
        this.pwField = pwF;
        this.pane = new GridPane();
        this.pane.add(field, 0, 0);
        this.pane.add(pwField, 0, 1);
        this.pane.add(button, 0, 2);
        this.number = 0;

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
        number++;
    }

    @Override
    public void initPane() {

    }

    @Override
    public void handleAction(ActionEvent t) {
        if (t.getTarget().getClass().equals(TextField.class)) {
            this.button.setText("Texfield press got through");
        }
        if (t.getTarget().getClass().equals(PasswordField.class)) {
            this.button.setText("Passwordfield press got through");
        }
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign in")) {
            this.gsm.setCurrentState(1);
        }
    }

    @Override
    public void restore() {
        this.field.setText("Restored!");
    }

    @Override
    public void restore(Algorithm a, String map) {

    }

    public long getNumber() {
        return number;
    }

}

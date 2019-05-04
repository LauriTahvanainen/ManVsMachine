package eventhandling;

import helpers.FakeLoginState;
import helpers.FakeMenuState;
import mvsm.eventhandling.ActionEventHandler;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.Test;
import static org.junit.Assert.*;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;

public class ActionEventHandlerTest {

    private final StateManager testManager;
    private final ActionEventHandler testHandler;
    private final State testLoginState;
    private final State testMenuState;
    private final Button loginButton;
    private final Button menuButton;
    private final TextField loginTextField;
    private final TextField menuTextField;
    private final PasswordField pwField;

    public ActionEventHandlerTest() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
        this.loginButton = new Button("Sign in");
        this.menuButton = new Button("Sign out");
        this.loginTextField = new TextField();
        this.menuTextField = new TextField();
        this.pwField = new PasswordField();
        this.testManager = new StateManager(new Properties());
        this.testLoginState = new FakeLoginState(this.loginButton, this.loginTextField, this.testManager, this.pwField);
        this.testMenuState = new FakeMenuState(this.menuButton, this.menuTextField, this.testManager);
        this.testManager.addState(this.testLoginState);
        this.testManager.addState(this.testMenuState);
        this.testHandler = new ActionEventHandler(this.testManager);
    }

    @Test
    public void eventHandlerStopsEventsFromTextFields() {
        this.testHandler.handle(new ActionEvent(this.loginTextField, this.loginTextField));
        if (this.loginButton.getText().equals("Sign in")) {
            return;
        }
        fail("ActionEventHandler doesn't stop texfield events from going through to the statemanager");
    }

    @Test
    public void eventHandlerStopsEventsFromPasswordFields() {
        this.testHandler.handle(new ActionEvent(this.pwField, this.pwField));
        if (this.loginButton.getText().equals("Sign in")) {
            return;
        }
        fail("ActionEventHandler doesn't stop texfield events from going through to the statemanager");
    }

    @Test
    public void handlingGoesToRightState() {
        this.testHandler.handle(new ActionEvent(this.loginButton, this.loginButton));
        if (this.testManager.getCurrentState().equals(this.testMenuState) && this.menuTextField.getText().equals("")) {
            return;
        }
        fail("Handling doesn't use the right states handle method. Expected that Statemanagers current state would be " + this.testMenuState + " but was: " + this.testManager.getCurrentState());
    }

}

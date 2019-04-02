package manvsmachine.uilogic;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import manvsmachine.uilogic.GameState;

public final class LoginState extends GameState {

    private final int stateId;
    private Pane currentPane;
    private final Pane root;
    private final Pane signInPane;
    private final Pane createAccountPane;
    private GameStateManager gsm;

    public LoginState(GameStateManager gsm) {
        this.gsm = gsm;
        this.root = new HBox();
        this.root.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.stateId = 0;
        this.signInPane = new VBox();
        this.signInPane.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.createAccountPane = new VBox();
        this.createAccountPane.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.currentPane = this.root;
        initPane();
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        //first view
        Button signIn = new Button("Sign in with an existing account");
        Button createNewAccount = new Button("Create a new account");
        root.getChildren().addAll(signIn, createNewAccount);

        //sign in view
        Button signIn2 = new Button("Sign in");
        TextField username = new TextField();
        Button returnB1 = new Button("Return");
        this.signInPane.getChildren().addAll(username, signIn2, returnB1);

        //create account view
        Button returnB2 = new Button("Return");
        TextField newUsername = new TextField();
        Button createAccount = new Button("Create account");
        this.createAccountPane.getChildren().addAll(newUsername, createAccount, returnB2);
    }

    @Override
    public void handleAction(ActionEvent t) {
        if (this.currentPane.equals(this.signInPane)) {
            handleSignInView(t);
            return;
        }
        if (this.currentPane.equals(this.createAccountPane)) {
            handleCreateAccountView(t);
            return;
        }
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign in with an existing account")) {
            this.currentPane = this.signInPane;
            this.gsm.setSceneRoot(this.signInPane);
        } else {
            this.currentPane = this.createAccountPane;
            this.gsm.setSceneRoot(this.createAccountPane);
        }
    }

    @Override
    public int getStateId() {
        return this.stateId;
    }

    @Override
    public Pane getCurrent() {
        return this.currentPane;
    }

    private void handleSignInView(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign in")) {
            this.gsm.setCurrentState(1);
            this.gsm.setSceneRoot(this.gsm.getCurrentState().getCurrent());
            this.currentPane = this.root;
        } else {
            this.currentPane = this.root;
            this.gsm.setSceneRoot(this.currentPane);
        }
    }

    private void handleCreateAccountView(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Create account")) {
            this.gsm.setCurrentState(1);
            this.gsm.setSceneRoot(this.gsm.getCurrentState().getCurrent());
            this.currentPane = this.root;
        } else {
            this.currentPane = this.root;
            this.gsm.setSceneRoot(this.currentPane);
        }
    }

    @Override
    public void restore() {
        
    }

}

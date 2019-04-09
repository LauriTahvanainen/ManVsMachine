package ui;

import algorithm.Algorithm;
import dao.UserDao;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import statemanagement.State;
import statemanagement.StateManager;

public final class LoginState extends State {

    private final int stateId;
    private Pane currentPane;
    private final HBox root;
    private final VBox signInPane;
    private final VBox createAccountPane;
    private final StateManager gsm;
    private final UserDao userDao;

    public LoginState(StateManager gsm, UserDao userDao) {
        this.gsm = gsm;
        this.root = new HBox();
        this.root.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.stateId = 0;
        this.signInPane = new VBox();
        this.signInPane.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.createAccountPane = new VBox();
        this.createAccountPane.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.currentPane = this.root;
        this.userDao = userDao;
        initPane();
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        //first view
        root.setAlignment(Pos.CENTER);
        Button signIn = new Button("Sign in with an existing account");
        Button createNewAccount = new Button("Create a new account");
        root.getChildren().addAll(signIn, createNewAccount);

        //sign in view
        signInPane.setAlignment(Pos.CENTER);
        Button signIn2 = new Button("Sign in");
        TextField username = new TextField();
        username.setMaxWidth(200);
        Button returnB1 = new Button("Return");
        Text text = new Text("");
        this.signInPane.getChildren().addAll(username, signIn2, returnB1, text);

        //create account view
        createAccountPane.setAlignment(Pos.CENTER);
        Button returnB2 = new Button("Return");
        TextField newUsername = new TextField();
        newUsername.setMaxWidth(200);
        Button createAccount = new Button("Create account");
        Text text2 = new Text();
        this.createAccountPane.getChildren().addAll(newUsername, createAccount, returnB2, text2);
    }

    @Override
    public void handleAction(ActionEvent t) {
        if (this.currentPane.equals(this.signInPane)) {
            handleSignInView(t, (Text) this.signInPane.getChildren().get(3), (TextField) this.signInPane.getChildren().get(0));
            return;
        }
        if (this.currentPane.equals(this.createAccountPane)) {
            handleCreateAccountView(t, (Text) this.createAccountPane.getChildren().get(3), (TextField) this.createAccountPane.getChildren().get(0));
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

    private void handleSignInView(ActionEvent t, Text errorText, TextField inputField) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Sign in")) {
            String username = null;
            try {
                username = this.userDao.read(inputField.getText());
                if (username != null) {
                    inputField.clear();
                    errorText.setText("");
                    this.gsm.setCurrentUser(username);
                    this.gsm.setCurrentState(1);
                    this.gsm.setSceneRoot(this.gsm.getCurrentState().getCurrent());
                    this.gsm.stateUpdate();
                    this.currentPane = this.root;
                } else {
                    inputField.clear();
                    errorText.setText("The username doesn't exist!");
                }
            } catch (SQLException ex) {
                errorText.setText("There was an unexpected error!");
            }

        } else {
            errorText.setText("");
            inputField.clear();
            this.currentPane = this.root;
            this.gsm.setSceneRoot(this.currentPane);
        }
    }

    private void handleCreateAccountView(ActionEvent t, Text errorText, TextField inputField) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Create account")) {
            int length = inputField.getText().trim().length();
            if (length < 17 && length > 3) {
                try {
                    if (this.userDao.create(inputField.getText())) {
                        errorText.setText("Username: '" + inputField.getText() + "' added!");
                        inputField.clear();
                    } else {
                        inputField.clear();
                        errorText.setText("The username is already taken!");
                    }
                } catch (SQLException ex) {
                    errorText.setText("Unexpected error!");
                    inputField.clear();
                }
            } else {
                inputField.clear();
                if (length < 4) {
                    errorText.setText("Given username too short!");
                } else {
                    errorText.setText("Given username too long!");
                }
            }
        } else {
            errorText.setText("");
            inputField.clear();
            this.currentPane = this.root;
            this.gsm.setSceneRoot(this.currentPane);
        }

    }

    @Override
    public void restore() {
    }

    @Override
    public void restore(Algorithm a, int[][] map) {
    }

}

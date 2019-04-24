package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.UserDao;
import mvsm.dao.User;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public final class LoginState extends State {

    private final BorderPane root;
    private final GridPane signInPane;
    private final VBox createAccountPane;
    private final StateManager gsm;
    private final UserDao userDao;
    private Text errorText1;
    private Text errorText2;
    private TextField username;
    private TextField newUsername;

    public LoginState(StateManager gsm, UserDao userDao) {
        this.gsm = gsm;
        this.root = new BorderPane();
        this.root.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.signInPane = new GridPane();
        this.root.setCenter(this.signInPane);
        this.createAccountPane = new VBox();
        this.userDao = userDao;
        initPane();
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        //sign in view
        this.signInPane.setAlignment(Pos.CENTER);
        Button quit = new Button("Quit");
        Button signIn = new Button("Sign in");
        Button createNewAccount = new Button("Create new account");
        createNewAccount.setPrefWidth(210);
        createNewAccount.setTextAlignment(TextAlignment.CENTER);
        Text usernameText = new Text("Username");
        Text passwordText = new Text("Password");
        this.errorText1 = new Text();
        this.username = new TextField();
        PasswordField password = new PasswordField();
        username.setMaxWidth(210);
        username.setPromptText("Username");
        password.setMaxWidth(210);
        password.setPromptText("Password");
        this.signInPane.add(errorText1, 1, 0);
        this.signInPane.addRow(1, usernameText, username);
        this.signInPane.addRow(2, passwordText, password);
        this.signInPane.addRow(3, signIn, createNewAccount, quit);
        

        //create account view
        createAccountPane.setAlignment(Pos.CENTER);
        Button returnB = new Button("Return");
        returnB.setPrefWidth(210);
        returnB.setTextAlignment(TextAlignment.CENTER);
        this.newUsername = new TextField();
        PasswordField newPassword1 = new PasswordField();
        PasswordField newPassword2 = new PasswordField();
        newUsername.setMaxWidth(210);
        newUsername.setPromptText("New username");
        newPassword1.setMaxWidth(210);
        newPassword1.setPromptText("New password");
        newPassword2.setMaxWidth(210);
        newPassword2.setPromptText("Confirm new password");
        Button createAccount = new Button("Create account");
        createAccount.setPrefWidth(210);
        createAccount.setTextAlignment(TextAlignment.CENTER);
        this.errorText2 = new Text();
        this.createAccountPane.getChildren().addAll(newUsername, newPassword1, newPassword2, createAccount, returnB, errorText2);
    }

    @Override
    public void handleAction(ActionEvent t) {
        if (this.root.getCenter().equals(this.signInPane)) {
            handleSignInView(t);
            return;
        }
        if (this.root.getCenter().equals(this.createAccountPane)) {
            handleCreateAccountView(t);
        }
    }

    @Override
    public int getStateId() {
        return 0;
    }

    @Override
    public Pane getCurrent() {
        return this.root;
    }

    private void handleSignInView(ActionEvent t) {
        Button button = (Button) t.getTarget();
        String text = button.getText();
        if (text.equals("Sign in")) {
            User user;
            try {
                user = this.userDao.read(this.username.getText());
                if (user != null) {
                    this.username.clear();
                    this.errorText1.setText("");
                    this.gsm.setCurrentUser(user);
                    this.gsm.setCurrentState(1);
                    this.gsm.setSceneRoot(this.gsm.getCurrentState().getCurrent());
                    this.gsm.getCurrentState().restore();
                } else {
                    this.username.clear();
                    this.errorText1.setText("The username doesn't exist!");
                }
            } catch (SQLException ex) {
                errorText1.setText("There was an unexpected error!");
            }

        } else if (text.equals("Create new account")) {
            errorText1.setText("");
            username.clear();
            this.root.setCenter(this.createAccountPane);
        } else {
            Platform.exit();
        }
    }

    private void handleCreateAccountView(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Create account")) {
            try {
                int ret = this.userDao.create(this.newUsername.getText());
                reactToUsernameCreation(ret, this.newUsername.getText());
                newUsername.clear();
            } catch (SQLException ex) {
                errorText2.setText("Unexpected error!");
                newUsername.clear();
            }
        } else {
            errorText2.setText("");
            newUsername.clear();
            this.root.setCenter(this.signInPane);
        }

    }

    @Override
    public void restore() {
    }

    @Override
    public void restore(Algorithm a, String map) {
    }

    private void reactToUsernameCreation(int ret, String username) {
        if (ret == 1) {
            errorText2.setText("Username: '" + username + "' added!");
        } else if (ret == 0) {
            errorText2.setText("The username is already taken!");
        } else if (ret == 3) {
            errorText2.setText("Given username contains a space!");
        } else if (ret == 4) {
            errorText2.setText("Given username too short!");
        } else {
            errorText2.setText("Given username too long!");
        }
    }

}

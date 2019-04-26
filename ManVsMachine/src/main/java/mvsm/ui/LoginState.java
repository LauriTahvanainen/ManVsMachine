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
import mvsm.dao.StringChecker;

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
    private PasswordField newPassword1;
    private PasswordField newPassword2;
    private final StringChecker checker;
    private PasswordField password;

    public LoginState(StateManager gsm, UserDao userDao) {
        this.gsm = gsm;
        this.root = new BorderPane();
        this.root.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        this.signInPane = new GridPane();
        this.root.setCenter(this.signInPane);
        this.createAccountPane = new VBox();
        this.userDao = userDao;
        this.checker = new StringChecker();
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
        Button signIn = new Button("Sign In");
        Button createNewAccount = new Button("Create New Account");
        createNewAccount.setPrefWidth(210);
        createNewAccount.setTextAlignment(TextAlignment.CENTER);
        Text usernameText = new Text("Username");
        Text passwordText = new Text("Password");
        this.errorText1 = new Text();
        this.username = new TextField();
        this.password = new PasswordField();
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
        Button returnB = new Button("Back to Sign In");
        returnB.setPrefWidth(210);
        returnB.setTextAlignment(TextAlignment.CENTER);
        this.newUsername = new TextField();
        this.newPassword1 = new PasswordField();
        this.newPassword2 = new PasswordField();
        newUsername.setMaxWidth(210);
        newUsername.setPromptText("New Username");
        newPassword1.setMaxWidth(210);
        newPassword1.setPromptText("New Password");
        newPassword2.setMaxWidth(210);
        newPassword2.setPromptText("Confirm New Password");
        Button createAccount = new Button("Create Account");
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
        if (text.equals("Sign In")) {
            User user;
            try {
                user = this.userDao.read(this.username.getText());
                if (user == null) {
                    this.username.clear();
                    this.password.clear();
                    this.errorText1.setText("The username doesn't exist!");
                } else if (this.password.getText().hashCode() != user.getPassword()) {
                    this.username.clear();
                    this.password.clear();
                    this.errorText1.setText("Wrong password!");
                } else {
                    this.username.clear();
                    this.password.clear();
                    this.errorText1.setText("");
                    this.gsm.setCurrentUser(user);
                    this.gsm.setCurrentState(1);
                    this.gsm.setSceneRoot(this.gsm.getCurrentState().getCurrent());
                    this.gsm.getCurrentState().restore();
                }
            } catch (SQLException ex) {
                errorText1.setText("There was an unexpected error!");
            }

        } else if (text.equals("Create New Account")) {
            errorText1.setText("");
            username.clear();
            this.password.clear();
            this.root.setCenter(this.createAccountPane);
        } else {
            Platform.exit();
        }
    }

    private void handleCreateAccountView(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Create Account")) {
            int pWCheck = this.checker.checkPassword(this.newPassword1.getText(), this.newPassword2.getText());
            if (pWCheck == 0) {
                try {
                    int ret = this.userDao.create(this.newUsername.getText(), this.newPassword1.getText());
                    reactToUsernameCreation(ret, this.newUsername.getText());
                } catch (SQLException ex) {
                    errorText2.setText("Unexpected error!");
                    newUsername.clear();
                    this.newPassword1.clear();
                    this.newPassword2.clear();
                }
            } else {
                reactToPasswordCheck(pWCheck);
                this.newUsername.clear();
                this.newPassword1.clear();
                this.newPassword2.clear();
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
        newUsername.clear();
        this.newPassword1.clear();
        this.newPassword2.clear();
    }

    private void reactToPasswordCheck(int ret) {
        if (ret == 1) {
            errorText2.setText("The given passwords do not match!");
        } else if (ret == 6) {
            errorText2.setText("The given password is too short!");
        } else if (ret == 16) {
            errorText2.setText("The given password is too long!");
        } else if (ret == 2) {
            errorText2.setText("The given password contains illegal characters!");
        } else if (ret == 3) {
            errorText2.setText("The given password does not contain a number!");
        } else if (ret == 4) {
            errorText2.setText("The given password does not contain a capital letter!");
        } else {
            errorText2.setText("The given password does not contain a lowercase letter!");
        }
    }

}

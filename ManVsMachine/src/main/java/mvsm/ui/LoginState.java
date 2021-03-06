package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.UserDao;
import mvsm.dao.User;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import mvsm.dao.StringChecker;

/**
 * State for login actions. Uses the class StringChecker for checking validity
 * of input strings.
 *
 * @see mvsm.dao.StringChecker
 */
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
    private TextField passwordVisible;
    private Button setPwVisible1;
    private static final String RESOURCE_PATH = "/pictures/";
    private final ImageView eyeClosed = new ImageView(new Image(LoginState.class.getResourceAsStream(RESOURCE_PATH + "PwEye.png")));
    private final ImageView eyeOpen = new ImageView(new Image(LoginState.class.getResourceAsStream(RESOURCE_PATH + "PwEyeOpen.png")));

    /**
     *
     * @param gsm For state management and playing music.
     * @param userDao For checking for sign in and for creating new users.
     */
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
        //graphics
        root.setStyle("-fx-background-color: black;");
        signInPane.setStyle("-fx-background-color: black;");

        //sign in view
        this.signInPane.setAlignment(Pos.CENTER);
        Button quit = new Button("Quit");
        quit.setPrefWidth(450);
        setPwVisible1 = new Button(null, eyeOpen);
        GridPane.setHalignment(setPwVisible1, HPos.CENTER);
        GridPane.setValignment(setPwVisible1, VPos.CENTER);
        Button signIn = new Button("Sign In");
        signIn.setPrefWidth(450);
        Button createNewAccount = new Button("Create New Account");
        createNewAccount.setPrefWidth(450);
        createNewAccount.setTextAlignment(TextAlignment.CENTER);
        Text usernameText = new Text("Username");
        Text passwordText = new Text("Password");
        this.errorText1 = new Text();
        //timer for the errortext
        this.errorText1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!errorText1.getText().equals("")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> errorText1.setText(""));
                    delay.play();
                }
            }

        });
        this.errorText1.getStyleClass().add("text-id");
        this.username = new TextField();
        this.password = new PasswordField();
        this.passwordVisible = new TextField();
        username.setPromptText("Username");
        password.setPromptText("Password");
        this.passwordVisible.setVisible(false);
        this.passwordVisible.setPromptText("Password");
        this.signInPane.setVgap(5);
        this.signInPane.add(errorText1, 1, 0);
        this.signInPane.addRow(1, usernameText, username);
        this.signInPane.addRow(2, passwordText, password, setPwVisible1);
        this.signInPane.add(this.passwordVisible, 1, 2);
        this.signInPane.addRow(3, new Text(""), signIn);
        this.signInPane.addRow(4, new Text(""), createNewAccount);
        this.signInPane.addRow(5, new Text(""), quit);
        this.signInPane.setHgap(5);

        //create account view
        createAccountPane.setAlignment(Pos.CENTER);
        createAccountPane.setSpacing(5);
        Button returnB = new Button("Back to Sign In");
        returnB.setPrefWidth(450);
        returnB.setTextAlignment(TextAlignment.CENTER);
        this.newUsername = new TextField();
        this.newPassword1 = new PasswordField();
        this.newPassword2 = new PasswordField();
        newUsername.setPromptText("New Username");
        newPassword1.setPromptText("New Password");
        newPassword2.setPromptText("Confirm New Password");
        Button createAccount = new Button("Create Account");
        createAccount.setPrefWidth(450);
        createAccount.setTextAlignment(TextAlignment.CENTER);
        this.errorText2 = new Text();
        //Timer for the errorText;
        this.errorText2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!errorText2.getText().equals("")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> errorText2.setText(""));
                    delay.play();
                }
            }

        });
        this.errorText2.getStyleClass().add("text-id");
        this.createAccountPane.getChildren().addAll(errorText2, newUsername, newPassword1, newPassword2, createAccount, returnB);
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
        if (button.getText() == null) {
            handleSetPwFieldVisible();
            return;
        }
        String text = button.getText();
        if (text.equals("Sign In")) {
            if (this.passwordVisible.isVisible()) {
                this.password.setText(this.passwordVisible.getText());
            }
            User user;
            try {
                user = this.userDao.read(this.username.getText());
                if (user == null) {
                    this.username.clear();
                    this.password.clear();
                    this.errorText1.setText("The username does not exist!");
                } else if (this.password.getText().hashCode() != user.getPassword()) {
                    this.username.clear();
                    this.password.clear();
                    this.errorText1.setText("Wrong password!");
                } else {
                    this.gsm.playMenuMusic();
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
            this.newPassword1.clear();
            this.newPassword2.clear();
            this.root.setCenter(this.signInPane);
        }

    }

    @Override
    public void restore() {
        this.passwordVisible.setText("");
        this.passwordVisible.setVisible(false);
        this.password.setDisable(false);
        this.setPwVisible1.setGraphic(this.eyeOpen);
        this.username.clear();
        this.password.clear();
        this.errorText1.setText("");
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

    private void handleSetPwFieldVisible() {
        Button button = (Button) this.signInPane.getChildren().get(5);
        if (this.passwordVisible.isVisible()) {
            this.password.setText(this.passwordVisible.getText());
            button.setGraphic(this.eyeOpen);
            this.password.setDisable(false);
            this.passwordVisible.setDisable(true);
            this.passwordVisible.setVisible(false);
        } else {
            this.passwordVisible.setText(this.password.getText());
            this.password.setDisable(true);
            button.setGraphic(this.eyeClosed);
            this.passwordVisible.setDisable(false);
            this.passwordVisible.setVisible(true);
        }
    }

}

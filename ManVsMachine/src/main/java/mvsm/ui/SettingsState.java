package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.UserDao;
import mvsm.dao.ScoreDao;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import mvsm.dao.StringChecker;

public class SettingsState extends State {

    private final StateManager stateM;
    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private final Button changeUsername;
    private final Button changeUsername2;
    private final Button changePassword1;
    private final Button changePassword2;
    private final Button customizeCharacter;
    private final Button backToMenu;
    private final Button cancel1;
    private final Button cancel2;
    private final Button cancelPwChange;
    private final Button changeColor;
    private final Button changeColor2;
    private final Rectangle currentColor;
    private final Rectangle newColor;
    private final TextField newUserNameInput;
    private final PasswordField newPasswordField;
    private final PasswordField newPasswordField2;
    private final Text errorText;
    private final Text errorText2;
    private final Text errorTextPwChange;
    private final ColorPicker colorPicker;
    private final VBox settingsPane;
    private final VBox changeUsernamePane;
    private final VBox changePasswordPane;
    private final BorderPane custCharPane;
    private final StackPane root;
    private final GridPane colorPickerPane;
    private final VBox custButtonPane;
    private final VBox colorPicButtons;
    private Pane currentPane;
    private final StringChecker checker;

    public SettingsState(StateManager stateM, UserDao userDao, ScoreDao scoreDao) {
        this.stateM = stateM;
        this.userDao = userDao;
        this.scoreDao = scoreDao;
        this.backToMenu = new Button("Back to Menu");
        this.checker = new StringChecker();

        //cancel buttons
        this.cancel1 = new Button("Back to Settings");
        this.cancel2 = new Button("Back to Settings");
        this.cancelPwChange = new Button("Back to Settings");

        //navigationbuttons
        this.changeUsername = new Button("Change Username");
        this.changePassword1 = new Button("Change Password");
        this.customizeCharacter = new Button("Customize Character");
        this.changeColor = new Button("Change Default Sprite Color");
        //activation buttons
        this.changeUsername2 = new Button("Change Username");
        this.changeColor2 = new Button("Change Color");
        this.changePassword2 = new Button("Change Password");

        //colorpicker view
        this.colorPicker = new ColorPicker(Color.WHITE);
        this.currentColor = new Rectangle(60, 60);
        this.newColor = new Rectangle(60, 60, Color.WHITE);
        this.settingsPane = new VBox();
        this.colorPickerPane = new GridPane();
        this.errorText2 = new Text();
        this.colorPicButtons = new VBox();

        //character cust view
        this.custCharPane = new BorderPane();
        this.custButtonPane = new VBox();

        //changepassword view
        this.changePasswordPane = new VBox();
        this.newPasswordField = new PasswordField();
        this.newPasswordField2 = new PasswordField();
        this.errorTextPwChange = new Text();

        //usernamechange view
        this.changeUsernamePane = new VBox();
        this.newUserNameInput = new TextField();
        this.newUserNameInput.setPrefWidth(210);
        this.errorText = new Text();

        this.root = new StackPane();
        initPane();
        this.currentPane = this.settingsPane;
    }

    @Override
    public int getStateId() {
        return 3;
    }

    @Override
    public Pane getCurrent() {
        return this.root;
    }

    @Override
    public void update() {
    }

    @Override
    public final void initPane() {
        this.root.getChildren().addAll(this.settingsPane, this.changeUsernamePane, this.custCharPane, this.changePasswordPane);
        this.root.setAlignment(Pos.CENTER);

        //settings view
        this.settingsPane.setAlignment(Pos.CENTER);
        this.settingsPane.setMaxSize(1200, 720);
        this.changeUsername.setPrefWidth(210);
        this.changePassword1.setPrefWidth(210);
        this.customizeCharacter.setPrefWidth(210);
        this.backToMenu.setPrefWidth(210);
        this.settingsPane.getChildren().addAll(this.changeUsername, this.changePassword1, this.customizeCharacter, this.backToMenu);

        //change username view
        this.changeUsernamePane.setAlignment(Pos.CENTER);
        this.changeUsernamePane.setMaxSize(1120, 640);
        this.changeUsernamePane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.8); -fx-background-radius: 1;");
        this.newUserNameInput.setMaxWidth(210);
        this.newUserNameInput.setPrefWidth(210);
        this.cancel1.setPrefWidth(210);
        this.changeUsername2.setPrefWidth(210);
        this.changeUsernamePane.getChildren().addAll(this.errorText, this.newUserNameInput, this.changeUsername2, this.cancel1);
        this.changeUsernamePane.setVisible(false);

        //change password view
        this.changePasswordPane.setAlignment(Pos.CENTER);
        this.changePasswordPane.setMaxSize(1120, 640);
        this.changePasswordPane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.8); -fx-background-radius: 1;");
        this.newPasswordField.setPromptText("New Password");
        this.newPasswordField2.setPromptText("Confirm New Password");
        this.newPasswordField.setPrefWidth(210);
        this.newPasswordField.setMaxWidth(210);
        this.newPasswordField2.setMaxWidth(210);
        this.cancelPwChange.setPrefWidth(210);
        this.changePassword2.setPrefWidth(210);
        this.cancelPwChange.setPrefWidth(210);
        this.changePasswordPane.getChildren().addAll(this.errorTextPwChange, this.newPasswordField, this.newPasswordField2, this.changePassword2, this.cancelPwChange);
        this.changePasswordPane.setVisible(false);

        //color picker view
        this.colorPickerPane.setAlignment(Pos.CENTER);
        this.colorPicButtons.setAlignment(Pos.CENTER);
        this.colorPicker.getStyleClass().add("button");
        this.colorPickerPane.addRow(1, new Text("Current Color"), new Text("New Color"));
        this.colorPickerPane.addRow(2, this.currentColor, this.newColor);
        this.colorPickerPane.addRow(3, this.colorPicker);
        this.colorPickerPane.addRow(4, this.changeColor2);

        //character customization
        this.custButtonPane.setAlignment(Pos.CENTER);
        this.custCharPane.setLeft(custButtonPane);
        this.custCharPane.setBottom(this.errorText2);
        this.errorText2.setFont(Font.font("alegreya", 20));

        custButtonPane.getChildren().addAll(this.cancel2, this.changeColor);
        this.custCharPane.setMaxSize(1120, 640);
        this.custCharPane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.8); -fx-background-radius: 1;");
        this.custCharPane.setVisible(false);
    }

    @Override
    public void handleAction(ActionEvent t) {
        if (t.getTarget().equals(this.colorPicker)) {
            this.newColor.setFill(this.colorPicker.getValue());
        }
        if (this.currentPane.equals(this.settingsPane)) {
            handleSettingsPane(t);
        }
        if (this.currentPane.equals(this.changeUsernamePane)) {
            handleUsernameChangePane(t);
        }
        if (this.currentPane.equals(this.changePasswordPane)) {
            handlePasswordChangePane(t);
        }
        handleCharacterCust(t);
    }

    @Override
    public void restore() {
        this.currentColor.setFill(this.stateM.getCurrentUser().getColor());
    }

    @Override
    public void restore(Algorithm a, String map) {

    }

    private void handleSettingsPane(ActionEvent t) {
        if (t.getTarget().equals(this.changeUsername)) {
            this.settingsPane.setDisable(true);
            this.currentPane = this.changeUsernamePane;
            this.changeUsernamePane.setVisible(true);
        } else if (t.getTarget().equals(this.backToMenu)) {
            this.stateM.setCurrentState(1);
            this.stateM.getCurrentState().restore();
            this.stateM.setSceneRoot(this.stateM.getCurrentState().getCurrent());
        } else if (t.getTarget().equals(this.changePassword1)) {
            this.settingsPane.setDisable(true);
            this.currentPane = this.changePasswordPane;
            this.changePasswordPane.setVisible(true);
        } else {
            this.settingsPane.setDisable(true);
            this.currentPane = this.custCharPane;
            this.custCharPane.setVisible(true);
        }
    }

    private void handleUsernameChangePane(ActionEvent t) {
        if (t.getTarget().equals(this.changeUsername2)) {
            try {
                int ret = this.userDao.update(this.stateM.getCurrentUser().getUsername(), this.newUserNameInput.getText());
                handleUsernameChangeRetVal(ret, this.newUserNameInput.getText());
            } catch (SQLException e) {
                this.errorText.setText("Unexpected error!");
                this.newUserNameInput.clear();
            }
            this.newUserNameInput.clear();
        }
        if (t.getTarget().equals(this.cancel1)) {
            this.currentPane = this.settingsPane;
            this.errorText.setText("");
            this.newUserNameInput.clear();
            this.settingsPane.setDisable(false);
            this.changeUsernamePane.setVisible(false);
        }
    }

    private void handleCharacterCust(ActionEvent t) {
        if (t.getTarget().equals(this.cancel2)) {
            this.currentPane = this.settingsPane;
            this.settingsPane.setDisable(true);
            this.custCharPane.setVisible(false);
            this.custCharPane.setCenter(null);
            this.errorText2.setText("");
            this.settingsPane.setDisable(false);
        } else if (t.getTarget().equals(this.changeColor)) {
            this.custCharPane.setCenter(this.colorPickerPane);
            this.custCharPane.setRight(this.colorPicButtons);
        } else if (t.getTarget().equals(this.changeColor2)) {
            Color toBeChangedTo = this.colorPicker.getValue();
            int red = (int) (toBeChangedTo.getRed() * 255);
            int green = (int) (toBeChangedTo.getGreen() * 255);
            int blue = (int) (toBeChangedTo.getBlue() * 255);
            try {
                this.userDao.updateColor(this.stateM.getCurrentUser().getUsername(), red, green, blue);
            } catch (SQLException e) {
                this.errorText2.setText("Unexpected error!");
                return;
            }
            this.currentColor.setFill(Color.rgb(red, green, blue));
            this.stateM.getCurrentUser().setColorM(Color.rgb(red, green, blue));
            this.errorText2.setText("Default Sprite Color Changed!");
        }
    }

    private void handlePasswordChangePane(ActionEvent t) {
        if (t.getTarget().equals(this.changePassword2)) {
            int ret = checker.checkPassword(this.newPasswordField.getText(), this.newPasswordField2.getText());
            if (ret != 0) {
                handlePasswordChangeRetVal(ret);
            } else {
                int ret2 = 1;
                try {
                    ret2 = this.userDao.updatePassword(this.stateM.getCurrentUser().getUsername(), this.newPasswordField.getText());
                } catch (SQLException e) {
                    this.errorTextPwChange.setText("There Was An Unexpected Error!");
                    this.newPasswordField.clear();
                    this.newPasswordField2.clear();
                    return;
                }
                if (ret2 == 1) {
                    this.errorTextPwChange.setText("Password Updated!");
                } else {
                    this.errorTextPwChange.setText("There Was An Unexpected Error!");
                }
                this.newPasswordField.clear();
                this.newPasswordField2.clear();
            }
        } else if (t.getTarget().equals(this.cancelPwChange)) {
            this.settingsPane.setDisable(false);
            this.currentPane = this.settingsPane;
            this.changePasswordPane.setVisible(false);
            this.errorTextPwChange.setText("");
            this.newPasswordField.clear();
            this.newPasswordField2.clear();
        }
    }

    private void handleUsernameChangeRetVal(int ret, String username) {
        if (ret == 1) {
            this.stateM.getCurrentUser().setUsername(username);
            this.errorText.setText("Username updated!");
        } else if (ret == 0) {
            this.errorText.setText("Given username has already been taken!");
        } else if (ret == 2) {
            this.errorText.setText("The new username is the same as the old one! Username not updated!");
        } else if (ret == 3) {
            this.errorText.setText("The new username contains a space! Username not updated!");
        } else if (ret == 4) {
            this.errorText.setText("The new username is too short! Username not updated!");
        } else if (ret == 16) {
            this.errorText.setText("The new username is too long! Username not updated!");
        }
    }

    private void handlePasswordChangeRetVal(int ret) {
        if (ret == 1) {
            errorTextPwChange.setText("The given passwords do not match!");
        } else if (ret == 6) {
            errorTextPwChange.setText("The given password is too short!");
        } else if (ret == 16) {
            errorTextPwChange.setText("The given password is too long!");
        } else if (ret == 2) {
            errorTextPwChange.setText("The given password contains illegal characters!");
        } else if (ret == 3) {
            errorTextPwChange.setText("The given password does not contain a number!");
        } else if (ret == 4) {
            errorTextPwChange.setText("The given password does not contain a capital letter!");
        } else {
            errorTextPwChange.setText("The given password does not contain a lowercase letter!");
        }
    }

}

package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.UserDao;
import mvsm.dao.ScoreDao;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mvsm.dao.StringChecker;

/**
 * State for implementing the features in the settings. e.g. Change username and
 * password and sprite color. Uses the class StringChecker for checking validity
 * of input strings.
 *
 * @see mvsm.dao.StringChecker
 */
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
    private final Button changePortalColor;
    private final Button changeSpriteTexture;
    private final Button changePortalColor2;
    private final Rectangle currentColor;
    private final Rectangle newColor;
    private final Rectangle currentTexture;
    private final TextField newUserNameInput;
    private final PasswordField newPasswordField;
    private final PasswordField newPasswordField2;
    private final Text errorText;
    private final Text errorTextCharCust;
    private final Text errorTextPwChange;
    private final ColorPicker colorPicker;
    private final VBox settingsPane;
    private final VBox changeUsernamePane;
    private final VBox changePasswordPane;
    private final BorderPane custCharPane;
    private final GridPane texturePane;
    private final StackPane root;
    private final GridPane colorPickerPane;
    private final VBox custButtonPane;
    private Pane currentPane;
    private final StringChecker checker;

    /**
     *
     * @param stateM For state management.
     * @param userDao For updating username.
     * @param scoreDao For updating username.
     */
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
        this.changePortalColor = new Button("Change Portal Color");
        this.changeSpriteTexture = new Button("Change Sprite Texture");
        //activation buttons
        this.changeUsername2 = new Button("Change Username");
        this.changePortalColor2 = new Button("Change Color");
        this.changePassword2 = new Button("Change Password");

        //textureChange view
        texturePane = new GridPane();
        this.currentTexture = new Rectangle(80, 80);

        //colorpicker view
        this.colorPicker = new ColorPicker(Color.WHITE);
        this.currentColor = new Rectangle(60, 60);
        this.newColor = new Rectangle(60, 60, Color.WHITE);
        this.settingsPane = new VBox();
        this.colorPickerPane = new GridPane();
        this.errorTextCharCust = new Text();
        this.errorTextCharCust.getStyleClass().add("text-id");
        this.errorTextCharCust.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!errorTextCharCust.getText().equals("")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                    delay.setOnFinished(event -> errorTextCharCust.setText(""));
                    delay.play();
                }
            }

        });

        //character cust view
        this.custCharPane = new BorderPane();
        this.custCharPane.setPadding(new Insets(20));
        this.custButtonPane = new VBox();

        //changepassword view
        this.changePasswordPane = new VBox();
        this.newPasswordField = new PasswordField();
        this.newPasswordField2 = new PasswordField();
        this.errorTextPwChange = new Text();
        this.errorTextPwChange.getStyleClass().add("text-id");
        this.errorTextPwChange.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!errorTextPwChange.getText().equals("")) {
                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                    delay.setOnFinished(event -> errorTextPwChange.setText(""));
                    delay.play();
                }
            }

        });

        //usernamechange view
        this.changeUsernamePane = new VBox();
        this.newUserNameInput = new TextField();
        this.newUserNameInput.setPrefWidth(210);
        this.errorText = new Text();
        this.errorText.getStyleClass().add("text-id");

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
        this.root.setStyle("-fx-background-color: black;");

        //settings view
        this.settingsPane.setAlignment(Pos.CENTER);
        this.settingsPane.setMaxSize(1200, 720);
        this.changeUsername.setPrefWidth(400);
        this.changePassword1.setPrefWidth(400);
        this.customizeCharacter.setPrefWidth(400);
        this.backToMenu.setPrefWidth(400);
        this.settingsPane.setSpacing(5);
        this.settingsPane.getChildren().addAll(this.changeUsername, this.changePassword1, this.customizeCharacter, this.backToMenu);

        //change username view
        this.changeUsernamePane.setAlignment(Pos.CENTER);
        this.changeUsernamePane.setSpacing(5);
        this.changeUsernamePane.setMaxSize(1120, 640);
        this.changeUsernamePane.setStyle("-fx-background-color: rgba(142, 143, 143, 0.8); -fx-background-radius: 1;");
        this.newUserNameInput.setPrefWidth(450);
        this.newUserNameInput.setPromptText("New Username");
        this.cancel1.setPrefWidth(450);
        this.changeUsername2.setPrefWidth(450);
        this.changeUsernamePane.getChildren().addAll(this.errorText, this.newUserNameInput, this.changeUsername2, this.cancel1);
        this.changeUsernamePane.setVisible(false);

        //texture change view
        this.texturePane.setAlignment(Pos.CENTER);
        this.texturePane.setVgap(10);
        this.texturePane.setHgap(10);

        //change password view
        this.changePasswordPane.setAlignment(Pos.CENTER);
        this.changePasswordPane.setSpacing(5);
        this.changePasswordPane.setMaxSize(1120, 640);
        this.changePasswordPane.setStyle("-fx-background-color: rgba(142, 143, 143, 0.8); -fx-background-radius: 1;");
        this.newPasswordField.setPromptText("New Password");
        this.newPasswordField2.setPromptText("Confirm New Password");
        this.newPasswordField.setPrefWidth(450);
        this.cancelPwChange.setPrefWidth(450);
        this.changePassword2.setPrefWidth(450);
        this.cancelPwChange.setPrefWidth(450);
        this.changePasswordPane.getChildren().addAll(this.errorTextPwChange, this.newPasswordField, this.newPasswordField2, this.changePassword2, this.cancelPwChange);
        this.changePasswordPane.setVisible(false);

        //color picker view
        this.colorPickerPane.setAlignment(Pos.CENTER);
        this.colorPickerPane.setVgap(5);
        this.colorPickerPane.setHgap(5);

        HBox colorRectangles = new HBox();
        colorRectangles.setAlignment(Pos.CENTER);
        colorRectangles.setSpacing(110);
        colorRectangles.getChildren().addAll(this.currentColor, this.newColor);
        this.colorPicker.getStyleClass().add("button");
        this.colorPicker.setPrefWidth(350);
        Text currentColorText = new Text("Current Color");
        currentColorText.getStyleClass().add("blacktext-id");
        Text newColorText = new Text("New Color");
        newColorText.getStyleClass().add("blacktext-id");
        HBox textBox = new HBox();
        textBox.setAlignment(Pos.CENTER);
        textBox.setSpacing(45);
        textBox.getChildren().addAll(currentColorText, newColorText);
        this.changePortalColor2.setPrefWidth(350);
        this.colorPickerPane.addRow(1, textBox);
        this.colorPickerPane.addRow(2, colorRectangles);
        this.colorPickerPane.addRow(3, this.colorPicker);
        this.colorPickerPane.addRow(4, this.changePortalColor2);

        //character customization
        this.custButtonPane.setAlignment(Pos.CENTER);
        this.custButtonPane.setSpacing(5);
        this.custCharPane.setLeft(custButtonPane);
        VBox errorTextBox = new VBox();
        errorTextBox.setAlignment(Pos.CENTER);
        errorTextBox.getChildren().add(this.errorTextCharCust);
        this.custCharPane.setBottom(errorTextBox);
        this.cancel2.getStyleClass().add("custChar-button");
        this.changePortalColor.getStyleClass().add("custChar-button");
        this.changeSpriteTexture.getStyleClass().add("custChar-button");

        custButtonPane.getChildren().addAll(this.cancel2, this.changePortalColor, this.changeSpriteTexture);
        this.custCharPane.setMaxSize(1120, 640);
        this.custCharPane.setStyle("-fx-background-color: rgba(142, 143, 143, 0.8); -fx-background-radius: 1;");
        this.custCharPane.setVisible(false);
    }

    @Override
    public void handleAction(ActionEvent t) {
        if (t.getTarget().equals(this.colorPicker)) {
            this.newColor.setFill(this.colorPicker.getValue());
            return;
        }
        if (this.currentPane.equals(this.settingsPane)) {
            handleSettingsPane(t);
            return;
        }
        if (this.currentPane.equals(this.changeUsernamePane)) {
            handleUsernameChangePane(t);
            return;
        }
        if (this.currentPane.equals(this.changePasswordPane)) {
            handlePasswordChangePane(t);
            return;
        }
        if (this.currentPane.equals(this.custCharPane)) {
            handleCharacterCust(t);
        }
    }

    @Override
    public void restore() {
        this.currentColor.setFill(this.stateM.getCurrentUser().getPortalColor());
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
            clearTextureSelect();
            this.custCharPane.setVisible(false);
            this.errorTextCharCust.setText("");
            this.settingsPane.setDisable(false);
        } else if (t.getTarget().equals(this.changePortalColor)) {
            clearTextureSelect();
            this.custCharPane.setCenter(this.colorPickerPane);
        } else if (t.getTarget().equals(this.changePortalColor2)) {
            Color toBeChangedTo = this.colorPicker.getValue();
            int red = (int) (toBeChangedTo.getRed() * 255);
            int green = (int) (toBeChangedTo.getGreen() * 255);
            int blue = (int) (toBeChangedTo.getBlue() * 255);
            try {
                this.userDao.updateColor(this.stateM.getCurrentUser().getUsername(), red, green, blue);
            } catch (SQLException e) {
                this.errorTextCharCust.setText("Unexpected error while updating color values in the database!");
                return;
            }
            this.currentColor.setFill(Color.rgb(red, green, blue));
            this.stateM.getCurrentUser().setPortalColor(Color.rgb(red, green, blue));
            this.errorTextCharCust.setText("Default Portal Color Changed!");
        } else if (t.getTarget().equals(this.changeSpriteTexture)) {
            buildTextureSelection();
            this.custCharPane.setCenter(this.texturePane);
        } else {
            Button button = (Button) t.getTarget();
            boolean ret = false;
            try {
                ret = this.userDao.updateTexture(this.stateM.getCurrentUser().getUsername(), button.getId());
            } catch (SQLException e) {
                this.errorTextCharCust.setText("Unexpected error while updating texture in the database!");
            }
            if (ret) {
                this.stateM.getCurrentUser().setTexture(button.getId());
                this.currentTexture.setFill(new ImagePattern(new Image(SettingsState.class.getResourceAsStream("/textures/" + this.stateM.getCurrentUser().getTexture() + "Left.png"))));
                this.errorTextCharCust.setText("Sprite Texture Updated!");
            }
        }
    }

    private void handlePasswordChangePane(ActionEvent t) {
        if (t.getTarget().equals(this.changePassword2)) {
            int ret = checker.checkPassword(this.newPasswordField.getText(), this.newPasswordField2.getText());
            if (ret != 0) {
                handlePasswordChangeRetVal(ret);
            } else {
                int ret2;
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
        } else if (ret == -1) {
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

    private void buildTextureSelection() {
        this.texturePane.getChildren().clear();
        Button guyRed = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyRedLeft.png"))));
        guyRed.setId("guyRed");
        guyRed.setMaxSize(78, 84);
        guyRed.setPrefSize(78, 84);
        Button guyBlack = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyBlackLeft.png"))));
        guyBlack.setId("guyBlack");
        guyBlack.setMaxSize(78, 84);
        guyBlack.setPrefSize(78, 84);
        Button guyWhite = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyWhiteLeft.png"))));
        guyWhite.setId("guyWhite");
        guyWhite.setMaxSize(78, 84);
        guyWhite.setPrefSize(78, 84);
        Button guyBlue = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyBlueLeft.png"))));
        guyBlue.setId("guyBlue");
        guyBlue.setMaxSize(78, 84);
        guyBlue.setPrefSize(78, 84);
        Button guyYellow = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyYellowLeft.png"))));
        guyYellow.setId("guyYellow");
        guyYellow.setMaxSize(78, 84);
        guyYellow.setPrefSize(78, 84);
        Button guyBrown = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyBrownLeft.png"))));
        guyBrown.setId("guyBrown");
        guyBrown.setMaxSize(78, 84);
        guyBrown.setPrefSize(78, 84);
        Button guyGreen = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyGreenLeft.png"))));
        guyGreen.setId("guyGreen");
        guyGreen.setMaxSize(78, 84);
        guyGreen.setPrefSize(78, 84);
        Button guyPink = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/guyPinkLeft.png"))));
        guyPink.setId("guyPink");
        guyPink.setMaxSize(78, 84);
        guyPink.setPrefSize(78, 84);
        Button chef = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/chefLeft.png"))));
        chef.setId("chef");
        chef.setMaxSize(78, 84);
        chef.setPrefSize(78, 84);
        Button soldier = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/soldierLeft.png"))));
        soldier.setId("soldier");
        soldier.setMaxSize(78, 84);
        soldier.setPrefSize(78, 84);

        this.texturePane.addRow(0, guyRed, guyBlack, guyWhite);
        this.texturePane.addRow(1, guyBlue, guyYellow, guyBrown);
        this.texturePane.addRow(2, guyGreen, guyPink, chef);
        this.texturePane.addRow(3, soldier);

        if (this.stateM.getCurrentUser().isDemonOpen()) {
            Button demon = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/demonLeft.png"))));
            demon.setId("demon");
            demon.setMaxSize(78, 84);
            demon.setPrefSize(78, 84);
            this.texturePane.addRow(3, demon);
        }
        if (this.stateM.getCurrentUser().isKnightOpen()) {
            Button knight = new Button(null, new ImageView(new Image(SettingsState.class.getResourceAsStream("/textures/knightLeft.png"))));
            knight.setId("knight");
            knight.setMaxSize(78, 84);
            knight.setPrefSize(78, 84);
            this.texturePane.addRow(3, knight);
        }

        HBox currentBox = new HBox();
        currentBox.setAlignment(Pos.CENTER);
        currentBox.setSpacing(10);
        Text currentTextureText = new Text("Current texture:");
        currentTextureText.getStyleClass().add("blacktext-id");
        this.currentTexture.setFill(new ImagePattern(new Image(SettingsState.class.getResourceAsStream("/textures/" + this.stateM.getCurrentUser().getTexture() + "Left.png"))));
        currentBox.getChildren().addAll(currentTextureText, this.currentTexture);
        this.custCharPane.setTop(currentBox);
    }

    public void clearTextureSelect() {
        this.custCharPane.setTop(null);
        this.custCharPane.setCenter(null);
        this.texturePane.getChildren().clear();
    }

}

package ui;

import algorithm.Algorithm;
import dao.DatabaseUserDao;
import dao.ScoreDao;
import dao.UserDao;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
import statemanagement.State;
import statemanagement.StateManager;

public class SettingsState extends State {

    private final StateManager stateM;
    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private final Button changeUsername;
    private final Button changeUsername2;
    private final Button customizeCharacter;
    private final Button backToMenu;
    private final Button cancel1;
    private final Button cancel2;
    private final Button changeColor;
    private final Button changeColor2;
    private final Button cancel3;
    private final Rectangle currentColor;
    private final Rectangle newColor;
    private final TextField newUserNameInput;
    private final Text errorText;
    private final Text errorText2;
    private final ColorPicker colorPicker;
    private final VBox settingsPane;
    private final VBox changeUsernamePane;
    private final BorderPane custCharPane;
    private final StackPane root;
    private final GridPane colorPickerPane;
    private final VBox custButtonPane;
    private final VBox colorPicButtons;
    private Pane currentPane;

    public SettingsState(StateManager stateM, UserDao userDao, ScoreDao scoreDao) {
        this.stateM = stateM;
        this.userDao = userDao;
        this.scoreDao = scoreDao;
        this.backToMenu = new Button("Back to menu");
        this.cancel1 = new Button("Cancel");
        this.cancel2 = new Button("Back to settings");
        this.cancel3 = new Button("Back to character customization");
        this.changeUsername = new Button("Change username");
        this.changeUsername2 = new Button("Change username");
        this.customizeCharacter = new Button("Customize character");
        this.changeColor = new Button("Change default sprite color");
        this.changeColor2 = new Button("Change color");
        this.colorPicker = new ColorPicker(Color.WHITE);
        this.currentColor = new Rectangle(60,60);
        this.newColor = new Rectangle(60,60, Color.WHITE);
        this.settingsPane = new VBox();
        this.changeUsernamePane = new VBox();
        this.custCharPane = new BorderPane();
        this.colorPickerPane = new GridPane();
        this.newUserNameInput = new TextField();
        this.custButtonPane = new VBox();
        this.colorPicButtons = new VBox();
        this.errorText = new Text();
        this.errorText2 = new Text();
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
        this.currentColor.setFill(this.stateM.getCurrentUser().getColor());
    }

    @Override
    public final void initPane() {
        this.root.getChildren().addAll(this.settingsPane, this.changeUsernamePane, this.custCharPane);
        this.root.setAlignment(Pos.CENTER);

        this.settingsPane.setAlignment(Pos.CENTER);
        this.settingsPane.setMaxSize(1200, 720);
        this.settingsPane.getChildren().addAll(this.changeUsername, this.customizeCharacter, this.backToMenu);

        this.changeUsernamePane.setAlignment(Pos.CENTER);
        this.changeUsernamePane.setMaxSize(1120, 640);
        this.changeUsernamePane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.8); -fx-background-radius: 1;");
        this.newUserNameInput.setMaxWidth(200);
        this.changeUsernamePane.getChildren().addAll(this.errorText, this.newUserNameInput, this.changeUsername2, this.cancel1);
        this.changeUsernamePane.setVisible(false);

        this.colorPickerPane.setAlignment(Pos.CENTER);
        this.custButtonPane.setAlignment(Pos.CENTER);
        this.colorPicButtons.setAlignment(Pos.CENTER);
        
        this.custCharPane.setLeft(custButtonPane);
        this.custCharPane.setBottom(this.errorText2);
        this.errorText2.setFont(Font.font("alegreya", 20));
        
        this.colorPicker.getStyleClass().add("button");
        this.colorPickerPane.addRow(1,new Text("Current color"), new Text("New color"));
        this.colorPickerPane.addRow(2,this.currentColor, this.newColor);
        this.colorPickerPane.addRow(3,this.colorPicker);
        
        this.colorPicButtons.getChildren().addAll(this.changeColor2, this.cancel3);
        
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
        handleCharacterCust(t);
    }

    @Override
    public void restore() {

    }

    @Override
    public void restore(Algorithm a, String map) {

    }

    private void handleSettingsPane(ActionEvent t) {
        if (t.getTarget().equals(this.changeUsername)) {
            this.settingsPane.setDisable(true);
            this.currentPane = this.changeUsernamePane;
            this.changeUsernamePane.setVisible(true);
            return;
        }
        if (t.getTarget().equals(this.backToMenu)) {
            this.stateM.setCurrentState(1);
            this.stateM.stateUpdate();
            this.stateM.setSceneRoot(this.stateM.getCurrentState().getCurrent());
            return;
        }
        this.settingsPane.setDisable(true);
        this.currentPane = this.custCharPane;
        this.custCharPane.setVisible(true);
    }

    private void handleUsernameChangePane(ActionEvent t) {
        //TODO username update also updates scoreTables.
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
            this.settingsPane.setDisable(false);
        } else if (t.getTarget().equals(this.changeColor)) {
            this.custButtonPane.setDisable(true);
            this.custCharPane.setCenter(this.colorPickerPane);
            this.custCharPane.setRight(this.colorPicButtons);
        } else if (t.getTarget().equals(this.cancel3)) {
            this.custCharPane.setCenter(null);
            this.custCharPane.setRight(null);
            this.custButtonPane.setDisable(false);
            this.errorText2.setText("");
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
            this.errorText2.setText("Default sprite color changed!");
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
}

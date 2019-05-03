package mvsm.ui;

import mvsm.dao.DatabaseUserDao;
import mvsm.dao.DatabaseScoreDao;
import mvsm.dao.Connector;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mvsm.eventhandling.ActionEventHandler;
import mvsm.eventhandling.KeyEventHandler;

/**
 * Main class of the application. Initializes and starts the application.
 */
public class Main extends Application {

    public final int WIDTH = 1200;
    public final int HEIGHT = 720;
    private StateManager stateManager;
    private ActionEventHandler actionEventHandler;
    private Scene scene;
    private boolean initSuccessful;

    @Override
    public void start(Stage stage) throws InterruptedException {
        stage.setTitle("Man Vs Machine");
        stage.setResizable(false);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(this.scene);
        if (initSuccessful) {
            stage.show();
        } else {
            Text error = new Text("Databasepath in the config.properties file was was wrong.\nCheck that the directories in the path actually exist!\n\nClosing the program");
            error.setFont(Font.font(30));
            VBox pane = new VBox();
            pane.setAlignment(Pos.CENTER);
            pane.getChildren().add(error);
            scene.setRoot(pane);
            stage.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }

    @Override
    public void init() throws Exception {
        initSuccessful = true;
        this.scene = new Scene(new Pane(), WIDTH, HEIGHT);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            properties.load(Main.class.getResourceAsStream("/defaultConfig.properties"));
            properties.store(new FileOutputStream("./config.properties"), "");
        }
        Connector connector = new Connector(properties.getProperty("databasepath"));
        DatabaseUserDao userDao = new DatabaseUserDao(connector);
        try {
            userDao.initDatabase();
        } catch (Exception e) {
            initSuccessful = false;
            return;
        }
        DatabaseScoreDao scoreDao = new DatabaseScoreDao(connector);
        this.stateManager = new StateManager(properties);
        this.stateManager.setScene(this.scene);
        KeyEventHandler KeyListener = new KeyEventHandler();
        this.scene.setOnKeyPressed(KeyListener);
        this.scene.setOnKeyReleased(KeyListener);
        State loginState = new LoginState(this.stateManager, userDao);
        State menuState = new MenuState(this.stateManager, userDao, scoreDao);
        State playingState = new PlayingState(this.stateManager, userDao, scoreDao);
        State settingsState = new SettingsState(this.stateManager, userDao, scoreDao);
        State highscoreState = new HighscoreState(this.stateManager, scoreDao);
        State gameSelectionState = new GameSelectionState(this.stateManager);

        this.stateManager.addState(loginState);
        this.stateManager.addState(menuState);
        this.stateManager.addState(playingState);
        this.stateManager.addState(settingsState);
        this.stateManager.addState(highscoreState);
        this.stateManager.addState(gameSelectionState);
        this.scene.setRoot(this.stateManager.getCurrentState().getCurrent());
        this.actionEventHandler = new ActionEventHandler(stateManager);
        this.scene.addEventHandler(ActionEvent.ACTION, actionEventHandler);
        this.stateManager.playLoginMusic();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

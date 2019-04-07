
package ui;

import dao.DatabaseUserDao;
import statemanagement.game.GameLoop;
import statemanagement.menu.LoginState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import eventhandling.ActionEventHandler;
import statemanagement.StateManager;
import eventhandling.KeyEventHandler;
import statemanagement.State;
import statemanagement.menu.MenuState;
import statemanagement.game.PlayingState;



public class MvsMUi extends Application {
    public final int WIDTH = 1200;
    public final int HEIGHT = 720;
    private StateManager gameStateManager;
    private GameLoop gameLoop;
    private ActionEventHandler actionEventHandler;
    private Scene scene;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Man Vs Machine");
        stage.setResizable(false);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(this.scene);
        stage.show();
    }
    
    @Override
    public void init() throws Exception {
        this.scene = new Scene(new Pane(), WIDTH, HEIGHT);
        this.gameStateManager = new StateManager();
        this.gameLoop = new GameLoop(this.gameStateManager);
        this.gameStateManager.setGameLoop(gameLoop);
        this.gameStateManager.setScene(this.scene);
        DatabaseUserDao userDao = new DatabaseUserDao();
        KeyEventHandler KeyListener = new KeyEventHandler();
        State loginState = new LoginState(this.gameStateManager, userDao);
        State menuState = new MenuState(this.gameStateManager, userDao);
        State playingState = new PlayingState(KeyListener, this.gameStateManager, userDao);
        
        this.gameStateManager.addState(loginState);
        this.gameStateManager.addState(menuState);
        this.gameStateManager.addState(playingState);
        this.scene.setRoot(this.gameStateManager.getCurrentState().getCurrent());
        this.actionEventHandler = new ActionEventHandler(gameStateManager);
        this.scene.addEventHandler(ActionEvent.ACTION, actionEventHandler);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


package ui;

import game.GameLoop;
import game.LoginState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import game.ActionEventHandler;
import game.GameState;
import game.GameStateManager;
import game.KeyEventHandler;
import game.MenuState;
import game.PlayingState;



public class MvsMUi extends Application {
    public final int WIDTH = 1200;
    public final int HEIGHT = 720;
    private GameStateManager gameStateManager;
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
        this.gameStateManager = new GameStateManager();
        this.gameLoop = new GameLoop(this.gameStateManager);
        this.gameStateManager.setGameLoop(gameLoop);
        this.gameStateManager.setScene(this.scene);
        
        KeyEventHandler KeyListener = new KeyEventHandler();
        GameState loginState = new LoginState(this.gameStateManager);
        GameState menuState = new MenuState(this.gameStateManager);
        GameState playingState = new PlayingState(KeyListener, this.gameStateManager);
        
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

package manvsmachine.game;

import com.sun.glass.ui.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import manvsmachine.ui.MvsMUi;

public class MenuState extends GameState {

    private final int stateId;
    private BorderPane menu;
    private GameStateManager gsm;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        this.stateId = 1;

        this.menu = new BorderPane();
        this.menu.setPrefSize(this.gsm.getScene().getWidth(), this.gsm.getScene().getHeight());
        initPane();
    }

    @Override
    public int getStateId() {
        return 1;
    }

    @Override
    public Pane getCurrent() {
        return this.menu;
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        VBox menuNodePane = new VBox();
        this.menu.setCenter(menuNodePane);
        Button play = new Button("Play");
        Button settings = new Button("Settings");
        Button highScores = new Button("Highscores");
        Button quit = new Button("Quit");
        menuNodePane.getChildren().addAll(play, highScores, settings, quit);
    }

    @Override
    public void handleAction(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Play")) {
            gsm.setCurrentState(2);
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
            gsm.getCurrentState().restore();
        } else if (button.getText().equals("Settings")) {
            //TODO
//            gsm.setCurrentState(3);
//            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Highscores")) {
            //TODO
//            gsm.setCurrentState(4);
//            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else {
            Platform.exit();
        }
    }

    @Override
    public void restore() {
        
    }

}

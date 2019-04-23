package ui;

import algorithm.Algorithm;
import algorithm.BFS;
import dao.HighScoreUser;
import dao.UserDao;
import statemanagement.StateManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import statemanagement.State;
import dao.ScoreDao;
import java.sql.SQLException;

public final class MenuState extends State {

    private final BorderPane menu;
    private final StateManager gsm;
    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private Text currentUserText;

    public MenuState(StateManager gsm, UserDao userDao, ScoreDao scoreDao) {
        this.gsm = gsm;
        this.userDao = userDao;
        this.scoreDao = scoreDao;
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
        this.currentUserText.setText("Current user: " + this.gsm.getCurrentUser().getUsername());
        int ret = 0;
        try {
            ret = this.scoreDao.createDefault(this.gsm.getCurrentUser().getUsername(), "BFS");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initPane() {
        VBox menuNodePane = new VBox();
        menuNodePane.setAlignment(Pos.CENTER);
        this.menu.setCenter(menuNodePane);
        Button play = new Button("Play");
        Button settings = new Button("Settings");
        Button highScores = new Button("Highscores");
        Button signOut = new Button("Sign Out");
        Button quit = new Button("Quit");
        this.currentUserText = new Text();
        this.currentUserText.setTranslateY(200);
        this.currentUserText.setFont(Font.font("verdana", 20));
        menuNodePane.getChildren().addAll(play, highScores, settings, signOut, quit, currentUserText);
    }

    @Override
    public void handleAction(ActionEvent t) {
        Button button = (Button) t.getTarget();
        if (button.getText().equals("Play")) {
            gsm.setCurrentState(5);
            gsm.getCurrentState().restore();
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Settings")) {
            gsm.setCurrentState(3);
            gsm.stateUpdate();
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Highscores")) {
            //TODO
            gsm.setCurrentState(4);
            gsm.getCurrentState().restore();
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Sign Out")) {
            gsm.setCurrentState(0);
            gsm.setCurrentUser(null);
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else {
            Platform.exit();
        }
    }

    @Override
    public void restore() {

    }

    @Override
    public void restore(Algorithm a, String map) {

    }

}

package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.UserDao;
import mvsm.dao.ScoreDao;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

/**
 * State representing the menu.
 */
public final class MenuState extends State {

    private final BorderPane menu;
    private final StateManager gsm;
    private final UserDao userDao;
    private final ScoreDao scoreDao;
    private Text currentUserText;
    private static final String PATH = "/pictures/";

    /**
     *
     * @param gsm For state management and playing music.
     * @param userDao For updating the current User text;
     * @param scoreDao For creating high-score defaults.
     */
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

    }

    @Override
    public void initPane() {
        VBox menuNodePane = new VBox();
        menuNodePane.setAlignment(Pos.CENTER);
        menuNodePane.setSpacing(5);
        this.menu.setCenter(menuNodePane);
        this.menu.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(MenuState.class.getResourceAsStream(PATH + "mvsmTitle.png"))), new CornerRadii(1), Insets.EMPTY)));
        Button play = new Button("Play");
        Button settings = new Button("Settings");
        Button highScores = new Button("Highscores");
        Button signOut = new Button("Sign Out");
        Button quit = new Button("Quit");
        play.setPrefWidth(300);
        settings.setPrefWidth(300);
        highScores.setPrefWidth(300);
        signOut.setPrefWidth(300);
        quit.setPrefWidth(300);
        this.currentUserText = new Text();
        this.currentUserText.getStyleClass().add("menu-text");
        this.currentUserText.setTranslateY(100);
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
            gsm.getCurrentState().restore();
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Highscores")) {
            gsm.setCurrentState(4);
            gsm.getCurrentState().restore();
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
        } else if (button.getText().equals("Sign Out")) {
            gsm.playLoginMusic();
            gsm.setCurrentState(0);
            gsm.getCurrentState().restore();
            if (this.gsm.getCurrentUser().isDemonOpen()) {
                try {
                    this.userDao.setDemonOpen(this.gsm.getCurrentUser().getUsername());
                } catch (SQLException ex) {
                }
            }
            if (this.gsm.getCurrentUser().isKnightOpen()) {
                try {
                    this.userDao.setKnightOpen(this.gsm.getCurrentUser().getUsername());
                } catch (SQLException ex) {
                }
            }
            gsm.setCurrentUser(null);
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());

        } else {
            if (this.gsm.getCurrentUser().isDemonOpen()) {
                try {
                    this.userDao.setDemonOpen(this.gsm.getCurrentUser().getUsername());
                } catch (SQLException ex) {
                }
            }
            if (this.gsm.getCurrentUser().isKnightOpen()) {
                try {
                    this.userDao.setKnightOpen(this.gsm.getCurrentUser().getUsername());
                } catch (SQLException ex) {
                }
            }
            Platform.exit();
        }
    }

    /**
     * Called when the Menu should be restored. Tries to create default values
     * for the high-scores of the user.
     */
    @Override
    public void restore() {
        this.currentUserText.setText("Current User: " + this.gsm.getCurrentUser().getUsername());
        try {
            this.scoreDao.createDefault(this.gsm.getCurrentUser().getUsername(), "BFS");
            this.scoreDao.createDefault(this.gsm.getCurrentUser().getUsername(), "DFS");
            this.scoreDao.createDefault(this.gsm.getCurrentUser().getUsername(), "Dijkstra");
        } catch (SQLException ex) {
            this.currentUserText.setText("There was an error creating default scores!");
        }
    }

    @Override
    public void restore(Algorithm a, String map) {

    }

}

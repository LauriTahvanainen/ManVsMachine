package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.HighScoreUser;
import mvsm.dao.ScoreDao;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import mvsm.game.GamePhysics;
import mvsm.eventhandling.KeyEventHandler;
import mvsm.sprite.Machine;
import mvsm.game.MapRenderer;
import mvsm.sprite.Sprite;

/**
 * The State where the game is played. The only State that uses the
 * restore(Algorithm algo, String mapName) method. Interacts with the classes
 * StateManager, DatabaseScoreDao, GamePhysics, Machine.
 *
 * @see mvsm.statemanagement.StateManager
 * @see mvsm.game.GamePhysics
 * @see mvsm.dao.DatabaseScoreDao
 * @see mvsm.sprite.Machine
 */
public class PlayingState extends State {

    private Sprite player;
    private Machine machine;
    private final StackPane root;
    private GridPane background;
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private final StateManager stateManager;
    private int[][] mapArray;
    private final ScoreDao scoreDao;
    private final MapRenderer renderer;
    private final GamePhysics physics;
    private final Text timeScoreBoard;
    private final Text lengthScoreBoard;
    private final BorderPane pauseMenu;
    private final HBox gameStatisticsPane;
    private final BorderPane endGamePane;
    private final Button saveHighScore;
    private final Button backToMainMenu;
    private final Button playAgain;
    private final Button restart;
    private final Button continueGame;
    private final BorderPane pauseButtonsPane;
    private final Text endText;
    private final Text yourEndScore;
    private int finalScore;
    private HighScoreUser currentScores;
    private String mapName;
    private String algorithmName;
    private int[] coordinates;
    public static final ImagePattern OVEN = new ImagePattern(new Image(PlayingState.class.getResourceAsStream("/textures/oven.png")));

    /**
     *
     * @param stateManager For state management and playing music.
     * @param scoreDao For saving high-scores.
     */
    public PlayingState(StateManager stateManager, ScoreDao scoreDao) {
        this.root = new StackPane();
        this.background = new GridPane();
        this.gameStatisticsPane = new HBox();
        this.gameStatisticsPane.setSpacing(20);
        this.endGamePane = new BorderPane();
        this.pauseButtonsPane = new BorderPane();
        this.stateManager = stateManager;
        this.scoreDao = scoreDao;
        this.renderer = new MapRenderer();
        this.timeScoreBoard = new Text("Time Score: 100");
        this.lengthScoreBoard = new Text("Route Length Score: 0");
        this.physics = new GamePhysics((KeyEventHandler) this.stateManager.getScene().getOnKeyPressed(), timeScoreBoard, lengthScoreBoard);
        this.pauseMenu = new BorderPane();
        this.saveHighScore = new Button("Save score");
        this.backToMainMenu = new Button("Back to menu");
        this.playAgain = new Button("Play again");
        this.restart = new Button("Restart game");
        this.continueGame = new Button("Continue");
        this.endText = new Text();
        this.yourEndScore = new Text();
        this.finalScore = 0;
        initPane();
    }

    @Override
    public int getStateId() {
        return 2;
    }

    @Override
    public Pane getCurrent() {
        return this.root;
    }

    @Override
    public void update() {
        int ret = this.physics.updateGameWorld();
        if (ret == -2) {
            pause();
            return;
        }
        if (ret == -1) {
            machineWin();
            return;
        }
        if (ret != 0) {
            playerWin(ret);
        }
    }

    @Override
    public void initPane() {
        //pausemenu and in-game scoreboards init
        this.pauseMenu.setTop(this.gameStatisticsPane);
        this.gameStatisticsPane.getChildren().addAll(this.lengthScoreBoard, this.timeScoreBoard);
        this.gameStatisticsPane.setAlignment(Pos.CENTER);
        this.pauseMenu.setCenterShape(true);
        this.pauseMenu.setPrefSize(1200, 720);

        this.timeScoreBoard.setFill(Color.CORNSILK);
        this.timeScoreBoard.setStrokeType(StrokeType.CENTERED);
        this.timeScoreBoard.setTextAlignment(TextAlignment.CENTER);
        this.timeScoreBoard.setStrokeLineCap(StrokeLineCap.ROUND);
        this.timeScoreBoard.setFont(Font.font("Nova Flat", 25));

        this.lengthScoreBoard.setFill(Color.CORNSILK);
        this.lengthScoreBoard.setStrokeType(StrokeType.CENTERED);
        this.lengthScoreBoard.setTextAlignment(TextAlignment.CENTER);
        this.lengthScoreBoard.setStrokeLineCap(StrokeLineCap.ROUND);
        this.lengthScoreBoard.setFont(Font.font("Nova Flat", 25));

        //endGame init
        this.playAgain.setPrefWidth(300);
        this.backToMainMenu.setPrefWidth(300);
        this.saveHighScore.setPrefWidth(300);
        this.endGamePane.setStyle("-fx-background-color: rgba(142, 143, 143, 0.8); -fx-background-radius: 1;");
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);
        this.endGamePane.setCenter(buttons);
        this.endGamePane.setPadding(new Insets(100));
        this.endGamePane.setTop(this.endText);
        BorderPane.setAlignment(this.endText, Pos.CENTER);
        this.endText.setFont(new Font("Didact Gothic", 40));
        this.endText.setLineSpacing(20);
        this.endText.setTextAlignment(TextAlignment.CENTER);
        this.yourEndScore.setFont(new Font("Didact Gothic", 30));
        buttons.getChildren().addAll(this.yourEndScore, this.playAgain, this.saveHighScore, this.backToMainMenu);

        //pause init
        this.continueGame.setPrefWidth(300);
        this.restart.setPrefWidth(300);
        Button backToMainMenu2 = new Button("Back to menu");
        backToMainMenu2.setPrefWidth(300);
        this.continueGame.setPrefWidth(300);
        this.pauseButtonsPane.setStyle("-fx-background-color: rgba(142, 143, 143, 0.8); -fx-background-radius: 1;");
        VBox pauseButtons = new VBox();
        pauseButtons.setAlignment(Pos.CENTER);
        pauseButtons.setSpacing(5);
        this.pauseButtonsPane.setCenter(pauseButtons);
        pauseButtons.getChildren().addAll(this.continueGame, this.restart, backToMainMenu2);
    }

    @Override
    public void handleAction(ActionEvent t) {
        Button b;
        try {
            b = (Button) t.getTarget();
        } catch (Exception e) {
            return;
        }
        String bText = b.getText();
        if (bText.equals("Back to menu")) {
            this.root.getChildren().clear();
            stateManager.setCurrentState(1);
            stateManager.setSceneRoot(stateManager.getCurrentState().getCurrent());
            stateManager.playMenuMusic();
            this.pauseMenu.getChildren().clear();
        }
        if (bText.equals("Restart game")) {
            this.pauseMenu.setCenter(null);
            restore();
        }
        if (bText.equals("Play again")) {
            this.pauseMenu.setCenter(null);
            this.pauseMenu.setTop(this.gameStatisticsPane);
            restore();
        }
        if (t.getTarget().equals(this.continueGame)) {
            this.pauseMenu.setCenter(null);
            this.stateManager.startLoop();
            this.stateManager.playMusic();
        }
        if (t.getTarget().equals(this.saveHighScore)) {
            try {
                this.scoreDao.updateScore(this.algorithmName, this.stateManager.getCurrentUser().getUsername(), this.mapName, this.finalScore);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            this.yourEndScore.setText("Highscore updated!");
            this.endText.setText("You Won!\nCurrent Highscore: " + this.finalScore + "\n");
            this.saveHighScore.setDisable(true);
            this.currentScores.updateScore(this.mapName, finalScore);
        }
    }

    /**
     * Restores the PlayingState to start a new game. MachineRestore coordinates
     * are saved, because machine movement is done via translate towards a point
     * on the machines route. This means that the Machine should always be
     * placed on the coordinates 1,1, and then it's translate should be set so
     * that the machine's form gets to the starting coordinates. If the machine
     * is simply placed on the machine coordinates, the machines movement will
     * break.
     *
     * @param algo To play against.
     * @param mapName To play in.
     */
    @Override
    public void restore(Algorithm algo, String mapName) {
        this.mapName = mapName;
        this.player = new Sprite(this.stateManager.getCurrentUser().getTexture(), 22, 26);
        this.machine = new Machine(38, 38, algo);
        this.machineGoal = new Rectangle(40, 40, OVEN);
        this.playerGoal = new Rectangle(40, 40, this.stateManager.getCurrentUser().getPortalColor());
        this.mapArray = this.renderer.formArrayMap(mapName);
        coordinates = this.renderer.getSpriteCoordinates(mapArray);
        algo.setUpAlgorithm(mapArray, coordinates[0], coordinates[1]);
        this.machine.calculateRoute(coordinates[2], coordinates[3]);
        this.background = renderer.renderMap(mapArray);
        this.renderer.placeSpritesOnMap(coordinates, background, player, machine, playerGoal, machineGoal);
        this.machine.getScanner().setBackground(background);
        this.saveHighScore.setDisable(false);
        this.pauseMenu.setTop(this.gameStatisticsPane);
        this.root.getChildren().addAll(this.background, this.pauseMenu);
        this.physics.setUpPhysicsWorld(background, player, machine, playerGoal, machineGoal, coordinates[1], coordinates[0]);
        this.finalScore = 0;
        this.algorithmName = algo.getName();
        try {
            this.currentScores = this.scoreDao.listUser(this.algorithmName, this.stateManager.getCurrentUser().getUsername());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        this.stateManager.startLoop();
        this.stateManager.playPlayingMusic();
    }

    private void playerWin(int finalScore) {
        stateManager.stopLoop();
        this.yourEndScore.setText("Your Final Score: " + finalScore);
        this.finalScore = finalScore;
        if (this.currentScores.getScore(mapName) < this.finalScore) {
            this.endText.setText("You Won!\nCurrent Highscore: " + this.currentScores.getScore(mapName) + "\nNew Highscore!");
        } else {
            this.endText.setText("You Won!\nCurrent Highscore: " + this.currentScores.getScore(mapName) + "\n");
            this.saveHighScore.setDisable(true);
        }
        if (this.finalScore == 6666 && !this.stateManager.getCurrentUser().isDemonOpen()) {
            this.endText.setText(this.endText.getText().concat("\nNew texture opened: 'Demon'!"));
            this.stateManager.getCurrentUser().setDemonOpen(true);
        } else if (this.finalScore == 1683 && !this.stateManager.getCurrentUser().isKnightOpen()) {
            this.endText.setText(this.endText.getText().concat("New texture opened: 'Knight'!"));
            this.stateManager.getCurrentUser().setKnightOpen(true);
        }
        this.pauseMenu.setCenter(this.endGamePane);
        this.pauseMenu.setTop(null);
    }

    private void machineWin() {
        stateManager.stopLoop();
        this.endText.setText("You Lost!");
        this.yourEndScore.setText("Your final Score: 0");
        this.finalScore = 0;
        this.pauseMenu.setCenter(this.endGamePane);
        this.saveHighScore.setDisable(true);
        this.pauseMenu.setTop(null);
    }

    /**
     * Restore the level completely.
     */
    @Override
    public void restore() {
        this.physics.restoreLevel();
        this.finalScore = 0;
        this.saveHighScore.setDisable(false);
        this.stateManager.startLoop();
        this.stateManager.playPlayingMusic();
    }

    private void pause() {
        stateManager.stopLoop();
        this.stateManager.pauseMusic();
        this.pauseMenu.setCenter(this.pauseButtonsPane);
    }

}

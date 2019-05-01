package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.HighScoreUser;
import mvsm.dao.UserDao;
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
import mvsm.game.GamePhysics;
import mvsm.eventhandling.KeyEventHandler;
import mvsm.sprite.Machine;
import mvsm.game.MapRenderer;
import mvsm.sprite.Sprite;

public class PlayingState extends State {

    private Sprite player;
    private Machine machine;
    private final StackPane root;
    private GridPane background;
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private final StateManager gsm;
    private int[][] mapArray;
    private final UserDao userDao;
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

    public PlayingState(StateManager gsm, UserDao userDao, ScoreDao scoreDao) {
        this.root = new StackPane();
        this.background = new GridPane();
        this.gameStatisticsPane = new HBox();
        this.gameStatisticsPane.setSpacing(20);
        this.endGamePane = new BorderPane();
        this.pauseButtonsPane = new BorderPane();
        this.gsm = gsm;
        this.userDao = userDao;
        this.scoreDao = scoreDao;
        this.renderer = new MapRenderer();
        this.timeScoreBoard = new Text("Time Score: 100");
        this.lengthScoreBoard = new Text("Route Length Score: 0");
        this.physics = new GamePhysics((KeyEventHandler) this.gsm.getScene().getOnKeyPressed(), timeScoreBoard, lengthScoreBoard);
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
        this.endGamePane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.9); -fx-background-radius: 1;");
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
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
        this.pauseButtonsPane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.9); -fx-background-radius: 1;");
        VBox pauseButtons = new VBox();
        pauseButtons.setAlignment(Pos.CENTER);
        this.pauseButtonsPane.setCenter(pauseButtons);
        pauseButtons.getChildren().addAll(this.continueGame, this.restart, new Button("Back to menu"));
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
            gsm.setCurrentState(1);
            gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
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
            this.gsm.startLoop();
        }
        if (t.getTarget().equals(this.saveHighScore)) {
            //TODO
            try {
                this.scoreDao.updateScore("BFS", this.gsm.getCurrentUser().getUsername(), this.mapName, this.finalScore);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            this.yourEndScore.setText("Highscore updated!");
            this.saveHighScore.setDisable(true);
            this.currentScores.updateScore(this.mapName, finalScore);
        }
    }

    public void setMap(int[][] map) {
        this.mapArray = map;
    }

    public void setBackground(GridPane background) {
        this.background = background;
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
     * @param algo
     * @param mapName
     */
    @Override
    public void restore(Algorithm algo, String mapName) {
        this.mapName = mapName;
        this.player = new Sprite(this.gsm.getCurrentUser().getColor(), 20, 20);
        this.machine = new Machine(Color.BLUE, 20, 20, algo);
        this.machineGoal = new Rectangle(40, 40, Color.BLUE);
        this.playerGoal = new Rectangle(40, 40, this.gsm.getCurrentUser().getColor());
        this.mapArray = this.renderer.formArrayMap(mapName);
        int[] machineCoordinates = this.renderer.getSpriteCoordinates(mapArray);
        algo.setUpAlgorithm(mapArray, machineCoordinates[0], machineCoordinates[1]);
        this.machine.calculateRoute(machineCoordinates[2], machineCoordinates[3]);
        this.background = renderer.renderMap(mapArray);
        this.renderer.placeSpritesOnMap(machineCoordinates, background, player, machine, playerGoal, machineGoal);
        this.machine.getScanner().setBackground(background);
        this.saveHighScore.setDisable(false);
        this.pauseMenu.setTop(this.gameStatisticsPane);
        this.root.getChildren().addAll(this.background, this.pauseMenu);
        this.physics.setUpPhysicsWorld(background, player, machine, playerGoal, machineGoal, machineCoordinates[1], machineCoordinates[0]);
        this.finalScore = 0;
        try {
            this.currentScores = this.scoreDao.listUser("BFS", this.gsm.getCurrentUser().getUsername());
        } catch (SQLException ex) {
            return;
        }
        this.gsm.setSceneRoot(this.root);
        this.gsm.startLoop();
    }

    private void playerWin(int finalScore) {
        gsm.stopLoop();
        this.yourEndScore.setText("Your Final Score: " + finalScore);
        this.finalScore = finalScore;
        if (this.currentScores.getScore(mapName) < this.finalScore) {
            this.endText.setText("You Won!\nCurrent Highscore: " + this.currentScores.getScore(mapName) + "\nNew Highscore!");
        } else {
            this.endText.setText("You Won!\nCurrent Highscore: " + this.currentScores.getScore(mapName) + "\n");
            this.saveHighScore.setDisable(true);
        }
        this.pauseMenu.setCenter(this.endGamePane);
        this.pauseMenu.setTop(null);
    }

    private void machineWin() {
        gsm.stopLoop();
        this.endText.setText("You Lost!");
        this.yourEndScore.setText("Your final Score: 0");
        this.finalScore = 0;
        this.pauseMenu.setCenter(this.endGamePane);
        this.saveHighScore.setDisable(true);
        this.pauseMenu.setTop(null);
    }

    @Override
    public void restore() {
        this.physics.restoreLevel();
        this.finalScore = 0;
        this.saveHighScore.setDisable(false);
        this.gsm.startLoop();
    }

    private void pause() {
        gsm.stopLoop();
        this.pauseMenu.setCenter(this.pauseButtonsPane);
    }

}

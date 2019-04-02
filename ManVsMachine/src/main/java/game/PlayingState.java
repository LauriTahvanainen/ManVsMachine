package manvsmachine.game;

import Sprites.Sprite;
import Sprites.algorithm.Algorithm;
import Sprites.algorithm.BFS;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayingState extends GameState {

    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private Sprite player;
    private Sprite machine;
    private GridPane background;
    private boolean start;
    private KeyEventHandler keyHandler;
    private Algorithm algorithm;
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private GameStateManager gsm;

    public PlayingState(KeyEventHandler handler, GameStateManager gsm) {
        this.player = new Sprite(Color.RED, 20, 20);
        this.machine = new Sprite(Color.BLUE, 20, 20);
        this.playerGoal = new Rectangle(40, 40, Color.RED);
        this.machineGoal = new Rectangle(40, 40, Color.BLUE);
        this.machine.getForm().setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        this.background = new GridPane();
        this.start = true;
        this.keyHandler = handler;
        this.gsm = gsm;
    }

    @Override
    public int getStateId() {
        return 3;
    }

    @Override
    public Pane getCurrent() {
        return this.background;
    }

    @Override
    public void update() {

        if (wallCollisionCheck()) {
            updatePlayerPosition();
        }
        if (!this.algorithm.getRoute().isEmpty()) {
            this.algorithm.takeStep();
        }
        goalCollisionCheck();
    }

    @Override
    public void initPane() {

    }

    @Override
    public void handleAction(ActionEvent t) {

    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public void setBackground(GridPane background) {
        this.background = background;
    }

    @Override
    public void restore() {
        this.background.getChildren().clear();
        this.machine.clearTranslate();
        this.player.clearTranslate();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (this.map[i][j] == 1) {
                    this.background.add(new Rectangle(40, 40, Color.BLACK), j, i);
                }
            }
        }
        this.background.add(this.player.getForm(), 28, 1);
        this.background.add(this.machine.getForm(), 1, 1);
        this.background.add(this.machineGoal, 28, 16);
        this.background.add(this.playerGoal, 1, 16);
        GridPane.setHalignment(this.player.getForm(), HPos.CENTER);
        GridPane.setHalignment(this.machine.getForm(), HPos.CENTER);

        setAlgorithm(new BFS(this.map, this.machine));
        this.algorithm.calculateRoute();
        this.algorithm.buildRoute();
        setKeyhandlerOn();

        this.gsm.startLoop();

    }

    public void setKeyhandlerOn() {
        this.background.getScene().setOnKeyPressed(keyHandler);
        this.background.getScene().setOnKeyReleased(keyHandler);
    }

    private void updatePlayerPosition() {
        if (this.keyHandler.getKeyCodes().contains(KeyCode.UP)) {
            this.player.moveUp();
        }
        if (this.keyHandler.getKeyCodes().contains(KeyCode.DOWN)) {
            this.player.moveDown();
        }
        if (this.keyHandler.getKeyCodes().contains(KeyCode.LEFT)) {
            this.player.moveLeft();
        }
        if (this.keyHandler.getKeyCodes().contains(KeyCode.RIGHT)) {
            this.player.moveRight();
        }
    }

    private boolean wallCollisionCheck() {
        for (Node node : this.background.getChildren()) {
            if (node.equals(this.machine.getForm()) || node.equals(this.machineGoal) || node.equals(this.playerGoal)) {
                continue;
            }
            if (this.player.checkCollision(node)) {
                this.player.getOutCollision(node.getBoundsInParent());
                return false;
            }
        }
        return true;
    }

    public void setAlgorithm(Algorithm a) {
        this.algorithm = a;
    }

    private void goalCollisionCheck() {
        if (this.playerGoal.getBoundsInParent().getMaxY() != this.playerGoal.getBoundsInLocal().getMaxY() && this.machineGoal.getBoundsInParent().getMaxY() != this.machineGoal.getBoundsInLocal().getMaxY()) {
            if (this.player.checkCollision(this.playerGoal)) {
                gsm.stopLoop();
                gsm.setCurrentState(1);
                gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
            }
            if (this.machine.checkCollision(this.machineGoal)) {
                gsm.stopLoop();
                gsm.setCurrentState(1);
                gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
            }
        }
    }

}

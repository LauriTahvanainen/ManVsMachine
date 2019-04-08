package statemanagement.game;

import statemanagement.StateManager;
import eventhandling.KeyEventHandler;
import sprite.Sprite;
import algorithm.Algorithm;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sprite.Machine;
import statemanagement.State;

public class PlayingState extends State {

    private Sprite player;
    private Machine machine;
    private GridPane background;
    private final KeyEventHandler keyHandler;
    private static final Rectangle PLAYERGOAL = new Rectangle(40, 40, Color.RED);
    private static final Rectangle MACHINEGOAL = new Rectangle(40, 40, Color.BLUE);
    private final StateManager gsm;
    private int[][] map;
    private final UserDao userdao;

    public PlayingState(KeyEventHandler handler, StateManager gsm, UserDao userdao) {
        this.player = new Sprite(Color.RED, 20, 20);
        this.background = new GridPane();
        this.keyHandler = handler;
        this.gsm = gsm;
        this.userdao = userdao;
        setKeyhandlerOn();
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
        if (!this.machine.getScanRoute().isEmpty()) {
            this.machine.takeStep();
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
    public void restore(Algorithm algo, int[][] map) {
        this.background.getChildren().clear();
        this.machine = new Machine(Color.BLUE, 20, 20, algo);
        this.player.clearTranslate();
        this.map = map;
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[0].length; j++) {
                if (this.map[i][j] == 1) {
                    this.background.add(new Rectangle(40, 40, Color.BLACK), j, i);
                }
            }
        }
        this.background.add(this.player.getForm(), 28, 1);
        this.background.add(this.machine.getForm(), 1, 1);
        this.background.add(PlayingState.MACHINEGOAL, 28, 16);
        this.background.add(PlayingState.PLAYERGOAL, 1, 16);
        GridPane.setHalignment(this.player.getForm(), HPos.CENTER);
        GridPane.setHalignment(this.machine.getForm(), HPos.CENTER);
        this.gsm.startLoop();
    }

    private void setKeyhandlerOn() {
        this.gsm.getScene().setOnKeyPressed(keyHandler);
        this.gsm.getScene().setOnKeyReleased(keyHandler);
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
            if (node.equals(this.machine.getForm()) || node.equals(PlayingState.MACHINEGOAL) || node.equals(this.PLAYERGOAL)) {
                continue;
            }
            if (this.player.checkCollision(node)) {
                this.player.getOutCollision(node.getBoundsInParent());
                return false;
            }
        }
        return true;
    }

    private void goalCollisionCheck() {
        //The first check is done because the seems to be a bug where a nodes bounds in it's parent will not update immediately after adding the node to the parent
        if (PlayingState.PLAYERGOAL.getBoundsInParent().getMaxY() != PlayingState.PLAYERGOAL.getBoundsInLocal().getMaxY() && PlayingState.MACHINEGOAL.getBoundsInParent().getMaxY() != PlayingState.MACHINEGOAL.getBoundsInLocal().getMaxY()) {
            if (this.player.checkCollision(PlayingState.PLAYERGOAL)) {
                gsm.stopLoop();
                gsm.setCurrentState(1);
                gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
            }
            if (this.machine.checkCollision(PlayingState.MACHINEGOAL)) {
                gsm.stopLoop();
                gsm.setCurrentState(1);
                gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
            }
        }
    }

    @Override
    public void restore() {

    }

}

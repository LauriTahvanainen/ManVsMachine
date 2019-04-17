package ui;

import statemanagement.StateManager;
import eventhandling.KeyEventHandler;
import sprite.Sprite;
import algorithm.Algorithm;
import dao.UserDao;
import game.GamePhysics;
import game.MapRenderer;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
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
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private final StateManager gsm;
    private int[][] map;
    private final UserDao userdao;
    private final MapRenderer renderer;
    private final GamePhysics physics;

    public PlayingState(StateManager gsm, UserDao userdao) {
        this.background = new GridPane();
        this.gsm = gsm;
        this.userdao = userdao;
        this.renderer = new MapRenderer();
        this.physics = new GamePhysics((KeyEventHandler) this.gsm.getScene().getOnKeyPressed());
    }

    @Override
    public int getStateId() {
        return 2;
    }

    @Override
    public Pane getCurrent() {
        return this.background;
    }

    @Override
    public void update() {
        int ret = this.physics.updateGameWorld();
        if (ret == 1) {
            playerWin();
        }
        if (ret == 2) {
            machineWin();
        }
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
        this.background = renderer.renderMap(map);
        this.player = new Sprite(this.gsm.getCurrentUser().getColor(), 20, 20);
        this.machine = new Machine(Color.BLUE, 20, 20, algo, this.background);
        this.machineGoal = new Rectangle(40, 40, Color.BLUE);
        this.playerGoal = new Rectangle(40, 40, this.gsm.getCurrentUser().getColor());
        this.map = map;
        this.background.add(this.player.getForm(), 28, 1);
        this.background.add(this.machine.getForm(), 1, 1);
        this.background.add(this.machineGoal, 28, 16);
        this.background.add(this.playerGoal, 1, 16);
        this.background.add(this.machine.getScanner().getScannerHead(), 1, 1);
        this.physics.setUpPhysicsWorld(background, player, machine, playerGoal, machineGoal);
        this.gsm.setSceneRoot(background);
        this.gsm.startLoop();
    }

    private void playerWin() {
        gsm.stopLoop();
        gsm.setCurrentState(1);
        gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
    }

    private void machineWin() {
        gsm.stopLoop();
        gsm.setCurrentState(1);
        gsm.setSceneRoot(gsm.getCurrentState().getCurrent());
    }

    @Override
    public void restore() {

    }

}

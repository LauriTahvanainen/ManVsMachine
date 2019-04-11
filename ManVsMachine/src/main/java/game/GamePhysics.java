package game;

import eventhandling.KeyEventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import sprite.Machine;
import sprite.Sprite;

public class GamePhysics {

    private GridPane background;
    private Sprite player;
    private Machine machine;
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private final KeyEventHandler handler;

    public GamePhysics(KeyEventHandler handler) {
        this.handler = handler;
    }
    
    

    public void setUpPhysicsWorld(GridPane background, Sprite player, Machine machine, Rectangle playerGoal, Rectangle machineGoal) {
        this.background = background;
        this.player = player;
        this.machine = machine;
        this.playerGoal = playerGoal;
        this.machineGoal = machineGoal;
    }

    public int updateGameWorld() {
        if (wallCollisionCheck()) {
            updatePlayerPosition();
        }
        if (!this.machine.getRoute().isEmpty()) {
            this.machine.takeStep();
        }
        if (playerGoalCollisionCheck()) {
            return 1;
        }
        if (machineGoalCollisionCheck()) {
            return 2;
        }
        return 0;
    }

    private void updatePlayerPosition() {
        if (this.handler.getKeyCodes().contains(KeyCode.UP)) {
            this.player.moveUp();
        }
        if (this.handler.getKeyCodes().contains(KeyCode.DOWN)) {
            this.player.moveDown();
        }
        if (this.handler.getKeyCodes().contains(KeyCode.LEFT)) {
            this.player.moveLeft();
        }
        if (this.handler.getKeyCodes().contains(KeyCode.RIGHT)) {
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

    private boolean playerGoalCollisionCheck() {
        //The first check is done because the seems to be a bug where a nodes bounds in it's parent will not update immediately after adding the node to the parent
        if (this.playerGoal.getBoundsInParent().getMaxY() != this.playerGoal.getBoundsInLocal().getMaxY()) {
            if (this.player.checkCollision(this.playerGoal)) {
                return true;
            }
        }
        return false;
    }

    private boolean machineGoalCollisionCheck() {
        //The first check is done because the seems to be a bug where a nodes bounds in it's parent will not update immediately after adding the node to the parent
        if (this.machineGoal.getBoundsInParent().getMaxY() != this.machineGoal.getBoundsInLocal().getMaxY()) {
            if (this.machine.checkCollision(this.machineGoal)) {
                return true;
            }
        }
        return false;
    }
}

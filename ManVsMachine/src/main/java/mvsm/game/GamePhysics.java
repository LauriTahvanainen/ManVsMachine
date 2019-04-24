package mvsm.game;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import mvsm.eventhandling.KeyEventHandler;
import mvsm.sprite.Machine;
import mvsm.sprite.Sprite;

/**
 * A class for handling physics and updating the state of the game. Collision
 * check, moving Sprites, updating scores.
 */
public class GamePhysics {

    private GridPane background;
    private Sprite player;
    private Machine machine;
    private Rectangle playerGoal;
    private Rectangle machineGoal;
    private final KeyEventHandler handler;
    private double timeScore;
    private double lengthScore;
    private final Text timeScoreText;
    private final Text lengthScoreText;
    private int machineRestoreX;
    private int machineRestoreY;

    /**
     *
     * @param handler sets the KeyEventhandler so the physics know what buttons
     * are pressed at each frame.
     * @param timeScoreText set Text that will be updated each frame with the
     * current time score.
     * @param lengthScoreText set Text that will be updated each frame with the
     * current route length score.
     */
    public GamePhysics(KeyEventHandler handler, Text timeScoreText, Text lengthScoreText) {
        this.handler = handler;
        this.timeScoreText = timeScoreText;
        this.lengthScoreText = lengthScoreText;
    }

    /**
     * This method is called in the PlayingState's restore method when a new
     * game is started. It is called after all the parameters have been
     * initialized e.g. the map has been rendered, a new machine has been
     * created.
     *
     * @param background The game's map.
     * @param player The player Sprite
     * @param machine The machine Sprite
     * @param playerGoal Player's goal.
     * @param machineGoal Machine's goal
     *
     * @see mvsm.ui.PlayingState#restore(mvsm.algorithm.Algorithm,
     * java.lang.String)
     */
    public void setUpPhysicsWorld(GridPane background, Sprite player, Machine machine, Rectangle playerGoal, Rectangle machineGoal, int machineRestoreX, int machineRestoreY) {
        this.background = background;
        this.player = player;
        this.machine = machine;
        this.playerGoal = playerGoal;
        this.machineGoal = machineGoal;
        this.machineRestoreX = machineRestoreX;
        this.machineRestoreY = machineRestoreY;
        restoreScore();
    }

    /**
     * The method that handles updating the game world, and communicating events
     * in the game world to the PlayingState. Checks for collisions, handles
     * collisions, updates player positions, updates scores, checks for winning
     * or losing, and checks for pause.
     *
     * @return 0 if no events have happened, the sum of scores if the player
     * reaches the goal, -1 if the machine reaches the goal, and -2 if the esc
     * button has been pressed.
     *
     */
    public int updateGameWorld() {
        if (updateMovement()) {
            this.timeScore -= 0.16;
            if (this.timeScore < 0) {
                this.timeScore = 0;
            }
        }
        this.timeScoreText.setText("Time Score: " + String.valueOf(Double.valueOf(timeScore).intValue()));
        this.lengthScoreText.setText("Route Length: " + String.valueOf(Double.valueOf(lengthScore).intValue()));
        if (playerGoalCollisionCheck()) {
            return (int) (this.timeScore + this.lengthScore);
        }
        if (machineGoalCollisionCheck()) {
            this.timeScoreText.setText("Score: 0");
            return -1;
        }
        return checkForPause();
    }

    private void updatePlayerPosition() {
        if (this.handler.getKeyCodes().contains(KeyCode.UP)) {
            this.player.moveUp();
            this.lengthScore += 0.5;
        }
        if (this.handler.getKeyCodes().contains(KeyCode.DOWN)) {
            this.player.moveDown();
            this.lengthScore += 0.5;
        }
        if (this.handler.getKeyCodes().contains(KeyCode.LEFT)) {
            this.player.moveLeft();
            this.lengthScore += 0.5;
        }
        if (this.handler.getKeyCodes().contains(KeyCode.RIGHT)) {
            this.player.moveRight();
            this.lengthScore += 0.5;
        }
    }

    private boolean wallCollisionCheck() {
        for (Node node : this.background.getChildren()) {
            if (node.equals(this.machine.getForm()) || node.equals(this.machineGoal) || node.equals(this.playerGoal) || Tile.SCAN_TILE.nodeEqualsTile(node)) {
                continue;
            }
            if (this.player.checkCollision(node)) {
                if (Tile.SAND.nodeEqualsTile(node)) {
                    this.player.setMovementFactor(0.5);
                    continue;
                }
                if (Tile.FLOOR.nodeEqualsTile(node)) {
                    this.player.setMovementFactor(1);
                    continue;
                }
                this.player.getOutCollision(node.getBoundsInParent());
                return false;
            }
        }
        return true;
    }

    private boolean playerGoalCollisionCheck() {
        //The first check is done because there seems to be a bug where a nodes bounds in it's parent will not update immediately after adding the node to the parent
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

    private int checkForPause() {
        if (this.handler.getKeyCodes().contains(KeyCode.ESCAPE)) {
            return -2;
        }
        return 0;
    }

    /**
     * Restores level. Sets Sprites to starting positions, restores machine's
     * routes, clears scans from screen and restores scores.
     *
     * @see mvsm.sprite.Machine#restoreRoute()
     * @see mvsm.sprite.Scanner#restoreScanRoute()
     */
    public void restoreLevel() {
        this.player.clearTranslate();
        this.machine.setTranslate(this.machineRestoreX, this.machineRestoreY);
        this.machine.restoreRoute();
        this.machine.getScanner().restoreScanRoute();
        this.machine.getScanner().clearTranslate();
        restoreScore();
        clearScan();
    }

    private void restoreScore() {
        this.timeScore = 2000.0;
        this.lengthScore = 0;
    }

    private void clearScan() {
        this.machine.getScanner().deleteScan();
    }

    private boolean updateMovement() {
        if (!this.machine.getScanRoute().isEmpty()) {
            this.machine.scanNext();
            if (this.machine.getScanRoute().isEmpty()) {
                this.machine.getScanner().deleteScan();
            }
            return false;
        }
        if (!this.machine.getRoute().isEmpty()) {
            this.machine.takeStep();
        }
        if (wallCollisionCheck()) {
            updatePlayerPosition();
        }
        return true;
    }
}

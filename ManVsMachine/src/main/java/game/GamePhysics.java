package game;

import eventhandling.KeyEventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sprite.Machine;
import sprite.Sprite;

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

    public GamePhysics(KeyEventHandler handler, Text timeScoreText, Text lengthScore) {
        this.handler = handler;
        this.timeScoreText = timeScoreText;
        this.lengthScoreText = lengthScore;
    }

    public void setUpPhysicsWorld(GridPane background, Sprite player, Machine machine, Rectangle playerGoal, Rectangle machineGoal) {
        this.background = background;
        this.player = player;
        this.machine = machine;
        this.playerGoal = playerGoal;
        this.machineGoal = machineGoal;
        this.timeScore = 2000.0;
        this.lengthScore = 0;
    }

    public int updateGameWorld() {
        if (!this.machine.getScanRoute().isEmpty()) {
            this.machine.scanNext();
            if (this.machine.getScanRoute().isEmpty()) {
                this.machine.getScanner().deleteScan();
            }
        } else {
            if (!this.machine.getRoute().isEmpty()) {
                this.machine.takeStep();
            }
            if (wallCollisionCheck()) {
                updatePlayerPosition();
            }
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
            return 7;
        }
        return 0;
    }

    public void restoreScore() {
        this.timeScore = 2000.0;
        this.lengthScore = 0;
    }
}

package mvsm.sprite;

import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

/**
 * Base class for all sprites in the game.
 */
public class Sprite {

    private final Polygon form;
    private final double height;
    private final double width;
    private ImagePattern textureLeft;
    private ImagePattern textureRight;
    /**
     * Variable that adjusts the movementSpeed of the sprite
     */
    private double movementFactor;

    /**
     * Default constructor that creates a sprite with a black fill.
     *
     * @param height Height of the form.
     * @param width Width of the form.
     */
    public Sprite(double height, double width) {
        this.form = new Polygon(0, 0, 0, height, width, height, width, 0);
        this.height = height;
        this.width = width;
        this.movementFactor = 1;
        GridPane.setHalignment(this.form, HPos.CENTER);
    }

    /**
     * Constructs a new Sprite with the given parameters and a movementFactor
     * set to 1. Sets its texture.
     *
     * @param texture To fill the form with.
     * @param height Height of the form.
     * @param width Width of the form.
     */
    public Sprite(String texture, double height, double width) {
        this(height, width);
        this.textureLeft = new ImagePattern(new Image(Sprite.class.getResourceAsStream("/textures/" + texture + "Left.png")));
        this.textureRight = new ImagePattern(new Image(Sprite.class.getResourceAsStream("/textures/" + texture + "Right.png")));
        this.form.setFill(textureLeft);
    }

    public Polygon getForm() {
        return form;
    }

    public double getTranslateY() {
        return this.form.getTranslateY();
    }

    public void setMovementFactor(double movementFactor) {
        this.movementFactor = movementFactor;
    }

    public double getTranslateX() {
        return this.form.getTranslateX();
    }

    /**
     * Move the Sprite's form right by adding 0.5 * movementFactor to it's
     * translateX, and set its texture to point to the right.
     */
    public void moveRight() {
        this.form.setTranslateX(this.form.getTranslateX() + (0.5 * this.movementFactor));
        this.form.setFill(textureRight);
    }

    /**
     * Move the Sprite's form up by subtracting 0.5 * movementFactor from it's
     * translateY.
     */
    public void moveUp() {
        this.form.setTranslateY(this.form.getTranslateY() - (0.5 * this.movementFactor));
    }

    /**
     * Move the Sprite's form down by adding 0.5 * movementFactor to it's
     * translateY.
     */
    public void moveDown() {
        this.form.setTranslateY(this.form.getTranslateY() + (0.5 * this.movementFactor));
    }

    /**
     * Move the Sprite's form left by subtracting 0.5 * movementFactor from it's
     * translateX, and make its texture point to the left.
     */
    public void moveLeft() {
        this.form.setTranslateX(this.form.getTranslateX() - (0.5 * this.movementFactor));
        this.form.setFill(textureLeft);
    }

    /**
     * When a node and this sprite are colliding, this method is called. The
     * center points of the node and this Sprite's form are calculated, and then
     * the Sprites form is moved away from the node along a vector calculated
     * with the center points.
     *
     * @param b bounds of the colliding node.
     *
     * @see #moveAlong(javafx.geometry.Point2D)
     */
    public void getOutCollision(Bounds b) {
        Bounds playerBounds = this.form.getBoundsInParent();
        double playerCenterX = playerBounds.getMinX() + playerBounds.getWidth() / 2;
        double playerCenterY = playerBounds.getMinY() + playerBounds.getHeight() / 2;

        double nodeCenterX = b.getMinX() + b.getWidth() / 2;
        double nodeCenterY = b.getMinY() + b.getHeight() / 2;

        moveAlong(new Point2D((playerCenterX - nodeCenterX), (playerCenterY - nodeCenterY)));
    }

    /**
     * Moves the form along the given vector.
     *
     * @param movementVector to move along.
     */
    public void moveAlong(Point2D movementVector) {
        movementVector = movementVector.normalize();
        this.form.setTranslateX(this.form.getTranslateX() + movementVector.getX() * 0.5 * movementFactor);
        this.form.setTranslateY(this.form.getTranslateY() + movementVector.getY() * 0.5 * movementFactor);
    }

    /**
     * Check if this node is colliding with the given node.
     *
     * @param node to check collision with.
     * @return true, if there is a collision, false otherwise.
     */
    public boolean checkCollision(Node node) {
        if (node.equals(this.form)) {
            return false;
        }
        return this.form.getBoundsInParent().intersects(node.getBoundsInParent());
    }

    /**
     * Move this Sprite's form to the starting position.
     */
    public void clearTranslate() {
        this.form.setTranslateX(0);
        this.form.setTranslateY(0);
    }

}

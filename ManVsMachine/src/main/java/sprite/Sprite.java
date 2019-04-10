package sprite;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Sprite {

    private Polygon form;
    private Color color;
    private double height;
    private double width;

    public Sprite(Color color, double height, double width) {
        this.form = new Polygon(0, 0, 0, height, width, height, width, 0);
        this.color = color;
        this.form.setFill(this.color);
        this.height = height;
        this.width = width;

    }

    public Polygon getForm() {
        return form;
    }

    public double getTranslateY() {
        return this.form.getTranslateY();
    }

    public double getTranslateX() {
        return this.form.getTranslateX();
    }

    public Point2D getTranslateCoordinates() {
        return new Point2D(this.form.getTranslateX(), this.form.getTranslateY());
    }

    public void moveRight() {
        this.form.setTranslateX(this.form.getTranslateX() + 0.5);
    }

    public void moveUp() {
        this.form.setTranslateY(this.form.getTranslateY() - 0.5);
    }

    public void moveDown() {
        this.form.setTranslateY(this.form.getTranslateY() + 0.5);
    }

    public void moveLeft() {
        this.form.setTranslateX(this.form.getTranslateX() - 0.5);
    }

    public void getOutCollision(Bounds b) {
        Bounds playerBounds = this.form.getBoundsInParent();
        double playerCenterX = playerBounds.getMinX() + playerBounds.getWidth() / 2;
        double playerCenterY = playerBounds.getMinY() + playerBounds.getHeight() / 2;

        double nodeCenterX = b.getMinX() + b.getWidth() / 2;
        double nodeCenterY = b.getMinY() + b.getHeight() / 2;

        moveAlong(new Point2D((playerCenterX - nodeCenterX), (playerCenterY - nodeCenterY)));
    }

    public void moveAlong(Point2D movementVector) {
        movementVector = movementVector.normalize();
        this.form.setTranslateX(this.form.getTranslateX() + movementVector.getX() * 0.5);
        this.form.setTranslateY(this.form.getTranslateY() + movementVector.getY() * 0.5);
    }

    public boolean checkCollision(Node node) {
        if (node.equals(this.form)) {
            return false;
        }
        return this.form.getBoundsInParent().intersects(node.getBoundsInParent());
    }

    public void clearTranslate() {
        this.form.setTranslateX(0);
        this.form.setTranslateY(0);
    }

}

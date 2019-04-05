package sprite;

import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Sprite {

    private Polygon form;
    private Color color;
    private double height;
    private double width;
    private double edellinenX;
    private double edellinenY;

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

    public void moveRight() {

        this.edellinenX = this.form.getTranslateX();
        this.edellinenY = this.form.getTranslateY();
        this.form.setTranslateX(this.form.getTranslateX() + 0.5);
    }

    public void moveUp() {

        this.edellinenX = this.form.getTranslateX();
        this.edellinenY = this.form.getTranslateY();
        this.form.setTranslateY(this.form.getTranslateY() - 0.5);
    }

    public void moveDown() {

        this.edellinenX = this.form.getTranslateX();
        this.edellinenY = this.form.getTranslateY();
        this.form.setTranslateY(this.form.getTranslateY() + 0.5);
    }

    public void moveLeft() {

        this.edellinenX = this.form.getTranslateX();
        this.edellinenY = this.form.getTranslateY();
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

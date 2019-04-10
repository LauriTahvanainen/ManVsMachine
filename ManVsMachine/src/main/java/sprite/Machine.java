package sprite;

import algorithm.Algorithm;
import algorithm.Vertex;
import java.util.ArrayDeque;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Machine extends Sprite {

    private Algorithm algorithm;
    private ArrayDeque<Vertex> route;
    private ArrayDeque<Vertex> scanRoute;

    public Machine(Color color, double height, double width, Algorithm a) {
        super(color, height, width);
        this.algorithm = a;
        this.algorithm.calculateRoute();
        this.route = this.algorithm.getRoute();
        this.scanRoute = this.algorithm.getMapScan();
    }

    public void takeStep() {
        Vertex v = this.route.peekFirst();
        if (this.getForm().getTranslateY() == v.getRow() && this.getForm().getTranslateX() == v.getColumn()) {
            this.route.pop();
        }
        this.moveAlong(new Point2D(v.getColumn() - this.getForm().getTranslateX(), v.getRow() - this.getForm().getTranslateY()));
    }

    public void scanNext() {
        Vertex v = this.scanRoute.peekFirst();
        if (this.getForm().getTranslateY() == v.getRow() && this.getForm().getTranslateX() == v.getColumn()) {
            this.scanRoute.pop();
        }
        moveTowards(new Point2D(v.getColumn(), v.getRow()));
    }

    public ArrayDeque<Vertex> getRoute() {
        return this.route;
    }

    public ArrayDeque<Vertex> getScanRoute() {
        return this.scanRoute;
    }

    public void moveTowards(Point2D point) {
        Point2D towards = new Point2D(point.getX() - this.getForm().getTranslateX(), point.getY() - this.getForm().getTranslateY());
        towards.normalize();
        this.getForm().setTranslateX(this.getForm().getTranslateX() + towards.getX());
        this.getForm().setTranslateY(this.getForm().getTranslateY() + towards.getY());
    }

}

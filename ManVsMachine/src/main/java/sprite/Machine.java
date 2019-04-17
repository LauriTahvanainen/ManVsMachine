package sprite;

import algorithm.Algorithm;
import algorithm.Vertex;
import java.util.ArrayDeque;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Machine extends Sprite {

    private Algorithm algorithm;
    private ArrayDeque<Vertex> route;
    private Scanner scanner;

    public Machine(Color color, double height, double width, Algorithm a, GridPane scanBackground) {
        super(color, height, width);
        this.algorithm = a;
        this.algorithm.calculateRoute();
        GridPane.setHalignment(this.getForm(), HPos.CENTER);
        this.route = this.algorithm.getRoute();
        this.scanner = new Scanner(this.algorithm.getMapScan(), scanBackground);
    }

    public void takeStep() {
        Vertex v = this.route.peekFirst();
        if (this.getForm().getTranslateY() == v.getRow() && this.getForm().getTranslateX() == v.getColumn()) {
            this.route.pop();
        }
        this.moveAlong(new Point2D(v.getColumn() - this.getForm().getTranslateX(), v.getRow() - this.getForm().getTranslateY()));
    }

    public ArrayDeque<Vertex> getRoute() {
        return this.route;
    }

    public ArrayDeque<Vertex> getScanRoute() {
        return this.scanner.getScanRoute();
    }

    public Scanner getScanner() {
        return scanner;
    }
    
    public void scanNext() {
        this.scanner.scanNext();
    }

}

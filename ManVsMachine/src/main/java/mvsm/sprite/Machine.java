package mvsm.sprite;

import mvsm.algorithm.Vertex;
import mvsm.algorithm.Algorithm;
import java.util.ArrayDeque;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * A class implementing the features of the machine. Machine has an instance of
 * scanner. The calculated routes are given to the scanner in the for of
 * Vertex-ArrayDeques.
 *
 * @see Scanner
 */
public class Machine extends Sprite {

    private Algorithm algorithm;
    private ArrayDeque<Vertex> route;
    private ArrayDeque<Vertex> routeBackUp;
    private Scanner scanner;

    /**
     * Constructor of the machine.
     *
     * @param color For the machine's fill.
     * @param height height of the machines form.
     * @param width width of the machines form.
     * @param a The algorithm used by the machine to calculate it's route to the
     * goal, and calculate the scan route for the scanner.
     */
    public Machine(Color color, double height, double width, Algorithm a) {
        super(color, height, width);
        this.algorithm = a;
        GridPane.setHalignment(this.getForm(), HPos.CENTER);
    }

    /**
     * Moves the form of the machine towards the next node on it's route. When
     * the form reaches the next node, the node is popped from the route.
     *
     * @see Sprite#moveAlong(javafx.geometry.Point2D)
     */
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

    /**
     * @see Scanner#scanNext()
     */
    public void scanNext() {
        this.scanner.scanNext();
    }

    /**
     * Restores the calculated route from the routeBackUp.
     */
    public void restoreRoute() {
        this.route = this.routeBackUp.clone();
    }

    /**
     * Calls the calculateRoute(int goalX, int goalY) of the algorithm. Then
     * gets the calculated route from the algorithm and saves a backup of it.
     * Finally, creates a scanner with the calculated scan route as parameter.
     *
     * @param goalX X-coordinate of the goal
     * @param goalY Y-coordinate of the goal
     *
     * @see Algorithm#calculateRoute(int, int)
     * @see mvsm.algorithm.BFS#calculateRoute(int, int)
     * @see mvsm.sprite.Scanner
     */
    public void calculateRoute(int goalX, int goalY) {
        this.algorithm.calculateRoute(goalX, goalY);
        this.route = this.algorithm.getRoute();
        this.routeBackUp = this.route.clone();
        this.scanner = new Scanner(this.algorithm.getMapScan());
    }
}

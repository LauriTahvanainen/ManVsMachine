package mvsm.sprite;

import mvsm.algorithm.Vertex;
import mvsm.algorithm.Algorithm;
import java.util.ArrayDeque;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * A class implementing the features of the machine. Machine has an instance of
 * scanner. The calculated routes are given to the scanner in the for of
 * Vertex-ArrayDeques. It should be noted that in the GridPane, the x- and
 * y-axis are flipped.
 *
 * @see Scanner
 */
public class Machine extends Sprite {

    private static final ImagePattern FILL1 = new ImagePattern(new Image(Machine.class.getResourceAsStream("/textures/cooking_mechRight.png")));
    private static final ImagePattern FILL2 = new ImagePattern(new Image(Machine.class.getResourceAsStream("/textures/cooking_mechLeft.png")));
    private final Algorithm algorithm;
    private ArrayDeque<Vertex> route;
    private Scanner scanner;

    /**
     * Constructs a new machine, with height, width and an algortihm. Also set's
     * the fill of the machine's form to machine's texture.
     *
     * @param height height of the machines form.
     * @param width width of the machines form.
     * @param a The algorithm used by the machine to calculate it's route to the
     * goal, and calculate the scan route for the scanner.
     */
    public Machine(double height, double width, Algorithm a) {
        super(height, width);
        this.getForm().setFill(FILL1);
        this.algorithm = a;
    }

    /**
     * Moves the form of the machine towards the next node on it's route, also
     * switches the fill of the form depending on the direction the machine is
     * moving. When the form reaches the next node, the node is popped from the
     * route.
     *
     * @see Sprite#moveAlong(javafx.geometry.Point2D)
     */
    public void takeStep() {
        Vertex v = this.route.peekFirst();
        if (Math.abs(v.getRow() - this.getTranslateY()) < 0.3 && Math.abs(v.getColumn() - this.getTranslateX()) < 0.3) {
            this.route.pop();
        }
        if (v.getColumn() > this.getTranslateX()) {
            this.getForm().setFill(FILL1);
        } else {
            this.getForm().setFill(FILL2);
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

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * @see Scanner#scanNext()
     */
    public void scanNext() {
        this.scanner.scanNext();
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
        this.scanner = new Scanner(this.algorithm.getMapScan());
    }

    /**
     * Recalculates the route to the goal.
     */
    public void reCalculate() {
        algorithm.reCalculate();
        this.route = this.algorithm.getRoute();
        this.scanner.restoreScanRoute(this.algorithm.getMapScan());
    }

    /**
     * Sets the machine's form's translate coordinates to the given values.
     *
     * @param coorX To set the translate-X of this machines form to.
     * @param coorY To set the translate-Y of this machines form to.
     */
    public void setTranslates(int coorX, int coorY) {
        this.getForm().setTranslateX(coorX);
        this.getForm().setTranslateY(coorY);
    }

}

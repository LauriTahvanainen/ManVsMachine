package mvsm.sprite;

import mvsm.algorithm.Vertex;
import java.util.ArrayDeque;
import java.util.Iterator;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mvsm.game.Tile;

/**
 * Implements the scanning effect by following a scanning route and adding
 * SCAN_TILES to the background. Form invisible.
 */
public class Scanner {

    private final Rectangle scannerHead;
    private ArrayDeque<Vertex> scanRoute;
    private GridPane backGround;

    /**
     * Creates a new instance of a Scanner with a route to scan along and an invisible form.
     *
     * @param scanRoute The route to scan along.
     */
    public Scanner(ArrayDeque<Vertex> scanRoute) {
        this.scannerHead = new Rectangle(10, 10);
        this.scannerHead.setFill(Color.rgb(255, 2, 255, 0));
        this.scanRoute = scanRoute;
        GridPane.setHalignment(scannerHead, HPos.CENTER);
    }

    /**
     * Called by a Machine instance. If the scannerHead is on the coordinates
     * given by the next node on the route, adds a SCAN_TILE to the position,
     * and removes the node from the route. Finally, moves the scannerHead to
     * the next node on the scanRoute.
     *
     * @see #moveTo(javafx.geometry.Point2D)
     * @see mvsm.game.Tile#SCAN_TILE
     */
    public void scanNext() {
        Vertex v = this.scanRoute.peekFirst();
        if (this.scannerHead.getTranslateY() == v.getRow() && this.scannerHead.getTranslateX() == v.getColumn()) {
            this.backGround.add(Tile.SCAN_TILE.getTile(), v.getColumn() / 40 + 1, v.getRow() / 40 + 1);
            this.scanRoute.pop();
        }
        moveTo(new Point2D(v.getColumn(), v.getRow()));
    }

    /**
     * Iterates through all the nodes in the background and if a node equals a
     * SCAN_TILE, removes it. In the end, all SCAN_TILEs will be removed.
     *
     * @see mvsm.game.Tile#nodeEqualsTile(javafx.scene.Node)
     */
    public void deleteScan() {
        Iterator itr = this.backGround.getChildren().iterator();
        while (itr.hasNext()) {
            Node node = (Node) itr.next();
            if (Tile.SCAN_TILE.nodeEqualsTile(node)) {
                itr.remove();
            }
        }
    }

    /**
     * Moves the scannerHead to the point given as a parameter.
     *
     * @param point To move to.
     */
    public void moveTo(Point2D point) {
        Point2D towards = new Point2D(point.getX() - this.scannerHead.getTranslateX(), point.getY() - this.scannerHead.getTranslateY());
        this.scannerHead.setTranslateX(this.scannerHead.getTranslateX() + towards.getX());
        this.scannerHead.setTranslateY(this.scannerHead.getTranslateY() + towards.getY());
    }

    public ArrayDeque<Vertex> getScanRoute() {
        return scanRoute;
    }

    public Rectangle getScannerHead() {
        return scannerHead;
    }

    /**
     * Restore scanRoute from a new route calculation;
     * @param scanRoute The new scan route.
     */
    public void restoreScanRoute(ArrayDeque<Vertex> scanRoute) {
        this.scanRoute = scanRoute;
    }

    /**
     * Returns the scanner back to the starting position.
     */
    public void clearTranslate() {
        this.scannerHead.setTranslateX(0);
        this.scannerHead.setTranslateY(0);
    }

    /**
     * Sets the background that the Scanner will fill with SCAN_TILEs.
     *
     * @param scanBackground to scan.
     */
    public void setBackground(GridPane scanBackground) {
        this.backGround = scanBackground;
    }
}

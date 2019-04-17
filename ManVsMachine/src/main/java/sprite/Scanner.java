package sprite;

import algorithm.Vertex;
import game.Tile;
import java.util.ArrayDeque;
import java.util.Iterator;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Scanner {

    private Rectangle scannerHead;
    private ArrayDeque<Vertex> scanRoute;
    private GridPane backGround;
    private boolean scanned;

    public Scanner(ArrayDeque<Vertex> scanRoute, GridPane scanBackground) {
        this.scannerHead = new Rectangle(10, 10);
        this.scannerHead.setFill(Color.rgb(255, 2, 255, 0));
        this.scanRoute = scanRoute;
        GridPane.setHalignment(scannerHead, HPos.CENTER);
        this.backGround = scanBackground;
        this.scanned = false;
    }

    public void scanNext() {
        Vertex v = this.scanRoute.peekFirst();
        if (this.scannerHead.getTranslateY() == v.getRow() && this.scannerHead.getTranslateX() == v.getColumn()) {
            this.backGround.add(Tile.SCAN_TILE.getTile(), v.getColumn() / 40 + 1, v.getRow() / 40 + 1);
            this.scanRoute.pop();
        }
        moveTowards(new Point2D(v.getColumn(), v.getRow()));
        if (this.scanRoute.isEmpty()) {
            this.scanned = true;
        }
    }

    public void deleteScan() {
        Iterator itr = this.backGround.getChildren().iterator();
        while (itr.hasNext()) {
            Node node = (Node) itr.next();
            if (Tile.SCAN_TILE.nodeEqualsTile(node)) {
                itr.remove();
            }
        }
    }

    public void moveTowards(Point2D point) {
        Point2D towards = new Point2D(point.getX() - this.scannerHead.getTranslateX(), point.getY() - this.scannerHead.getTranslateY());
        towards.normalize();
        this.scannerHead.setTranslateX(this.scannerHead.getTranslateX() + towards.getX());
        this.scannerHead.setTranslateY(this.scannerHead.getTranslateY() + towards.getY());
    }

    public ArrayDeque<Vertex> getScanRoute() {
        return scanRoute;
    }

    public Rectangle getScannerHead() {
        return scannerHead;
    }

}

package game;

import mvsm.algorithm.Algorithm;
import mvsm.algorithm.BFS;
import mvsm.algorithm.Vertex;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import mvsm.sprite.Machine;

//Also tests scanner
public class MachineTest {

    private Machine testMachine;
    private GridPane pane;
    private int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1},
        {1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1},
        {1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
        {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
        {1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private final int lastX = this.map[0].length - 3;
    private final int lastY = this.map.length - 3;
    private Algorithm algo;

    public MachineTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.pane = new GridPane();
        this.algo = new BFS();
        this.testMachine = new Machine(Color.BLACK, 10, 10, this.algo);
        this.algo.setUpAlgorithm(this.map, 1, 1);
        this.testMachine.calculateRoute(16, 28);
        this.testMachine.getScanner().setBackground(this.pane);
        this.pane.add(this.testMachine.getForm(), 1, 1);
        this.pane.add(this.testMachine.getScanner().getScannerHead(), 1, 1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void scanRouteNotNull() {
        assertTrue(this.testMachine.getScanRoute() != null);
    }

    @Test
    public void routeNotNull() {
        assertTrue(this.testMachine.getRoute() != null);
    }

    @Test
    public void machineFindsToGoal() {
        while (!this.testMachine.getRoute().isEmpty()) {
            this.testMachine.takeStep();
        }
        assertTrue(this.testMachine.getForm().getTranslateX() == (this.lastX * 40) && this.testMachine.getForm().getTranslateY() == (this.lastY * 40));
    }

    @Test
    public void scannerFindsToGoal() {
        while (!this.testMachine.getScanRoute().isEmpty()) {
            this.testMachine.scanNext();
        }
        assertTrue(this.testMachine.getScanner().getScannerHead().getTranslateX() == (this.lastX * 40) && this.testMachine.getScanner().getScannerHead().getTranslateY() == (this.lastY * 40));
    }

    @Test
    public void scanRouteSizeRight() {
        if (this.testMachine.getScanRoute().size() == 232) {
            return;
        }
        fail("ScanRoute size should be 232, but is: " + this.testMachine.getScanRoute().size());
    }

    @Test
    public void scannerDeletesEveryScan() {
        while (!this.testMachine.getScanRoute().isEmpty()) {
            this.testMachine.scanNext();
        }
        this.testMachine.getScanner().deleteScan();
        if (this.pane.getChildren().size() == 2) {
            return;
        }
        fail("Scanner doesn't delete it's scan right. Pane children size should be 2, but is: " + this.pane.getChildren().size());
    }

    @Test
    public void clearTranslateTest() {
        while (!this.testMachine.getScanRoute().isEmpty()) {
            this.testMachine.scanNext();
        }
        while (!this.testMachine.getRoute().isEmpty()) {
            this.testMachine.takeStep();
        }
        this.testMachine.clearTranslate();
        this.testMachine.getScanner().clearTranslate();
        if (this.testMachine.getForm().getTranslateX() == 0 && this.testMachine.getForm().getTranslateY() == 0 && this.testMachine.getScanner().getScannerHead().getTranslateX() == 0 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 0) {
            return;
        }
        fail("Translates should be machine: 0.0, scanner: 0.0, but was machine: "
                + this.testMachine.getForm().getTranslateX()
                + "."
                + this.testMachine.getForm().getTranslateY()
                + " scanner: "
                + this.testMachine.getScanner().getScannerHead().getTranslateX()
                + "."
                + this.testMachine.getScanner().getScannerHead().getTranslateY());
    }

    @Test
    public void scannerScansAtRightOrder() {
        int checkVal = 0;
        for (int i = 0; i < 10; i++) {
            this.testMachine.getScanRoute().pop();
            if (this.testMachine.getScanRoute().contains(new Vertex(80, 0))) {
                checkVal++;
            }
        }
        for (int i = 0; i < 10; i++) {
            this.testMachine.getScanRoute().pop();
        }
        if (this.testMachine.getScanRoute().poll().equals(new Vertex(280, 40))) {
            checkVal++;
        }
        for (int i = 0; i < 20; i++) {
            this.testMachine.getScanRoute().pop();
        }
        if (this.testMachine.getScanRoute().poll().equals(new Vertex(160, 320))) {
            checkVal++;
        }
        for (int i = 0; i < 26; i++) {
            this.testMachine.getScanRoute().pop();
        }
        if (this.testMachine.getScanRoute().poll().equals(new Vertex(80, 440))) {
            checkVal++;
        }
        for (int i = 0; i < 47; i++) {
            this.testMachine.getScanRoute().pop();
        }
        if (this.testMachine.getScanRoute().poll().equals(new Vertex(560, 0))) {
            checkVal++;
        }
        while (!this.testMachine.getScanRoute().isEmpty()) {
            this.testMachine.scanNext();
            if (this.testMachine.getScanRoute().contains(new Vertex(520, 1080))) {
                checkVal++;
            }
        }
        if (checkVal == 224) {
            return;
        }

        fail("CheckValue should be x, but is: " + checkVal);
    }
    
    @Test
    public void moveToTest() {
        int val = 0;
        this.testMachine.getScanner().moveTo(new Point2D(0,1));
        if (this.testMachine.getScanner().getScannerHead().getTranslateX() == 0.0 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 1.0) {
            val++;
        }
        this.testMachine.getScanner().moveTo(new Point2D(1,0));
        if (this.testMachine.getScanner().getScannerHead().getTranslateX() == 1.0 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 0.0) {
            val++;
        }
        this.testMachine.getScanner().moveTo(new Point2D(1,1));
        System.out.println(this.testMachine.getScanner().getScannerHead().getTranslateX() + " : " + this.testMachine.getScanner().getScannerHead().getTranslateY());
        if (this.testMachine.getScanner().getScannerHead().getTranslateX() == 1.0 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 1.0) {
            val++;
        }
        this.testMachine.getScanner().moveTo(new Point2D(40,57));
        if (this.testMachine.getScanner().getScannerHead().getTranslateX() == 40 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 57) {
            val++;
        }
        this.testMachine.getScanner().moveTo(new Point2D(500,100));
        if (this.testMachine.getScanner().getScannerHead().getTranslateX() == 500 && this.testMachine.getScanner().getScannerHead().getTranslateY() == 100) {
            val++;
        }
        if (val == 5) {
            return;
        }
        fail("Return value should be 5, but it was: " + val);   
    }
    
    @Test
    public void restoreRoutesTest() {
        while (!this.testMachine.getScanRoute().isEmpty()) {
            this.testMachine.scanNext();
        }
        while (!this.testMachine.getRoute().isEmpty()) {
            this.testMachine.takeStep();
        }
        this.testMachine.restoreRoute();
        this.testMachine.getScanner().restoreScanRoute();
        if (this.testMachine.getRoute().size() == 46 && this.testMachine.getScanRoute().size() == 232) {
            return;
        }
        fail("Routes not restored correctly. Route size should be 46, but was : " + this.testMachine.getRoute().size() + " ScanRoute size should be 232, but was: " + this.testMachine.getScanRoute().size());
    }

}

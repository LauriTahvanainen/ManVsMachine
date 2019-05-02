package mvsm.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import mvsm.algorithm.BFS;
import mvsm.algorithm.Algorithm;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameSelectionState extends State {

    private final StateManager sm;
    private final StackPane root;
    private final BorderPane algorithmSelect;
    private final BorderPane mapSelect;
    private String currentAlgorithmName;
    private Algorithm selectedAlgorithm;
    private Text currentAlgoText;
    private static final String RESOURCE_PATH = "/pictures/";

    public GameSelectionState(StateManager sm) {
        this.sm = sm;
        this.root = new StackPane();
        this.algorithmSelect = new BorderPane();
        this.mapSelect = new BorderPane();
        this.currentAlgorithmName = null;
        this.selectedAlgorithm = null;
        initPane();
    }

    @Override
    public int getStateId() {
        return 5;
    }

    @Override
    public Pane getCurrent() {
        return root;
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        this.algorithmSelect.setPrefSize(1200, 720);
        this.mapSelect.setPrefSize(1200, 720);
        this.root.getChildren().addAll(this.algorithmSelect, this.mapSelect);
        this.mapSelect.setVisible(false);

        this.currentAlgoText = new Text();
        Font font = new Font("Didact Gothic", 40);
        this.currentAlgoText.setFont(font);
    }

    @Override
    public void handleAction(ActionEvent t) {

        if (t.getTarget().getClass().equals(Button.class)) {
            Button target = (Button) t.getTarget();
            if (target.getId().equals("Menu")) {
                this.sm.setCurrentState(1);
                this.algorithmSelect.getChildren().clear();
                this.mapSelect.getChildren().clear();
                this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                return;
            }
            if (target.getId().equals("Back2Algo")) {
                this.algorithmSelect.setVisible(true);
                this.mapSelect.setVisible(false);
                return;
            }
            if (this.algorithmSelect.isVisible()) {
                if (target.getId().equals("BFS")) {
                    this.currentAlgoText.setText("Algorithm selected: BFS");
                    this.algorithmSelect.setVisible(false);
                    this.mapSelect.setVisible(true);
                    this.selectedAlgorithm = new BFS();
                    this.currentAlgorithmName = "BFS";
                }
                if (target.getId().equals("DFS")) {
                    this.currentAlgoText.setText("Algorithm selected: DFS");
                    this.currentAlgorithmName = "DFS";
                }
                if (target.getId().equals("DIJKSTRA")) {
                    this.currentAlgoText.setText("Algorithm selected: Dijkstra");
                    this.currentAlgorithmName = "Dijkstra";
                }
                if (target.getId().equals("A-STAR")) {
                    this.currentAlgoText.setText("Algorithm selected: A-Star");
                    this.currentAlgorithmName = "A-Star";
                }
            } else {
                if (target.getId().equals("map1")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map1");
                }
                if (target.getId().equals("map2")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map2");
                }
                if (target.getId().equals("map3")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map3");
                }
            }
        }
    }

    @Override
    public void restore() {
        this.mapSelect.setVisible(false);
        this.algorithmSelect.setVisible(true);

        //algorithmSelect navigation buttons
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        Button backToMenu = new Button("Back to Menu");
        backToMenu.setId("Menu");
        buttons.getChildren().add(backToMenu);
        this.algorithmSelect.setLeft(buttons);

        //mapSelect navigation buttons
        VBox buttons2 = new VBox();
        buttons2.setAlignment(Pos.CENTER);
        Button backToMenu2 = new Button("Back to Menu");
        backToMenu2.setId("Menu");
        Button backToAlgoSelect = new Button("Back to Algorithm Selection");
        backToAlgoSelect.setId("Back2Algo");
        buttons2.getChildren().addAll(backToMenu2, backToAlgoSelect);
        this.mapSelect.setLeft(buttons2);

        //algorithm select buttons
        Button bfs = new Button("BFS");
        bfs.setId("BFS");
        bfs.setPrefSize(100, 100);
        Button dfs = new Button("DFS");
        dfs.setId("DFS");
        dfs.setPrefSize(100, 100);
        Button dijkstra = new Button("Dijkstra");
        dijkstra.setId("DIJKSTRA");
        dijkstra.setPrefSize(100, 100);
        Button aStar = new Button("A-Star");
        aStar.setId("A-STAR");
        aStar.setPrefSize(100, 100);

        GridPane algoButtons = new GridPane();
        algoButtons.setAlignment(Pos.CENTER);
        algoButtons.setHgap(20);
        algoButtons.setVgap(40);
        algoButtons.addRow(0, bfs, dfs, dijkstra, aStar);

        this.algorithmSelect.setCenter(algoButtons);

        //Map select buttons
        Button map1 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map1.png"))));
        map1.setId("map1");
        Button map2 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map2.png"))));
        map2.setId("map2");
        Button map3 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map3.png"))));
        map3.setId("map3");

        GridPane mapButtons = new GridPane();
        mapButtons.setAlignment(Pos.CENTER);
        mapButtons.setHgap(20);
        mapButtons.setVgap(40);
        mapButtons.addRow(0, map1, map2);
        mapButtons.addRow(1, map3);
        this.mapSelect.setCenter(mapButtons);

        //current algorithm selection text
        HBox mapSelectTop = new HBox();
        mapSelectTop.setAlignment(Pos.CENTER);
        mapSelectTop.getChildren().add(this.currentAlgoText);
        this.mapSelect.setTop(mapSelectTop);
    }

    @Override
    public void restore(Algorithm a, String mapName) {

    }

}

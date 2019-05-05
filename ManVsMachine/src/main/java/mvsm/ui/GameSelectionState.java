package mvsm.ui;

import mvsm.algorithm.BFS;
import mvsm.algorithm.Algorithm;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mvsm.algorithm.DFS;
import mvsm.algorithm.Dijkstra;

/**
 * State that presents the user with a selection view to select the algorithm to
 * play against, and a map to play in. An algorithm is selected first, then the
 * map.
 */
public class GameSelectionState extends State {

    private final StateManager sm;
    private final StackPane root;
    private final BorderPane algorithmSelect;
    private final BorderPane mapSelect;
    private Algorithm selectedAlgorithm;
    private Text currentAlgoText;
    private final ScrollPane mapScroller;
    private static final String RESOURCE_PATH = "/pictures/";

    public GameSelectionState(StateManager sm) {
        this.sm = sm;
        this.mapScroller = new ScrollPane();
        this.root = new StackPane();
        this.algorithmSelect = new BorderPane();
        this.mapSelect = new BorderPane();
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
        this.root.setStyle("-fx-background-color: black;");
        this.algorithmSelect.setPrefSize(1200, 720);
        this.algorithmSelect.setPadding(new Insets(60, 5, 5, 5));
        this.mapSelect.setPrefSize(1200, 720);
        this.mapSelect.setPadding(new Insets(60, 107, 5, 107));
        this.root.getChildren().addAll(this.algorithmSelect, this.mapSelect);
        this.mapSelect.setVisible(false);

        this.currentAlgoText = new Text();
        this.currentAlgoText.getStyleClass().add("text-id");
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
                }
                if (target.getId().equals("DFS")) {
                    this.currentAlgoText.setText("Algorithm selected: DFS");
                    this.algorithmSelect.setVisible(false);
                    this.mapSelect.setVisible(true);
                    this.selectedAlgorithm = new DFS();
                }
                if (target.getId().equals("DIJKSTRA")) {
                    this.currentAlgoText.setText("Algorithm selected: Dijkstra");
                    this.mapSelect.setVisible(true);
                    this.algorithmSelect.setVisible(false);
                    this.selectedAlgorithm = new Dijkstra();
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
                if (target.getId().equals("map4")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map4");
                }
                if (target.getId().equals("map5")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map5");
                }
                if (target.getId().equals("map6")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map6");
                }
                if (target.getId().equals("map7")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map7");
                }
                if (target.getId().equals("map8")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map8");
                }
                if (target.getId().equals("map9")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map9");
                }
                if (target.getId().equals("map10")) {
                    this.sm.setCurrentState(2);
                    this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
                    this.sm.getCurrentState().restore(selectedAlgorithm, "map10");
                }
            }
        }
    }

    /**
     * Restores the map selection view to show only the algorithm selection.
     */
    @Override
    public void restore() {
        this.mapSelect.setVisible(false);
        this.algorithmSelect.setVisible(true);

        //algorithmSelect navigation buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);
        Button backToMenu = new Button("Back to Menu");
        backToMenu.setId("Menu");
        buttons.getChildren().add(backToMenu);
        this.algorithmSelect.setTop(buttons);

        //mapSelect navigation buttons and current algorithm text.
        HBox mapSelectTop = new HBox();
        mapSelectTop.setAlignment(Pos.CENTER);
        mapSelectTop.setSpacing(5);
        Button backToMenu2 = new Button("Back to Menu");
        backToMenu2.setId("Menu");
        backToMenu2.getStyleClass().add("mapSelect-button2");
        Button backToAlgoSelect = new Button("Back to Algorithm Selection");
        backToAlgoSelect.setId("Back2Algo");
        backToAlgoSelect.getStyleClass().add("mapSelect-button");
        mapSelectTop.getChildren().addAll(this.currentAlgoText, backToMenu2, backToAlgoSelect);
        this.mapSelect.setTop(mapSelectTop);

        //algorithm select buttons
        Button bfs = new Button("BFS");
        bfs.setId("BFS");
        bfs.setPrefSize(300, 300);
        Button dfs = new Button("DFS");
        dfs.setId("DFS");
        dfs.setPrefSize(300, 300);
        Button dijkstra = new Button("Dijkstra");
        dijkstra.setId("DIJKSTRA");
        dijkstra.setPrefSize(300, 300);

        GridPane algoButtons = new GridPane();
        algoButtons.setAlignment(Pos.CENTER);
        algoButtons.setHgap(20);
        algoButtons.setVgap(40);
        algoButtons.addRow(0, bfs, dfs, dijkstra);

        this.algorithmSelect.setCenter(algoButtons);

        //Map select buttons
        Button map1 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map1.png"))));
        map1.setId("map1");
        Button map2 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map2.png"))));
        map2.setId("map2");
        Button map3 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map3.png"))));
        map3.setId("map3");
        Button map4 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map4.png"))));
        map4.setId("map4");
        Button map5 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map5.png"))));
        map5.setId("map5");
        Button map6 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map6.png"))));
        map6.setId("map6");
        Button map7 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map7.png"))));
        map7.setId("map7");
        Button map8 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map8.png"))));
        map8.setId("map8");
        Button map9 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map9.png"))));
        map9.setId("map9");
        Button map10 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map10.png"))));
        map10.setId("map10");

        GridPane mapButtons = new GridPane();
        mapButtons.setAlignment(Pos.CENTER);
        mapButtons.addRow(0, map1, map2);
        mapButtons.addRow(1, map3, map4);
        mapButtons.addRow(2, map5, map6);
        mapButtons.addRow(3, map7, map8);
        mapButtons.addRow(4, map9, map10);
        this.mapScroller.setContent(mapButtons);
        this.mapSelect.setCenter(this.mapScroller);
    }

    @Override
    public void restore(Algorithm a, String mapName) {

    }

}

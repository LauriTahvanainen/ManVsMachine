package mvsm.ui;

import mvsm.algorithm.Algorithm;
import mvsm.dao.HighScoreUser;
import mvsm.dao.ScoreDao;
import mvsm.statemanagement.State;
import mvsm.statemanagement.StateManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * State for presenting high-scores to the user.
 */
public class HighscoreState extends State {

    private final ScoreDao scoreDao;
    private final StackPane root;
    private final BorderPane selectionPane;
    private final BorderPane scorePane;
    private final Button bfs;
    private final Button dfs;
    private final Button dijkstra;
    private final Button backToMenu;
    private final ScrollPane mapScroller;
    private final ScrollPane scoreScroller;
    private final StateManager sm;
    private final GridPane scoreList;
    private final Button map1;
    private final Button map2;
    private final Button map3;
    private final Button map4;
    private final Button map5;
    private final Button map6;
    private final Button backToMapSelect;
    private final Text algInfo;
    private final Text mapInfo;
    private final Text selectedAlgoText;
    private String selectedAlgo;
    private static final String RESOURCE_PATH = "/pictures/";

    /**
     *
     * @param sm For state management.
     * @param scoreDao For fetching score data from the database.
     */
    public HighscoreState(StateManager sm, ScoreDao scoreDao) {
        this.scoreDao = scoreDao;
        this.sm = sm;
        this.root = new StackPane();
        this.selectionPane = new BorderPane();
        this.scorePane = new BorderPane();
        this.mapScroller = new ScrollPane();
        this.scoreScroller = new ScrollPane();
        this.scoreList = new GridPane();
        this.bfs = new Button("BFS");
        this.dfs = new Button("DFS");
        this.dijkstra = new Button("Dijkstra");
        this.backToMenu = new Button("Back to Menu");
        this.backToMapSelect = new Button("Go Back");
        this.algInfo = new Text();
        this.mapInfo = new Text();
        this.selectedAlgo = "-";
        this.selectedAlgoText = new Text("Selected: " + selectedAlgo);
        this.map1 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map1.png"))));
        this.map2 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map2.png"))));
        this.map3 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map3.png"))));
        this.map4 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map4.png"))));
        this.map5 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map5.png"))));
        this.map6 = new Button(null, new ImageView(new Image(HighscoreState.class.getResourceAsStream(RESOURCE_PATH + "map6.png"))));
        initPane();
    }

    @Override
    public int getStateId() {
        return 4;
    }

    @Override
    public Pane getCurrent() {
        return this.root;
    }

    @Override
    public void update() {

    }

    @Override
    public void initPane() {
        Font font = new Font("Didact Gothic", 40);

        //selector
        this.root.getChildren().addAll(this.selectionPane, this.scorePane);

        //Buttons and selectionview
        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(this.backToMenu, this.bfs, this.dfs, this.dijkstra);
        this.selectionPane.setLeft(buttons);

        this.selectedAlgoText.setFont(font);
        HBox selectedPane = new HBox();
        selectedPane.setAlignment(Pos.CENTER);
        selectedPane.getChildren().add(this.selectedAlgoText);
        this.selectionPane.setTop(selectedPane);

        //Mapscrolling view
        this.selectionPane.setCenter(this.mapScroller);
        this.mapScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //map select view
        this.map1.setId("map1");
        this.map2.setId("map2");
        this.map3.setId("map3");
        this.map4.setId("map4");
        this.map5.setId("map5");
        this.map6.setId("map6");

        //scoreview
        VBox buttons2 = new VBox();
        HBox info = new HBox();
        info.setSpacing(40);
        this.algInfo.setFont(font);
        this.mapInfo.setFont(font);
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(this.algInfo, this.mapInfo);
        buttons2.setAlignment(Pos.CENTER);
        buttons2.getChildren().add(this.backToMapSelect);
        this.scorePane.setLeft(buttons2);
        this.scorePane.setMaxSize(1120, 640);
        this.scorePane.setCenter(this.scoreScroller);
        this.scorePane.setTop(info);
        this.scorePane.setStyle("-fx-background-color: rgba(220, 220, 250, 0.95); -fx-background-radius: 1;");
        this.scoreScroller.setStyle("-fx-background: rgba(220, 220, 250, 0.1); -fx-background-radius: 1; -fx-background-color: transparent;");
        this.scoreScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scorePane.setVisible(false);
        //list
        this.scoreList.setMinWidth(1000);
        this.scoreList.setHgap(15);
        this.scoreList.setVgap(15);
        this.scoreList.setAlignment(Pos.CENTER);
        this.scoreList.getColumnConstraints().add(0, new ColumnConstraints(50, 50, 50));
        this.scoreList.getColumnConstraints().add(1, new ColumnConstraints(400, 400, 400));
        this.scoreList.getColumnConstraints().add(2, new ColumnConstraints(350, 350, 350));
        this.scoreScroller.setContent(this.scoreList);
    }

    /**
     * Handles the button presses. When an algorithm is chosen, maps are shown
     * according to the selected algorithm in a scroll view. When a map is
     * selected, an list of scores is called from the ScoreDao. These are formed
     * in to a table and put in to a scroll view.
     *
     * @param t ActionEvent, the button pressed.
     */
    @Override
    public void handleAction(ActionEvent t) {
        if (t.getTarget().equals(this.backToMenu)) {
            this.sm.setCurrentState(1);
            this.sm.setSceneRoot(this.sm.getCurrentState().getCurrent());
        }
        if (t.getTarget().equals(this.bfs)) {
            showMaps();
            setSelectedAlgo("BFS");
        }
        if (t.getTarget().equals(this.dfs)) {
            showMaps();
            setSelectedAlgo("DFS");
        }
        if (t.getTarget().equals(this.dijkstra)) {
            showMaps();
            setSelectedAlgo("Dijkstra");
        }
        if (t.getTarget().equals(this.map1)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map1"), "map1", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.map2)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map2"), "map2", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.map3)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map3"), "map3", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.map4)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map4"), "map4", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.map5)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map5"), "map5", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.map6)) {
            try {
                formScoreList(this.scoreDao.listAllSorted(this.selectedAlgo, "map6"), "map6", this.selectedAlgo);
            } catch (SQLException ex) {
                return;
            }
            this.scorePane.setVisible(true);
            this.selectionPane.setDisable(true);
        }
        if (t.getTarget().equals(this.backToMapSelect)) {
            this.scorePane.setVisible(false);
            this.selectionPane.setDisable(false);
        }
    }

    @Override
    public void restore() {
        this.mapScroller.setContent(null);
        this.selectedAlgo = "-";
        this.selectedAlgoText.setText("Selected: -");
    }

    @Override
    public void restore(Algorithm a, String map) {

    }

    private void formScoreList(ArrayList<HighScoreUser> list, String map, String algo) throws SQLException {
        HighScoreUser currentUser = this.scoreDao.listUser(algo, this.sm.getCurrentUser().getUsername());
        this.scoreList.getChildren().clear();
        this.algInfo.setText(algo);
        this.mapInfo.setText("Map " + map.charAt(3));
        Font font = new Font("Didact Gothic", 60);
        Text username = new Text("Username");
        Text score = new Text("Score");
        username.setFont(font);
        score.setFont(font);
        this.scoreList.add(username, 1, 0);
        this.scoreList.add(score, 2, 0);
        Font font2 = new Font("Didact Gothic", 40);
        Font font3 = new Font("Didact Gothic", 30);
        Text curUser = new Text(currentUser.getName());
        Text curUserScore = new Text(currentUser.getScoreAsString(map));
        curUser.setFont(font2);
        curUser.setUnderline(true);
        curUserScore.setFont(font2);
        curUserScore.setUnderline(true);
        this.scoreList.add(curUser, 1, 1);
        this.scoreList.add(curUserScore, 2, 1);
        int i = 1;
        for (HighScoreUser user : list) {
            Text name = new Text(user.getName());
            name.setFont(font2);
            Text nameScore = new Text(user.getScoreAsString(map));
            nameScore.setFont(font2);
            Text number = new Text(String.valueOf(i));
            if (user.getName().equals(this.sm.getCurrentUser().getUsername())) {
                name.setUnderline(true);
                nameScore.setUnderline(true);
                number.setUnderline(true);
            }
            number.setFont(font3);
            this.scoreList.addRow(i + 1, number, name, nameScore);
            i++;
            if (i == 21) {
                break;
            }
        }
    }

    private void showMaps() {
        GridPane maps = new GridPane();

        maps.setAlignment(Pos.CENTER);
        maps.setHgap(10);
        maps.setVgap(10);

        maps.getChildren().clear();
        maps.addRow(0, this.map1, this.map2);
        maps.addRow(1, this.map3, this.map4);
        maps.addRow(2, this.map5, this.map6);
        this.mapScroller.setContent(maps);
    }

    private void setSelectedAlgo(String algo) {
        this.selectedAlgo = algo;
        this.selectedAlgoText.setText("Selected: " + algo);
    }

}

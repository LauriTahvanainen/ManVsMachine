package dao;

import java.util.ArrayList;

public class HighScoreUser {
    private String name;
    private ArrayList<MapScore> mapHighScores;

    public HighScoreUser(String name) {
        this.name = name;
        this.mapHighScores = new ArrayList<>();
    }

    public ArrayList<MapScore> getMapHighScore() {
        return mapHighScores;
    }

    public String getName() {
        return name;
    }
    
    public void addScore(String map, int score) {
        this.mapHighScores.add(new MapScore(map, score));
    }
    
    public MapScore getMapScore(int map) {
        return this.mapHighScores.get(map - 1);
    }
}

package mvsm.dao;

import java.util.HashMap;

public class HighScoreUser {
    private String name;
    private HashMap<String, Integer> mapHighScores;

    public HighScoreUser(String name) {
        this.name = name;
        this.mapHighScores = new HashMap<>();
    }
    
    public int getScore(String map) {
        return this.mapHighScores.get(map);
    }
    
    public String getScoreAsString(String map) {
        return String.valueOf(this.mapHighScores.get(map));
    }

    public String getName() {
        return name;
    }
    
    public void addScore(String map, int score) {
        this.mapHighScores.putIfAbsent(map, score);
    }
    
    public void updateScore(String map, int score) {
        this.mapHighScores.put(map, score);
    }
}

package mvsm.dao;

import java.util.HashMap;

/**
 * An object for encapsulating high-scores of one user in different maps. Does
 * not know about the algorithm the score was made against. Scores are saved in
 * a HashMap with maps as keys, and scores as values.
 *
 * @see mvsm.dao.DatabaseScoreDao
 */
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

    /**
     * Add a new score to the HashMap.
     *
     * @param map The key. The map the score was made in.
     * @param score The value. The score.
     */
    public void addScore(String map, int score) {
        this.mapHighScores.putIfAbsent(map, score);
    }

    /**
     * Updates the score in the map given as a parameter.
     *
     * @param map To update the score in.
     * @param score The score to update the old value to.
     */
    public void updateScore(String map, int score) {
        this.mapHighScores.put(map, score);
    }
}

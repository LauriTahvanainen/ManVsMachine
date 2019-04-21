package dao;

public class MapScore {
    private int score;
    private String map;

    public MapScore(String map, int score) {
        this.score = score;
        this.map = map;
    }

    public String getMap() {
        return map;
    }

    public int getScore() {
        return score;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Map: " + this.map + ", Score: " + this.score;
    }
    
    
}

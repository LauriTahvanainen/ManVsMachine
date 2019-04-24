package mvsm.dao;

import javafx.scene.paint.Color;


public class User {
    private String username;
    private Color color;

    public User(String username, Color color) {
        this.username = username;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public void setColorM(Color color) {
        this.color = color;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}

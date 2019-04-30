package mvsm.dao;

import javafx.scene.paint.Color;

/**
 * An object for encapsulating the data involving an user. Used by the DAOs.
 * Does not save high-scores, that is done by HighScoreUser
 *
 * @see mvsm.dao.UserDao
 * @see mvsm.dao.HighScoreUser
 */
public class User {

    private String username;
    private int password;
    private Color color;

    public User(String username, Color color, int password) {
        this.username = username;
        this.color = color;
        this.password = password;
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

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

}

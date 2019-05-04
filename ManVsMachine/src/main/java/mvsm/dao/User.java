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
    private Color portalColor;
    private String texture;

    public User(String username, Color color, int password, String texture) {
        this.username = username;
        this.portalColor = color;
        this.password = password;
        this.texture = texture;
    }

    public Color getPortalColor() {
        return portalColor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public String getTexture() {
        return texture;
    }

    public void setPortalColor(Color portalColor) {
        this.portalColor = portalColor;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

}

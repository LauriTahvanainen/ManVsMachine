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
    private boolean knightOpen;
    private boolean demonOpen;

    public User(String username, Color color, int password, String texture, int knightOpen, int demonOpen) {
        this.username = username;
        this.portalColor = color;
        this.password = password;
        this.texture = texture;
        this.knightOpen = knightOpen == 1;
        this.demonOpen = demonOpen == 1;
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

    public boolean isDemonOpen() {
        return demonOpen;
    }

    public boolean isKnightOpen() {
        return knightOpen;
    }

    public void setDemonOpen(boolean demonOpen) {
        this.demonOpen = demonOpen;
    }

    public void setKnightOpen(boolean knightOpen) {
        this.knightOpen = knightOpen;
    }

}

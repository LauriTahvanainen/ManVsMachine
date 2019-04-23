package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.paint.Color;

public class DatabaseUserDao implements UserDao {

    private static final String USERTABLE_INIT = "CREATE TABLE IF NOT EXISTS Username(username VARCHAR(16) PRIMARY KEY, password VARCHAR(64), red INTEGER, green INTEGER, blue INTEGER);";
    private static final String BFS_INIT = "CREATE TABLE IF NOT EXISTS BFS(Username VARCHAR(64) PRIMARY KEY, map1 INTEGER DEFAULT 0, map2 INTEGER DEFAULT 0, map3 INTEGER DEFAULT 0);";
    private final Connector connector;
    
    public DatabaseUserDao(Connector conn) throws Exception {
        this.connector = conn;
        initDatabase();
    }

    @Override
    public int create(String userName) throws SQLException {
        int ret = checkUserNameAdd(userName);
        if (ret == 3 || ret == 4 || ret == 16) {
            return ret;
        }
        PreparedStatement stmt;
        try (Connection conn = this.connector.openConnection()) {
            stmt = conn.prepareStatement("INSERT INTO Username(username,password,red,green,blue) VALUES(?,'-',255,0,0)");
            try {
                stmt.setString(1, userName);
                stmt.executeUpdate();
            } catch (SQLException e) {
                conn.close();
                stmt.close();
                return 0;
            }
        }
        stmt.close();
        return 1;
    }

    @Override
    public int update(String oldUsername, String newUsername) throws SQLException {
        int ret = checkUserNameUpdate(oldUsername, newUsername);
        if (ret == 2 || ret == 3 || ret == 4 || ret == 16) {
            return ret;
        }
        PreparedStatement username, bfs;
        try (Connection conn = this.connector.openConnection()) {
            conn.setAutoCommit(false);
            username = conn.prepareStatement("UPDATE Username SET username = ? WHERE username = ?");
            bfs = conn.prepareStatement("UPDATE BFS SET username = ? WHERE username = ?");
            try {
                username.setString(1, newUsername);
                username.setString(2, oldUsername);
                bfs.setString(1, newUsername);
                bfs.setString(2, oldUsername);
                username.executeUpdate();
                bfs.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                username.close();
                bfs.close();
                conn.rollback();
                return 0;
            }
        }
        username.close();
        bfs.close();
        return 1;
    }

    private void initDatabase() throws SQLException {
        Connection conn = this.connector.openConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(USERTABLE_INIT);
            stmt.execute(BFS_INIT);
        }
    }

    @Override
    public User read(String username) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT username,red,green,blue FROM Username WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(rs.getString("username"), Color.rgb(rs.getInt("red"), rs.getInt("green"), rs.getInt("blue")));
        }
    }

    @Override
    public void updateColor(String username, int red, int green, int blue) throws SQLException {
        if (checkColors(red, green, blue)) {
            return;
        }
        PreparedStatement stmt;
        try (Connection conn = this.connector.openConnection()) {
            stmt = conn.prepareStatement("UPDATE Username SET red = ?, green = ?, blue = ? WHERE username = ?");
            try {
                stmt.setInt(1, red);
                stmt.setInt(2, green);
                stmt.setInt(3, blue);
                stmt.setString(4, username);
                stmt.executeUpdate();
            } catch (SQLException e) {
                conn.close();
                stmt.close();
                return;
            }
        }
        stmt.close();
    }

    private int checkUserNameUpdate(String oldUsername, String newUsername) {
        if (newUsername.equals(oldUsername)) {
            return 2;
        }
        return checkUserNameAdd(newUsername);
    }

    private int checkUserNameAdd(String newUsername) {
        if (newUsername.contains(" ")) {
            return 3;
        }
        int length = newUsername.trim().length();
        if (length < 4) {
            return 4;
        }
        if (length > 16) {
            return 16;
        }
        return 1;
    }

    private boolean checkColors(int red, int green, int blue) {
        return red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0;
    }
}

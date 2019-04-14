package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.scene.paint.Color;

public class DatabaseUserDao implements UserDao {

    private static final String USERTABLE_INIT = "CREATE TABLE IF NOT EXISTS Username(username VARCHAR(16) PRIMARY KEY, password VARCHAR(64), red INTEGER, green INTEGER, blue INTEGER);";
    private static final String BFS_INIT = "CREATE TABLE IF NOT EXISTS BFS(user varchar(64) PRIMARY KEY, map1 varchar(16));";
    private final String databasepath;

    public DatabaseUserDao() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        this.databasepath = properties.getProperty("databasepath");
        initDatabase(this.databasepath);
    }

    @Override
    public int create(String userName) throws SQLException {
        int ret = checkUserNameAdd(userName);
        if (ret == 3 || ret == 4 || ret == 16) {
            return ret;
        }
        PreparedStatement stmt;
        try (Connection conn = this.openConnection()) {
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
        PreparedStatement stmt;
        try (Connection conn = this.openConnection()) {
            stmt = conn.prepareStatement("UPDATE Username SET username = ? WHERE username = ?");
            try {
                stmt.setString(1, newUsername);
                stmt.setString(2, oldUsername);
                stmt.executeUpdate();
            } catch (SQLException e) {
                stmt.close();
                return 0;
            }
        }
        stmt.close();
        return 1;
    }

    private void initDatabase(String path) throws SQLException {
        Connection conn = this.openConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(USERTABLE_INIT);
            stmt.execute(BFS_INIT);
        }
    }

    @Override
    public User read(String username) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT username,red,green,blue FROM Username WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(rs.getString("username"), Color.rgb(rs.getInt("red"), rs.getInt("green"), rs.getInt("blue")));
        }
    }

    @Override
    public Connection openConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + this.databasepath);
        } catch (SQLException e) {
            return null;
        }
        return conn;
    }

    public void updateColor(String username, int red, int green, int blue) throws SQLException {
        PreparedStatement stmt;
        try (Connection conn = this.openConnection()) {
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
}

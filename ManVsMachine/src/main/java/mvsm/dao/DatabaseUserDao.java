package mvsm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.paint.Color;

/**
 * A class that implements the UserDao and handles the interaction with the
 * database that involves Users. Also handles the initialization of the
 * database.
 *
 * @see mvsm.dao.User
 */
public class DatabaseUserDao implements UserDao {

    private static final String USERTABLE_INIT = "CREATE TABLE IF NOT EXISTS Username(username VARCHAR(16) PRIMARY KEY, password INTEGER, red INTEGER, green INTEGER, blue INTEGER);";
    private static final String BFS_INIT = "CREATE TABLE IF NOT EXISTS Bfs(username VARCHAR(64) PRIMARY KEY, map1 INTEGER DEFAULT 0, map2 INTEGER DEFAULT 0, map3 INTEGER DEFAULT 0, map4 INTEGER DEFAULT 0, map5 INTEGER DEFAULT 0, map6 INTEGER DEFAULT 0);";
    private static final String DFS_INIT = "CREATE TABLE IF NOT EXISTS Dfs(username VARCHAR(64) PRIMARY KEY, map1 INTEGER DEFAULT 0, map2 INTEGER DEFAULT 0, map3 INTEGER DEFAULT 0, map4 INTEGER DEFAULT 0, map5 INTEGER DEFAULT 0, map6 INTEGER DEFAULT 0);";
    private static final String DIJKSTRA_INIT = "CREATE TABLE IF NOT EXISTS Dijkstra(username VARCHAR(64) PRIMARY KEY, map1 INTEGER DEFAULT 0, map2 INTEGER DEFAULT 0, map3 INTEGER DEFAULT 0, map4 INTEGER DEFAULT 0, map5 INTEGER DEFAULT 0, map6 INTEGER DEFAULT 0);";
    private final Connector connector;
    private final StringChecker checker;

    public DatabaseUserDao(Connector conn) throws Exception {
        this.checker = new StringChecker();
        this.connector = conn;
    }

    @Override
    public int create(String userName, String password) throws SQLException {
        int ret = this.checker.checkUserNameAdd(userName);
        if (ret == 3 || ret == 4 || ret == 16) {
            return ret;
        }
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("INSERT INTO Username(username,password,red,green,blue) VALUES(?,?,255,0,0)")) {
            stmt.setString(1, userName);
            stmt.setInt(2, password.hashCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public int update(String oldUsername, String newUsername) throws SQLException {
        int ret = this.checker.checkUserNameUpdate(oldUsername, newUsername);
        if (ret == 2 || ret == 3 || ret == 4 || ret == 16) {
            return ret;
        }
        try (Connection conn = this.connector.openConnection();
                PreparedStatement bfs = conn.prepareStatement("UPDATE BFS SET username = ? WHERE username = ?");
                PreparedStatement dfs = conn.prepareStatement("UPDATE DFS SET username = ? WHERE username = ?");
                PreparedStatement dijkstra = conn.prepareStatement("UPDATE Dijkstra SET username = ? WHERE username = ?");
                PreparedStatement username = conn.prepareStatement("UPDATE Username SET username = ? WHERE username = ?")) {
            conn.setAutoCommit(false);
            setStrings(oldUsername, newUsername, username);
            setStrings(oldUsername, newUsername, bfs);
            setStrings(oldUsername, newUsername, dfs);
            setStrings(oldUsername, newUsername, dijkstra);
            executeUpdates(username, bfs, dfs, dijkstra);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            return -1;
        }
        return 1;
    }

    /**
     * Initializes the database on startup.
     *
     * @throws Exception if the path to the database is incorrect;
     */
    public void initDatabase() throws Exception {
        Connection conn = this.connector.openConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(USERTABLE_INIT);
        stmt.execute(BFS_INIT);
        stmt.execute(DFS_INIT);
        stmt.execute(DIJKSTRA_INIT);
        stmt.close();
        conn.close();
    }

    @Override
    public User read(String username) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.connector.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT username, password,red,green,blue FROM Username WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(rs.getString("username"), Color.rgb(rs.getInt("red"), rs.getInt("green"), rs.getInt("blue")), rs.getInt("password"));
        }
    }

    @Override
    public boolean updateColor(String username, int red, int green, int blue) throws SQLException {
        if (checkColors(red, green, blue)) {
            return false;
        }
        try (Connection conn = this.connector.openConnection();
                PreparedStatement stmt = conn.prepareStatement("UPDATE Username SET red = ?, green = ?, blue = ? WHERE username = ?")) {
            stmt.setInt(1, red);
            stmt.setInt(2, green);
            stmt.setInt(3, blue);
            stmt.setString(4, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public int updatePassword(String username, String newPassword) throws SQLException {
        try (Connection conn = this.connector.openConnection();
                PreparedStatement usernameTable = conn.prepareStatement("UPDATE Username SET password = ? WHERE username = ?")) {
            usernameTable.setInt(1, newPassword.hashCode());
            usernameTable.setString(2, username);
            usernameTable.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
        return 1;
    }

    private boolean checkColors(int red, int green, int blue) {
        return red > 255 || red < 0 || green > 255 || green < 0 || blue > 255 || blue < 0;
    }

    private void setStrings(String oldUserName, String newUserName, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, newUserName);
        stmt.setString(2, oldUserName);
    }

    private void executeUpdates(PreparedStatement stmt1, PreparedStatement stmt2, PreparedStatement stmt3, PreparedStatement stmt4) throws SQLException {
        stmt1.executeUpdate();
        stmt2.executeUpdate();
        stmt3.executeUpdate();
        stmt4.executeUpdate();
    }

}

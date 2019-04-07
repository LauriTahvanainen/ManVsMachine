package dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseUserDao implements UserDao {

    private static final String USERTABLEINIT = "CREATE TABLE IF NOT EXISTS Username(username varchar(16) PRIMARY KEY, password varchar(64));";
    private static final String MAP1INIT = "CREATE TABLE IF NOT EXISTS BFS(map1 varchar(16), user varchar(64) PRIMARY KEY);";
    private String databasepath;

    public DatabaseUserDao() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        this.databasepath = properties.getProperty("databasepath");
        initDatabase(this.databasepath);
    }

    @Override
    public boolean create(String userName) throws SQLException {
        PreparedStatement stmt;
        try (Connection conn = this.openConnection()) {
            stmt = conn.prepareStatement("INSERT INTO Username(username,password) VALUES(?,'-')");
            try {
                stmt.setString(1, userName);
                stmt.executeUpdate();
            } catch (SQLException e) {
                conn.close();
                stmt.close();
                return false;
            }
        }
        stmt.close();
        return true;
    }

    @Override
    public boolean update(String oldUsername, String newUsername) throws SQLException {

        return true;
    }

    private void initDatabase(String path) throws SQLException {
        Connection conn = this.openConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(USERTABLEINIT);
            stmt.execute(MAP1INIT);
        }
    }

    @Override
    public String read(String username) throws SQLException {
        ResultSet rs;
        try (Connection conn = this.openConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT username FROM Username WHERE username = ?")) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rs.getString("username");
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
}

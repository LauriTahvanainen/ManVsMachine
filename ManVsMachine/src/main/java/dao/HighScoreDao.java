package dao;

import java.sql.*;

public interface HighScoreDao {

    boolean create(String userName, String map, int score) throws SQLException;

    int read(String username, String map) throws SQLException;

    boolean update(String userName, int score) throws SQLException;
}

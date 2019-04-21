package dao;

import java.sql.*;
import java.util.ArrayList;

public interface ScoreDao {

    int createDefault(String username, String algorithm) throws SQLException;

    int read(String algorithm, String username, String map) throws SQLException;

    boolean updateScore(String algorithm, String username, String map, int score) throws SQLException;

    ArrayList<HighScoreUser> listAll(String algorithm) throws SQLException ;

    HighScoreUser listUser(String algorithm, String username) throws SQLException ;
}

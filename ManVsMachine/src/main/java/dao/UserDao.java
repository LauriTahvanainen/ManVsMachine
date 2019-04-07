package dao;

import java.sql.*;

public interface UserDao {
    boolean create(String userName) throws SQLException;

    String read(String username) throws SQLException;

    boolean update(String oldUsername, String newUsername) throws SQLException;
    
    Connection openConnection();
}

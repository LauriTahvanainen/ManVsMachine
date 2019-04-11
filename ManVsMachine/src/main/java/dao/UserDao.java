package dao;

import java.sql.*;

public interface UserDao {
    int create(String userName) throws SQLException;

    User read(String username) throws SQLException;

    int update(String oldUsername, String newUsername) throws SQLException;
    
    Connection openConnection();
}

package mvsm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private final String databasePath;

    public Connector(String databasePath) {
        this.databasePath = databasePath;
    }
    
    public Connection openConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (SQLException e) {
            return null;
        }
    }
}

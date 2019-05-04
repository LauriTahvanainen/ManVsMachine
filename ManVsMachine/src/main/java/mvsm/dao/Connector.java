package mvsm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class for encapsulating creating a connection to a database.
 */
public class Connector {

    private final String databasePath;

    public Connector(String databasePath) {
        this.databasePath = databasePath;
    }

    /**
     * Connects to a database with the path that was given in the constructor.
     *
     * @return A new Connection.
     */
    public Connection openConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (SQLException e) {
            return null;
        }
    }
}

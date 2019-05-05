package mvsm.dao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Interface for handling High-score tables in a database. The tables are
 * structured as tables that have an algorithm as a name, an username as the
 * first column and a primary key, and different maps as their own columns.
 *
 * @see mvsm.dao.HighScoreUser
 */
public interface ScoreDao {

    /**
     * Creates a new HighScoreUser in the database with default score values.
     *
     * @param username Username.
     * @param algorithm The Algorithm which to create the default values for.
     * @return 1 if the default values were created successfully, 0 if an
     * exception was thrown, for example if the username already existed in the
     * table.
     * @throws SQLException when the table already contains the given username.
     * @see mvsm.ui.MenuState#restore()
     */
    int createDefault(String username, String algorithm) throws SQLException;

    /**
     * Read a high-score from the database. Against an algorithm, of a username,
     * in one map.
     *
     * @param algorithm The table to search from.
     * @param username The username of which to search for.
     * @param map The map of which to search for.
     * @return The score as an Integer, or -1 if there was no data on the given
     * user, or if an exception was thrown.
     * @throws SQLException in case of error.
     */
    int read(String algorithm, String username, String map) throws SQLException;

    /**
     * Updates the score of an user, on a given algorithm and map.
     *
     * @param algorithm To update the score.
     * @param username Of who to update.
     * @param map The map to update.
     * @param score The new new score to update to.
     * @return True if the update was successful, false if it was not.
     * @throws SQLException in case of error.
     */
    boolean updateScore(String algorithm, String username, String map, int score) throws SQLException;

    /**
     * Lists all the high-scores in specific table given as a parameter in the
     * form of an ArrayList containing HighScoreUser objects, sorted by the map
     * given as a parameter. Sorted in an descending order.
     *
     * @param algorithm The table to search from.
     * @param mapToSortBy The map to sort the results by.
     * @return An ArrayList of HighScoreUser objects, or an empty list if the
     * table was empty.
     * @throws SQLException in case of error.
     * @see mvsm.dao.HighScoreUser
     */
    ArrayList<HighScoreUser> listAllSorted(String algorithm, String mapToSortBy) throws SQLException;

    /**
     * Get an HighScoreuser object from the database determined by the given
     * parameters.
     *
     * @param algorithm The users scores against this algorithm.
     * @param username The user who's scores to return.
     * @return A new HighScoreUser object containing the high-scores of the
     * user, or null if there was not a row in the table with the specified
     * username. against one algorithm, in all the maps.
     * @throws SQLException In case of error.
     * @see mvsm.dao.HighScoreUser
     */
    HighScoreUser listUser(String algorithm, String username) throws SQLException;
}

package mvsm.dao;

import java.sql.*;

/**
 * Interface for defining the methods of a Data Access Object that handles
 * saving user-data to and retrieving user-data from a database. structures.
 * Used in this game with a database.
 *
 * @see mvsm.dao.User
 */
public interface UserDao {

    /**
     * Create a new User defined by the given parameters and save it to the
     * database.
     *
     * @param userName Username of the user.
     * @param password Password of the user.
     * @return 1 if the user was created and saved in to the database
     * successfully, 0 if the database already contained the given username, 3
     * if the username contained a space-character, 4 if the username-length was
     * smaller than 4, and 16 if the length was bigger than 16.
     * @throws SQLException
     */
    int create(String userName, String password) throws SQLException;

    /**
     * Read the user from the database determined by the username given as a
     * parameter.
     *
     * @param username The username to search for.
     * @return A new User object if the database contains an User with the given
     * username or null if it does not.
     * @throws SQLException
     */
    User read(String username) throws SQLException;

    /**
     * Update the username of an User in the database.
     *
     * @param oldUsername The old username.
     * @param newUsername The new username to be updated to.
     * @return 1 if the username was updated successfully, -1 if it was
     * unsuccessful (for example because the old username did not exist in the
     * database), 2 if the old username was the same as the new username, 3 if
     * the username contained a space-character, 4 if the username-length was
     * smaller than 4, and 16 if the length was bigger than 16.
     * @throws SQLException
     */
    int update(String oldUsername, String newUsername) throws SQLException;

    /**
     * Update the RGB values of a given User in the database.
     *
     * @param username The User to be updated.
     * @param red The 256 bit Integer value for the red color.
     * @param green The 256 bit Integer value for the green color.
     * @param blue The 256 bit Integer value for the blue color.
     * @return True if the update was done successfully, else false.
     * @throws SQLException
     */
    boolean updateColor(String username, int red, int green, int blue) throws SQLException;

    /**
     * Update the password of an User in the database.
     *
     * @param username The User to be updated.
     * @param newPassword The new password.
     * @return 1 if the update was done successfully, -1 if an SQLException was
     * caught.
     * @throws SQLException
     */
    int updatePassword(String username, String newPassword) throws SQLException;
}

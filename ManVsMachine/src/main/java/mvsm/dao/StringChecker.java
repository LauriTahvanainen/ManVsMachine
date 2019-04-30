package mvsm.dao;

/**
 * An utility class for checking usernames and passwords for correctness.
 *
 * @see mvsm.dao.DatabaseUserDao
 */
public class StringChecker {

    /**
     * A pre-check before updating an User's username with the DatabaseUserDao.
     * Checks for length of the new username, if the usernames are the same and
     * if the new username contains a space-character.
     *
     * @param oldUsername The current username of the User.
     * @param newUsername The new username.
     * @return 1 if the inputs pass the checks, 2 if the old and the new
     * username were equal, 3 if the new username contained a space-character, 4
     * if the length of the new username was less than 4, and 16 if the length
     * was more than 16.
     *
     * @see mvsm.dao.UserDao#update(java.lang.String, java.lang.String)
     */
    public int checkUserNameUpdate(String oldUsername, String newUsername) {
        if (newUsername.equals(oldUsername)) {
            return 2;
        }
        return checkUserNameAdd(newUsername);
    }

    /**
     * A pre-check before adding an User to a database with the DatabaseUserDao.
     * Checks for length of the new username and if the new username contains a
     * space-character.
     *
     * @param newUsername The username to be added.
     * @return 1 if the username passes the checks, 3 if the username contained
     * a space-character, 4 if the length of the username was less than 4, and
     * 16 if the length was more than 16.
     */
    public int checkUserNameAdd(String newUsername) {
        if (newUsername.contains(" ")) {
            return 3;
        }
        int length = newUsername.trim().length();
        if (length < 4) {
            return 4;
        }
        if (length > 16) {
            return 16;
        }
        return 1;
    }

    /**
     * Check for the correctness of a password. The second parameter should be
     * the confirmation of the password.
     *
     * @param pW The password
     * @param pWConf The confirmation of the password.
     * @return 0 if the passwords pass the checks, 1 if the passwords do not
     * match, 6 if the length of the password is less than 7, 16 if the length
     * is more than 16, 2 if the password contains a character other than a
     * digit or a letter, 3 if the password does not contain a number, 4 if the
     * password does not contain a capital letter, 5 if the password does not
     * contain a lowercase letter.
     *
     * @see mvsm.dao.UserDao#updatePassword(java.lang.String, java.lang.String)
     * @see mvsm.dao.UserDao#create(java.lang.String, java.lang.String)
     */
    public int checkPassword(String pW, String pWConf) {
        if (!pW.equals(pWConf)) {
            return 1;
        } else if (pW.length() < 7) {
            return 6;
        } else if (pW.length() > 16) {
            return 16;
        }
        return checkPasswordLetters(pW);
    }

    private int checkPasswordLetters(String pW) {
        boolean number = false, capitalLetter = false, lowercaseLetter = false;
        for (int i = 0; i < pW.length(); i++) {
            int c = pW.codePointAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return 2;
            }
            lowercaseLetter = Character.isLowerCase(c) || lowercaseLetter;
            number = Character.isDigit(c) || number;
            capitalLetter = Character.isUpperCase(c) || capitalLetter;
        }
        if (!number) {
            return 3;
        } else if (!capitalLetter) {
            return 4;
        } else if (!lowercaseLetter) {
            return 5;
        }
        return 0;
    }
}

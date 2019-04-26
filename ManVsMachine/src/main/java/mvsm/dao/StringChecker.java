package mvsm.dao;

public class StringChecker {

    public int checkUserNameUpdate(String oldUsername, String newUsername) {
        if (newUsername.equals(oldUsername)) {
            return 2;
        }
        return checkUserNameAdd(newUsername);
    }

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

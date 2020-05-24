package model.exceptions;

/**
 * Custom exception that is thrown when the app is trying to deserialize empty user.txt.
 *
 * @author Jakub Povinec
 */
public class NoRegisteredUsersException extends Exception {

    /**
     * Constructor.
     */
    public NoRegisteredUsersException() {
        System.out.println("no1 has registered yet.");
    }
}

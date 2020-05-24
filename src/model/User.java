package model;

import java.io.Serializable;

/**
 * Model class user.
 * It's serialized in abstract class Serialization so it has to implements Serializable.
 * It's attributes are username and password.
 *
 * @author Jakub Povinec
 */
public class User implements Serializable {
    private String username;
    private String password;

    /**
     * Constructor.
     *
     * @param username user's username
     * @param password user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter method that returns user's username.
     *
     * @return user's username of type String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method that returns user's password.
     *
     * @return user's password of type String
     */
    public String getPassword() {
        return password;
    }
}

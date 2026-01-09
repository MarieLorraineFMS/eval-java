package fr.fms.model;

/**
 * Represents a user account.
 *
 * A UserAccount contains authentication data only:
 * - login
 * - password hash
 *
 * Just basics=good security practice
 */
public class UserAccount {

    /** Db identifier (0 means "not persisted yet") */
    private final int id;

    /** User login (username) */
    private final String login;

    /** Hashed password */
    private final String passwordHash;

    /**
     * Full constructor.
     * Used when loading a user account from database.
     *
     * @param id           user account identifier
     * @param login        user login
     * @param passwordHash hashed password
     */
    public UserAccount(int id, String login, String passwordHash) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    /**
     * Lightweight constructor.
     * Used when creating a new user account before persistence.
     *
     * @param login        user login
     * @param passwordHash hashed password
     */
    public UserAccount(String login, String passwordHash) {
        this(0, login, passwordHash);
    }

    /**
     * @return hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @return user account identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * String representation.
     *
     * @return string describing the user account
     */
    @Override
    public String toString() {
        return id + " | " + login;
    }
}

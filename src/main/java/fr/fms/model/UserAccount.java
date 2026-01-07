package fr.fms.model;

public class UserAccount {
    private final int id;
    private final String login;
    private final String passwordHash;

    public UserAccount(int id, String login, String passwordHash) {
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public UserAccount(String login, String passwordHash) {
        this(0, login, passwordHash);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return id + " | " + login;
    }

}

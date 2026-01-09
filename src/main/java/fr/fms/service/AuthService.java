package fr.fms.service;

import fr.fms.dao.UserAccountDao;
import fr.fms.exception.AuthenticationException;
import fr.fms.model.UserAccount;
import fr.fms.utils.PasswordHasher;

import static fr.fms.utils.Helpers.isNullOrEmpty;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Authentication service.
 *
 * Responsibilities:
 * - login: validate credentials & return the matching user
 * - register: create a new user account with a hashed password
 *
 * This service contains business rules, while the DAO only talks to the db.
 *
 */
public class AuthService {

    /** DAO used to read/write user accounts */
    private final UserAccountDao userDao;

    /**
     * Builds AuthService.
     *
     * @param userDao DAO used to access user accounts
     */
    public AuthService(UserAccountDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Authenticates a user & returns the corresponding account.
     *
     * Steps:
     * 1) validate inputs (no empty login/password)
     * 2) normalize login (trim + lowercase)
     * 3) hash password
     * 4) find user by login & compare hashes
     *
     * @param login    user login
     * @param password user password
     * @return authenticated UserAccount
     * @throws AuthenticationException if input is invalid or credentials do not
     *                                 match
     */
    public UserAccount login(String login, String password) {
        // Validation: no login, no party
        if (isNullOrEmpty(login) || isNullOrEmpty(password)) {
            throw new AuthenticationException("Identifiant/mot de passe obligatoires.");
        }

        // Normalize login to avoid "Bob" vs " bob " vs "BOB"
        String cleanLogin = login.trim().toLowerCase();

        // Hash input password
        String inputHash = PasswordHasher.sha256(password);

        return userDao.findByLogin(cleanLogin)
                .filter(u -> {
                    // Constant-time compare to reduce timing leaks
                    byte[] a = inputHash.getBytes(StandardCharsets.UTF_8);
                    byte[] b = u.getPasswordHash().getBytes(StandardCharsets.UTF_8);
                    return MessageDigest.isEqual(a, b);
                })
                .orElseThrow(() -> new AuthenticationException("Identifiant ou mot de passe invalide."));

    }

    /**
     * Registers a new user account.
     *
     * Steps:
     * 1) validate inputs
     * 2) normalize login
     * 3) check for duplicates
     * 4) hash password
     * 5) create user in db & return the created account
     *
     * @param login    desired login
     * @param password desired password
     * @return created UserAccount
     * @throws AuthenticationException if input is invalid, login already exists, or
     *                                 persistence fails
     */
    public UserAccount register(String login, String password) {
        // Validation: empty credentials are not a personality trait
        if (isNullOrEmpty(login) || isNullOrEmpty(password)) {
            throw new AuthenticationException("Identifiant/mot de passe obligatoires.");
        }

        String cleanLogin = login.trim().toLowerCase();

        // Prevent duplicate login
        if (userDao.findByLogin(cleanLogin).isPresent()) {
            throw new AuthenticationException("L'identifiant existe déjà.");
        }

        // Hash before storing
        String pwdHash = PasswordHasher.sha256(password);

        int id = userDao.create(new UserAccount(cleanLogin, pwdHash));
        if (id <= 0) {
            throw new AuthenticationException("Une erreur est survenue lors de la création de l'utilisateur.");
        }

        // Reload user from DB to return the official stored version
        return userDao.findById(id)
                .orElseThrow(() -> new AuthenticationException(
                        "Une erreur est survenue lors de la récupération de l'utilisateur."));
    }
}

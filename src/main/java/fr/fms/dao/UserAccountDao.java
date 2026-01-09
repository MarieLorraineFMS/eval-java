package fr.fms.dao;

import java.util.Optional;

import fr.fms.model.UserAccount;

/**
 * Data Access Object interface for UserAccount.
 *
 * Defines persistence operations related to authentication data:
 * - find user by id
 * - find user by login
 * - create a new user account
 *
 * Authentication logic itself is handled in AuthService,
 */
public interface UserAccountDao {

    /**
     * Finds a user account by its identifier.
     *
     * @param id user account identifier
     * @return Optional containing the UserAccount if found, otherwise
     *         Optional.empty()
     */
    Optional<UserAccount> findById(int id);

    /**
     * Finds a user account by login.
     *
     * Login is expected to be unique.
     *
     * @param login user login
     * @return Optional containing the UserAccount if found, otherwise
     *         Optional.empty()
     */
    Optional<UserAccount> findByLogin(String login);

    /**
     * Creates a new user account in db.
     *
     * @param user user account to persist
     * @return generated user identifier, or 0 if creation failed
     */
    int create(UserAccount user);

    // Authentication logic is handled in AuthService (as it should be)
}

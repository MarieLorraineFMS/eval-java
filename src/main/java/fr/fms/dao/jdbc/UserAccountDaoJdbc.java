package fr.fms.dao.jdbc;

import java.sql.Statement;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.UserAccountDao;
import fr.fms.exception.DaoException;
import fr.fms.model.UserAccount;

/**
 * JDBC implementation of {@link UserAccountDao}.
 *
 * Handles persistence of authentication-related data:
 * - user id
 * - login
 * - password hash
 *
 * No passwords in clear text. Ever.
 */
public class UserAccountDaoJdbc implements UserAccountDao {

    /**
     * Finds a user account by its identifier.
     *
     * @param id user account identifier
     * @return Optional containing the UserAccount if found, otherwise
     *         Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<UserAccount> findById(int id) {
        final String sql = "SELECT id, login, password_hash FROM user_account WHERE id = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find user by id=" + id, e);
        }
    }

    /**
     * Finds a user account by login.
     *
     * Login is trimmed before query to avoid silly mistakes
     *
     *
     * @param login user login
     * @return Optional containing the UserAccount if found, otherwise
     *         Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<UserAccount> findByLogin(String login) {
        final String sql = "SELECT id, login, password_hash FROM user_account WHERE login = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            // Never query with raw spaces
            ps.setString(1, login == null ? null : login.trim());

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapUser(rs));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find user by login=" + login, e);
        }
    }

    /**
     * Creates a new user account in db.
     *
     *
     * @param user user account to persist
     * @return generated user identifier, or 0 if creation failed
     * @throws DaoException if a db error occurs
     */
    @Override
    public int create(UserAccount user) {
        final String sql = "INSERT INTO user_account(login, password_hash) VALUES(?, ?)";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPasswordHash());

            int affected = ps.executeUpdate();
            if (affected == 0)
                return 0;

            try (var keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    return 0;
                return keys.getInt(1);
            }

        } catch (Exception e) {
            throw new DaoException("Failed to create user " + user.getLogin(), e);
        }
    }

    /**
     * Maps a ResultSet row to a UserAccount object.
     *
     * Centralizing mapping avoids duplication & keeps things tidy.
     *
     * @param rs SQL result set
     * @return mapped UserAccount object
     * @throws Exception if a SQL access error occurs
     */
    private UserAccount mapUser(java.sql.ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String login = rs.getString("login");
        String pwdHash = rs.getString("password_hash");
        return new UserAccount(id, login, pwdHash);
    }
}

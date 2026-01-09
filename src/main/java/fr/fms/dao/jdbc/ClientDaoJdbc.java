package fr.fms.dao.jdbc;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.fms.config.DbConfig;
import fr.fms.dao.ClientDao;
import fr.fms.exception.DaoException;
import fr.fms.model.Client;

/**
 * JDBC implementation of {@link ClientDao}.
 *
 * Handles all SQL operations related to Client persistence.
 * Translates db rows into Client objects.
 */
public class ClientDaoJdbc implements ClientDao {

    /**
     * Retrieves all clients from db.
     *
     * @return list of all clients ordered by id
     * @throws DaoException if a db error occurs
     */
    @Override
    public List<Client> findAll() {
        final String sql = "SELECT id, first_name, last_name, email, address, phone FROM client ORDER BY id";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql);
                var rs = ps.executeQuery()) {

            List<Client> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapClient(rs));
            }
            return list;

        } catch (Exception e) {
            throw new DaoException("Failed to find all clients", e);
        }
    }

    /**
     * Finds a client by its identifier.
     *
     * @param id client identifier
     * @return Optional containing the Client if found, otherwise Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<Client> findById(int id) {
        final String sql = "SELECT id, first_name, last_name, email, address, phone FROM client WHERE id = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapClient(rs));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find client by id=" + id, e);
        }
    }

    /**
     * Finds a client by email address.
     *
     * @param email client email address
     * @return Optional containing the Client if found, otherwise Optional.empty()
     * @throws DaoException if a db error occurs
     */
    @Override
    public Optional<Client> findByEmail(String email) {
        final String sql = "SELECT id, first_name, last_name, email, address, phone FROM client WHERE email = ?";
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql)) {

            ps.setString(1, email);

            try (var rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(mapClient(rs));
            }

        } catch (Exception e) {
            throw new DaoException("Failed to find client by email=" + email, e);
        }
    }

    /**
     * Creates a new client in db.
     *
     * @param client client to persist
     * @return generated client identifier, or 0 if creation failed
     * @throws DaoException if a db error occurs
     */
    @Override
    public int create(Client client) {
        final String sql = """
                INSERT INTO client(first_name, last_name, email, address, phone)
                VALUES(?, ?, ?, ?, ?)
                """;
        try (var cnx = DbConfig.getConnection();
                var ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getAddress());
            ps.setString(5, client.getPhone());

            int affected = ps.executeUpdate();
            if (affected == 0)
                return 0;

            try (var keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    return 0;
                return keys.getInt(1);
            }

        } catch (Exception e) {
            throw new DaoException("Failed to create client " + client.getEmail(), e);
        }
    }

    /**
     * Maps a ResultSet row to a Client object.
     *
     * Centralizes mapping logic to avoid duplication.
     *
     * @param rs SQL result set
     * @return mapped Client object
     * @throws Exception if a SQL access error occurs
     */
    private Client mapClient(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String fn = rs.getString("first_name");
        String ln = rs.getString("last_name");
        String email = rs.getString("email");
        String addr = rs.getString("address");
        String phone = rs.getString("phone");
        return new Client(id, fn, ln, email, addr, phone);
    }
}

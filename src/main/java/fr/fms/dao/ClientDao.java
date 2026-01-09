package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Client;

/**
 * Data Access Object interface for Client.
 *
 * Defines all operations related to clients:
 * - listing all clients
 * - finding a client by id or email
 * - creating a new client
 *
 * JDBC handle db details.
 */
public interface ClientDao {

    /**
     * Retrieves all clients.
     *
     * @return list of all clients
     */
    List<Client> findAll();

    /**
     * Finds a client by its identifier.
     *
     * @param id client identifier
     * @return Optional containing the Client if found, otherwise Optional.empty()
     */
    Optional<Client> findById(int id);

    /**
     * Finds a client by email address.
     *
     * Email is expected to be unique.
     *
     * @param email client email address
     * @return Optional containing the Client if found, otherwise Optional.empty()
     */
    Optional<Client> findByEmail(String email);

    /**
     * Creates a new client in db.
     *
     * @param client client to persist
     * @return generated client identifier, or 0 if creation failed
     */
    int create(Client client);
}

package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Client;

public interface ClientDao {
    List<Client> findAll();

    Optional<Client> findById(int id);

    Optional<Client> findByEmail(String email);

    int create(Client client);

}

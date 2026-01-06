// src/main/java/fr/fms/dao/ClientDao.java
package fr.fms.dao;

import fr.fms.model.Client;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> readByEmail(String email);

    int create(Client client);

    void update(Client client);
}

// src/main/java/fr/fms/dao/CartDao.java
package fr.fms.dao;

import fr.fms.model.Cart;

import java.util.Optional;

public interface CartDao {
    Optional<Cart> readByUser(int userId);

    int create(int userId);
}

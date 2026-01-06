// src/main/java/fr/fms/dao/CartItemDao.java
package fr.fms.dao;

import fr.fms.model.CartItem;

import java.util.List;

public interface CartItemDao {
    List<CartItem> readByCart(int cartId);

    void create(int cartId, int trainingId, int quantity);

    void delete(int cartId, int trainingId);

    void deleteAll(int cartId);
}

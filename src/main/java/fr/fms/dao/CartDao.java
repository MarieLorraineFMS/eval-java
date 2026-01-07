package fr.fms.dao;

import java.math.BigDecimal;
import java.util.Optional;

import fr.fms.model.Cart;

public interface CartDao {

    Optional<Cart> findByUserId(int userId);

    // Returns existing cart id for user or creates one & returns its id
    int getOrCreateCartId(int userId);

    // Add line or increment if exists
    void addOrIncrement(int cartId, int trainingId, int deltaQty, BigDecimal unitPrice);

    // Decrement or remove if <=0
    boolean decrementOrRemove(int cartId, int trainingId, int deltaQty);

    // Remove line
    boolean removeLine(int cartId, int trainingId);

    // Clear cart lines
    void clear(int cartId);
}

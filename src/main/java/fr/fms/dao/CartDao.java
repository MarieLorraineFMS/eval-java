package fr.fms.dao;

import java.math.BigDecimal;
import java.util.Optional;

import fr.fms.model.Cart;

public interface CartDao {

    // One cart per user (UNIQUE user_id)
    Optional<Cart> findByUserId(int userId);

    // Returns existing cart id for user, or creates one and returns its id
    int getOrCreateCartId(int userId);

    // Add line or increment if exists (uq_cart_training)
    void addOrIncrement(int cartId, int trainingId, int deltaQty, BigDecimal unitPrice);

    // Decrement (and remove if <=0)
    boolean decrementOrRemove(int cartId, int trainingId, int deltaQty);

    // Remove line
    boolean removeLine(int cartId, int trainingId);

    // Clear cart lines
    void clear(int cartId);
}

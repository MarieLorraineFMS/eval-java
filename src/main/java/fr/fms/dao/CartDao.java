package fr.fms.dao;

import java.math.BigDecimal;
import java.util.Optional;

import fr.fms.model.Cart;

/**
 * Data Access Object interface for Cart.
 *
 * Defines all operations related to carts:
 * - loading a cart for a user
 * - creating or retrieving a cart
 * - managing cart items (add, increment, decrement, remove, clear)
 *
 * JDBC handle db details.
 * Rest of app only talks to this
 */
public interface CartDao {

    /**
     * Finds a cart for a given user.
     *
     * @param userId identifier of the user
     * @return Optional containing the Cart if found, otherwise Optional.empty()
     */
    Optional<Cart> findByUserId(int userId);

    /**
     * Returns the existing cart id for a user,
     * or creates a new cart if none exists.
     *
     * This method guarantees to always gets a cart id, unless something really bad
     * happens
     *
     * @param userId identifier of the user
     * @return cart identifier
     */
    int getOrCreateCartId(int userId);

    /**
     * Adds a cart line or increments quantity if the line already exists.
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @param deltaQty   quantity to add
     * @param unitPrice  unit price
     */
    void addOrIncrement(int cartId, int trainingId, int deltaQty, BigDecimal unitPrice);

    /**
     * Decrements quantity of a cart line.
     *
     * If quantity becomes zero or less, line is removed.
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @param deltaQty   quantity to remove
     * @return true if the line existed & was updated, false otherwise
     */
    boolean decrementOrRemove(int cartId, int trainingId, int deltaQty);

    /**
     * Removes an entire cart line, regardless of quantity.
     *
     * @param cartId     identifier of the cart
     * @param trainingId identifier of the training
     * @return true if a line was removed, false if it did not exist
     */
    boolean removeLine(int cartId, int trainingId);

    /**
     * Clears all lines from a cart.
     *
     * @param cartId identifier of the cart
     */
    void clear(int cartId);
}

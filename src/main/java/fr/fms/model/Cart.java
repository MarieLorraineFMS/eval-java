package fr.fms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents a shopping cart.
 *
 * A Cart belongs to a user & contains a list of CartItem.
 * It knows:
 * - who owns it (userId)
 * - when it was created
 * - what is inside
 * - how much it costs
 */
public class Cart {

    /** DB identifier (0 means "not persisted yet") */
    private int id;

    /** Owner of the cart */
    private final int userId;

    /** Creation date of the cart */
    private final LocalDateTime createdAt;

    /** List of items inside the cart */
    private final List<CartItem> items;

    /**
     * Full constructor.
     * Used when loading a cart from db.
     *
     * @param id        cart identifier
     * @param userId    identifier of cart owner
     * @param createdAt creation date of cart
     * @param items     list of items in the cart
     */
    public Cart(int id, int userId, LocalDateTime createdAt, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        // Defensive copy : we don't trust external lists (never trust anyone)
        this.items = new ArrayList<>(items);
    }

    /**
     * Lightweight constructor.
     * Used when creating a brand new cart (empty and innocent).
     *
     * @param userId    identifier of the cart owner
     * @param createdAt creation date of the cart
     */
    public Cart(int userId, LocalDateTime createdAt) {
        this(0, userId, createdAt, new ArrayList<>());
    }

    /**
     * @return cart identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return identifier of the user owning this cart
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return creation date of the cart
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns an unmodifiable view of cart items.
     * No one is allowed to mess with the cart behind our back
     *
     * @return read-only list of cart items
     */
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    /**
     * Computes the total amount of the cart.
     *
     * Uses BigDecimal because money & double = sadness.
     *
     * @return total price of all cart items
     */
    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //////////// HELPERS ///////////////////

    /**
     * Adds a training to the cart.
     *
     * If the training already exists:
     * - quantity is incremented
     * Otherwise:
     * - a new CartItem is created
     *
     * @param training  training to add
     * @param unitPrice price of one unit
     * @param quantity  quantity to add
     */
    public void addTraining(Training training, BigDecimal unitPrice, int quantity) {
        items.stream()
                .filter(i -> i.getTraining().getId() == training.getId())
                .findFirst()
                .ifPresentOrElse(
                        i -> i.incrementQuantity(quantity), // same item, more quantity
                        () -> items.add(new CartItem(training, quantity, unitPrice)) // new line
                );
    }

    /**
     * Finds a cart item by training id.
     *
     * Optional is used to avoid null hell
     *
     * @param trainingId identifier of training
     * @return Optional containing CartItem if found, empty otherwise
     */
    private Optional<CartItem> findItemByTrainingId(int trainingId) {
        return items.stream()
                .filter(i -> i.getTraining().getId() == trainingId)
                .findFirst();
    }

    /**
     * Decrements quantity of a training.
     *
     * If quantity becomes zero or less:
     * - line is removed
     *
     * @param trainingId identifier of training
     * @param delta      quantity to remove
     * @return true if the item was found & updated, false otherwise
     */
    public boolean decrementTraining(int trainingId, int delta) {
        return findItemByTrainingId(trainingId)
                .map(i -> {
                    i.incrementQuantity(-delta); // negative delta = decrement
                    if (i.getQuantity() <= 0) {
                        items.remove(i); // clean cart
                    }
                    return true;
                })
                .orElse(false);
    }

    /**
     * Removes a training from the cart.
     *
     * @param trainingId identifier of training
     * @return true if item was removed, false if it was not found
     */
    public boolean removeTraining(int trainingId) {
        return findItemByTrainingId(trainingId)
                .map(i -> items.remove(i))
                .orElse(false);
    }

    /**
     * Empties the cart.
     */
    public void clear() {
        items.clear();
    }

    /**
     * String representation of cart.
     *
     * @return string describing cart
     */
    @Override
    public String toString() {
        int nbItems = items == null ? 0 : items.size();
        return "Cart{id=" + id
                + ", userId=" + userId
                + ", createdAt=" + createdAt
                + ", itemsCount=" + nbItems
                + ", total=" + getTotal()
                + "}";
    }
}

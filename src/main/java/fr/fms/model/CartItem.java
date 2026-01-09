package fr.fms.model;

import java.math.BigDecimal;

/**
 * Represents one line in a cart.
 *
 * A CartItem links:
 * - a training (what you buy)
 * - a quantity (how many)
 * - a unit price (how expensive the fun is)
 * - optionally an id/cartId when persisted in db
 */
public class CartItem {

    /** Database identifier (0 means "not persisted yet") */
    private int id;

    /** Db cart identifier (0 means "not attached/persisted yet") */
    private final int cartId;

    /** Training being purchased */
    private final Training training;

    /** Quantity of purchased training */
    private int quantity;

    /** Price for one unit (BigDecimal because money is serious) */
    private final BigDecimal unitPrice;

    /**
     * Full constructor.
     * Used when reading from the db.
     *
     * @param id        cart item identifier
     * @param cartId    identifier of cart containing this item
     * @param training  training being purchased
     * @param quantity  quantity of purchased training
     * @param unitPrice price of one unit
     */
    public CartItem(int id, int cartId, Training training, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.cartId = cartId;
        this.training = training;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Lightweight constructor.
     * Useful before cart persistence.
     *
     * Note: cartId is set to 0 here, meaning "not persisted/attached yet".
     *
     * @param training  training being purchased
     * @param quantity  quantity of purchased training
     * @param unitPrice price of one unit
     */
    public CartItem(Training training, int quantity, BigDecimal unitPrice) {
        this(0, 0, training, quantity, unitPrice);
    }

    /**
     * @return cart item identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return cart identifier (0 if not attached/persisted yet)
     */
    public int getCartId() {
        return cartId;
    }

    /**
     * @return training being purchased
     */
    public Training getTraining() {
        return training;
    }

    /**
     * @return quantity of purchased training
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return unit price of purchased training
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Adds or decrease quantity.
     *
     * delta can be negative to decrement.
     * (Yes, this method is simple, but it's useful to have it.)
     *
     * @param delta quantity to add
     */
    public void incrementQuantity(int delta) {
        this.quantity += delta;
    }

    /**
     * Computes total amount for this cart line.
     *
     * @return unitPrice * quantity
     */
    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * String representation.
     *
     * @return string describing cart item
     */
    @Override
    public String toString() {
        return "CartItem : id=" + id
                + ", cartId=" + cartId
                + ", training=" + training.getName()
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice;
    }
}

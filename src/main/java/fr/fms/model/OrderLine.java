package fr.fms.model;

import java.math.BigDecimal;

/**
 * Represents one line of an order.
 *
 * An OrderLine is immutable:
 * - once an order is created, quantities & prices should not change
 * - this is a historical record, not a shopping cart
 *
 * Like a receipt line
 */
public class OrderLine {

    /** Db identifier (0 means "not persisted yet") */
    private int id;

    /** Identifier of the order this line belongs to */
    private final int orderId;

    /** Training being purchased */
    private final Training training;

    /** Quantity purchased */
    private final int quantity;

    /** Unit price at the time of purchase */
    private final BigDecimal unitPrice;

    /**
     * Full constructor.
     * Used when loading order lines from db.
     *
     * @param id        order line identifier
     * @param orderId   identifier of the related order
     * @param training  training being purchased
     * @param quantity  quantity purchased
     * @param unitPrice unit price
     */
    public OrderLine(int id, int orderId, Training training, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.training = training;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Lightweight constructor.
     * Used when creating order lines before persistence.
     *
     * @param orderId   identifier of the related order
     * @param training  training being purchased
     * @param quantity  quantity purchased
     * @param unitPrice unit price
     */
    public OrderLine(int orderId, Training training, int quantity, BigDecimal unitPrice) {
        this(0, orderId, training, quantity, unitPrice);
    }

    /**
     * @return order line identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return identifier of the related order
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @return training being purchased
     */
    public Training getTraining() {
        return training;
    }

    /**
     * @return quantity purchased
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return unit price
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Computes total amount for this order line.
     *
     * @return unitPrice * quantity
     */
    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * String representation.
     *
     * @return string describing the order line
     */
    @Override
    public String toString() {
        return id
                + " | order=" + orderId
                + " | training=" + training.getId()
                + " | qty=" + quantity
                + " | price=" + unitPrice;
    }
}

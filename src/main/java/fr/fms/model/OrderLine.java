package fr.fms.model;

import java.math.BigDecimal;

public class OrderLine {
    private int id;
    private final int orderId;
    private final Training training;
    private final int quantity;
    private final BigDecimal unitPrice;

    public OrderLine(int id, int orderId, Training training, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.training = training;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public OrderLine(int orderId, Training training, int quantity, BigDecimal unitPrice) {
        this(0, orderId, training, quantity, unitPrice);
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public Training getTraining() {
        return training;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return id + " | order=" + orderId + " | training=" + training.getId() + " | qty=" + quantity + " | price="
                + unitPrice;
    }

}

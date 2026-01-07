package fr.fms.model;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private final int cartId;
    private final Training training;
    private int quantity;
    private final BigDecimal unitPrice;

    public CartItem(int id, int cartId, Training training, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.cartId = cartId;
        this.training = training;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public CartItem(Training training, int quantity, BigDecimal unitPrice) {
        this(0, 0, training, quantity, unitPrice);
    }

    public int getId() {
        return id;
    }

    public int getCartId() {
        return cartId;
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

    public void incrementQuantity(int delta) {
        this.quantity += delta;
    }

    public BigDecimal getLineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "CartItem : id=" + id + ", cartId=" + cartId + ", training=" + training.getName() + ", quantity="
                + quantity
                + ", unitPrice=" + unitPrice;
    }

}
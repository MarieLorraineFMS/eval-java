package fr.fms.model;

import java.math.BigDecimal;

public class CartItem {
    private int id;
    private int cartId;
    private int trainingId;
    private int quantity;
    private BigDecimal unitPrice;

    public CartItem(int id, int cartId, int trainingId, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.cartId = cartId;
        this.trainingId = trainingId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public int getCartId() {
        return cartId;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return id + " | " + cartId + " | " + trainingId + " | " + quantity + " | " + unitPrice;
    }

}
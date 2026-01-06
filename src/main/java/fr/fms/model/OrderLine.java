package fr.fms.model;

import java.math.BigDecimal;

public class OrderLine {
    private int id;
    private int orderId;
    private int trainingId;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderLine(int id, int orderId, int trainingId, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.trainingId = trainingId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
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
        return id + " | " + orderId + " | " + trainingId + " | " + quantity + " | " + unitPrice;
    }

}

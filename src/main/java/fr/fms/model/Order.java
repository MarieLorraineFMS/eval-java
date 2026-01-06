package fr.fms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private int clientId;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private BigDecimal total;
    private List<OrderLine> lines = new ArrayList<>();

    public Order(int id, int userId, int clientId, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getClientId() {
        return clientId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return id + " | " + userId + " | " + clientId + " | " + createdAt + " | " + status + " | " + total + " | "
                + lines;
    }
}
package fr.fms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private final int userId;
    private final Client client;
    private final LocalDateTime createdAt;
    private final OrderStatus status;
    private final BigDecimal total;
    private final List<OrderLine> lines = new ArrayList<>();

    public Order(int id, int userId, Client client, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.client = client;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    public Order(int userId, Client client, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this(0, userId, client, createdAt, status, total);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderLine> getLines() {
        return new ArrayList<>(lines);
    }

    // Total from DB
    public BigDecimal getTotal() {
        return total;
    }

    // Recompute total from lines
    public BigDecimal computeTotalFromLines() {
        return lines.stream()
                .map(OrderLine::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Building order lines for DAO/service
    public void addLine(OrderLine line) {
        // Basic guard to avoid null insert
        if (line == null)
            return; // or throw IllegalArgumentException
        lines.add(line);
    }

    @Override
    public String toString() {
        return id + " | user=" + userId
                + " | client=" + client.getId()
                + " | " + status
                // DB total
                + " | total=" + total;
    }

}
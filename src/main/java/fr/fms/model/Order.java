package fr.fms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an order.
 *
 * An Order is a snapshot of a validated cart:
 * - linked to a user & a client
 * - has a status (DRAFT, CONFIRMED, etc.)
 * - contains order lines
 * - stores a total coming from db
 *
 * In short: this is the "no turning back" version of the cart
 */
public class Order {

    /** Db identifier (0 means "not persisted yet") */
    private final int id;

    /** User who created the order */
    private final int userId;

    /** Client linked to this order */
    private final Client client;

    /** Order creation date */
    private final LocalDateTime createdAt;

    /** Current order status */
    private final OrderStatus status;

    /** Total amount stored in db */
    private final BigDecimal total;

    /** Order lines (one per purchased training) */
    private final List<OrderLine> lines = new ArrayList<>();

    /**
     * Full constructor.
     * Used when loading an order from db.
     *
     * @param id        order identifier
     * @param userId    identifier of the user who created the order
     * @param client    client linked to the order
     * @param createdAt order creation date
     * @param status    current order status
     * @param total     total amount stored in database
     */
    public Order(int id, int userId, Client client, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.client = client;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    /**
     * Lightweight constructor.
     * Used when creating a new order before persistence.
     *
     * @param userId    identifier of the user who created the order
     * @param client    client linked to the order
     * @param createdAt order creation date
     * @param status    initial order status
     * @param total     total amount (computed from cart)
     */
    public Order(int userId, Client client, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this(0, userId, client, createdAt, status, total);
    }

    /**
     * @return order identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return identifier of the user who created the order
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return client linked to this order
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return order creation date
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return current order status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns a copy of order lines.
     * To avoid messing with internal state.
     *
     * @return list of order lines
     */
    public List<OrderLine> getLines() {
        return new ArrayList<>(lines);
    }

    /**
     * Returns total amount stored in db.
     *
     * Trusted as official total.
     *
     * @return total amount from db
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Recomputes total amount from order lines.
     *
     * Useful for:
     * - consistency checks
     * - debugging
     * - "wait… why is this total weird?" moments
     *
     * @return computed total based on order lines
     */
    public BigDecimal computeTotalFromLines() {
        return lines.stream()
                .map(OrderLine::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds an order line to the order.
     *
     * Used by DAO or service layer
     * when building the order from db data.
     *
     * @param line order line to add
     */
    public void addLine(OrderLine line) {
        // Basic guard to avoid null insert
        if (line == null)
            return;
        lines.add(line);
    }

    /**
     * String representation.
     *
     * @return string describing order
     */
    @Override
    public String toString() {
        return id + " | user=" + userId
                + " | client=" + client.getId()
                + " | " + status
                + " | total=" + total;
    }
}

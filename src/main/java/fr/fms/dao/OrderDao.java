package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.model.OrderStatus;

/**
 * Data Access Object interface for Order.
 *
 * Defines persistence operations for:
 * - creating an order (header)
 * - creating an order with its lines (transaction)
 * - reading orders (by id / by user)
 * - updating order status
 *
 * JDBC handle db details.
 */
public interface OrderDao {

    /**
     * Creates an order header in database (no lines).
     *
     * @param order order to persist
     * @return order id, or 0 if creation failed
     */
    int create(Order order);

    /**
     * Adds one order line to an existing order.
     *
     * @param orderId    identifier of the order
     * @param trainingId identifier of the training
     * @param quantity   quantity purchased
     * @param unitPrice  unit price
     */
    void addLine(int orderId, int trainingId, int quantity, java.math.BigDecimal unitPrice);

    /**
     * Finds an order by its identifier.
     *
     * @param orderId identifier of the order
     * @return Optional containing the Order if found, otherwise Optional.empty()
     */
    Optional<Order> findById(int orderId);

    /**
     * Finds all orders created by a given user.
     *
     * @param userId identifier of the user
     * @return list of orders for the user
     */
    List<Order> findByUserId(int userId);

    /**
     * Creates an order header & its lines in a single transaction.
     * If anything fails, nothing is persisted (all or nothing).
     *
     * @param order order header to persist
     * @param lines order lines to persist
     * @return generated order id, or 0 if creation failed
     */
    int createOrderWithLines(Order order, List<OrderLine> lines);

    /**
     * Updates the status of an order.
     *
     * @param orderId identifier of the order
     * @param status  new status to set
     * @return true if an order was updated, false otherwise
     */
    boolean updateStatus(int orderId, OrderStatus status);
}

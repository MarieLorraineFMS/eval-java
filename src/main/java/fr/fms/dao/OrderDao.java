package fr.fms.dao;

import java.util.List;
import java.util.Optional;

import fr.fms.model.Order;
import fr.fms.model.OrderLine;
import fr.fms.model.OrderStatus;

public interface OrderDao {

    int create(Order order);

    // Add one order line
    void addLine(int orderId, int trainingId, int quantity, java.math.BigDecimal unitPrice);

    Optional<Order> findById(int orderId);

    List<Order> findByUserId(int userId);

    // Add order & lines in one transaction
    int createOrderWithLines(Order order, List<OrderLine> lines);

    boolean updateStatus(int orderId, OrderStatus status);
}

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

    // getters/setters/constructors
}
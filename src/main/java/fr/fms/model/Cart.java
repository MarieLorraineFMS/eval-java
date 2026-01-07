package fr.fms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Cart {
    private int id;
    private final int userId;
    private final LocalDateTime createdAt;
    private final List<CartItem> items;

    public Cart(int id, int userId, LocalDateTime createdAt, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.items = new ArrayList<>(items);
    }

    public Cart(int userId, LocalDateTime createdAt) {
        this(0, userId, createdAt, new ArrayList<>());

    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //////////// HELPERS ///////////////////

    public void addTraining(Training training, BigDecimal unitPrice, int quantity) {
        items.stream()
                .filter(i -> i.getTraining().getId() == training.getId())
                .findFirst()
                .ifPresentOrElse(
                        i -> i.incrementQuantity(quantity),
                        () -> items.add(new CartItem(training, quantity, unitPrice)));
    }

    private Optional<CartItem> findItemByTrainingId(int trainingId) {
        return items.stream()
                .filter(i -> i.getTraining().getId() == trainingId)
                .findFirst();
    }

    // Decrement or remove line if quantity == 0
    public boolean decrementTraining(int trainingId, int delta) {
        return findItemByTrainingId(trainingId)
                .map(i -> {
                    i.incrementQuantity(-delta); // negative delta to decrement
                    if (i.getQuantity() <= 0) {
                        items.remove(i);
                    }
                    return true;
                })
                .orElse(false);
    }

    // Remove entire line
    public boolean removeTraining(int trainingId) {
        return findItemByTrainingId(trainingId)
                .map(i -> items.remove(i))
                .orElse(false);
    }

    public void clear() {
        // Empty cart
        items.clear();
    }

    @Override
    public String toString() {
        return id + " | " + userId + " | " + createdAt + " | " + items;
    }
}

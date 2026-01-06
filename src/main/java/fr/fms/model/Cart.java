package fr.fms.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private LocalDateTime createdAt;
    private List<CartItem> items = new ArrayList<>();

    public Cart(int id, int userId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
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
        return items;
    }

    @Override
    public String toString() {
        return id + " | " + userId + " | " + createdAt + " | " + items;
    }
}

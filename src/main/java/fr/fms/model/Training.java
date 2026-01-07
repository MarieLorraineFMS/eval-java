package fr.fms.model;

import java.math.BigDecimal;

public class Training {
    private final int id;
    private final String name;
    private final String description;
    private final int durationDays;
    private final BigDecimal price;
    private final boolean onsite;

    public Training(int id, String name, String description, int durationDays, BigDecimal price, boolean onsite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationDays = durationDays;
        this.price = price;
        this.onsite = onsite;
    }

    public Training(String name, String description, int durationDays, BigDecimal price, boolean onsite) {
        this(0, name, description, durationDays, price, onsite);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isOnsite() {
        return onsite;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + description + " | " + durationDays + " | " + price + " | " + onsite;
    }
}

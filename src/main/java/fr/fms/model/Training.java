package fr.fms.model;

import java.math.BigDecimal;

/**
 * Represents a training.
 *
 * A Training is a read-only business object:
 * - defined in a catalog
 * - referenced by carts and orders
 * - never modified once created
 *
 * Stable data, no surprises
 */
public class Training {

    /** Db identifier (0 means "not persisted yet") */
    private final int id;

    /** Training name */
    private final String name;

    /** Training description */
    private final String description;

    /** Duration of the training in days */
    private final int durationDays;

    /** Price of the training */
    private final BigDecimal price;

    /** Indicates if the training is onsite or remote */
    private final boolean onsite;

    /**
     * Full constructor.
     * Used when loading a training from db.
     *
     * @param id           training identifier
     * @param name         training name
     * @param description  training description
     * @param durationDays duration of the training in days
     * @param price        price of training
     * @param onsite       true if onsite, false if remote
     */
    public Training(int id, String name, String description, int durationDays, BigDecimal price, boolean onsite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationDays = durationDays;
        this.price = price;
        this.onsite = onsite;
    }

    /**
     * Lightweight constructor.
     * Used when creating a new training before persistence.
     *
     * @param name         training name
     * @param description  training description
     * @param durationDays duration of the training in days
     * @param price        price of the training
     * @param onsite       true if onsite, false if remote
     */
    public Training(String name, String description, int durationDays, BigDecimal price, boolean onsite) {
        this(0, name, description, durationDays, price, onsite);
    }

    /**
     * @return training identifier
     */
    public int getId() {
        return id;
    }

    /**
     * @return training name
     */
    public String getName() {
        return name;
    }

    /**
     * @return training description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return duration of the training in days
     */
    public int getDurationDays() {
        return durationDays;
    }

    /**
     * @return price of the training
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @return true if onsite, false if remote
     */
    public boolean isOnsite() {
        return onsite;
    }

    /**
     * Simple string representation.
     *
     * @return string describing the training
     */
    @Override
    public String toString() {
        return id + " | "
                + name + " | "
                + description + " | "
                + durationDays + " | "
                + price + " | "
                + onsite;
    }
}

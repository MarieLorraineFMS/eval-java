package fr.fms.model;

import static fr.fms.utils.Helpers.isNullOrEmpty;

/**
 * Represents the status of an order.
 *
 * Used to:
 * - keep order states explicit & type-safe
 * - convert values to/from db representation
 *
 */
public enum OrderStatus {

    /** Order is created but not yet validated */
    DRAFT,

    /** Order has been confirmed */
    CONFIRMED,

    /** Order has been cancelled */
    CANCELLED;

    /**
     * Converts a db value to an OrderStatus.
     *
     * Db values are expected to be strings like:
     * "draft", "confirmed", "cancelled".
     *
     * @param value status value coming from db
     * @return corresponding OrderStatus, or null if value is null/empty
     */
    public static OrderStatus fromDb(String value) {
        return isNullOrEmpty(value)
                ? null
                : OrderStatus.valueOf(value.toUpperCase());
    }

    /**
     * Converts this OrderStatus to a db-friendly value.
     *
     * @return string value to store
     */
    public String toDb() {
        return this.name();
    }
}

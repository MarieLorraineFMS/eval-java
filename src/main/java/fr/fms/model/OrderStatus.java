package fr.fms.model;

import static fr.fms.utils.Helpers.isNullOrEmpty;

public enum OrderStatus {
    DRAFT,
    CONFIRMED,
    CANCELLED;

    public static OrderStatus fromDb(String value) {
        return isNullOrEmpty(value) ? null : OrderStatus.valueOf(value.toUpperCase());
    }

    public String toDb() {
        return this.name();
    }
}

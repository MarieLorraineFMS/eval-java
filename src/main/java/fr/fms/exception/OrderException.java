package fr.fms.exception;

/**
 * Exception thrown when an order operation fails.
 *
 * Ex:
 * - checkout cannot create the order
 * - client data is invalid
 * - persistence fails during checkout
 */
public class OrderException extends RuntimeException {

    /**
     * @param message error message
     */
    public OrderException(String message) {
        super(message);
    }

    /**
     * @param message error message
     * @param cause   original exception
     */
    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

package fr.fms.exception;

/**
 * Exception thrown when an operation requires a non-empty cart
 * but the cart is empty.
 */
public class CartEmptyException extends RuntimeException {

    /**
     * @param message error message
     */
    public CartEmptyException(String message) {
        super(message);
    }
}

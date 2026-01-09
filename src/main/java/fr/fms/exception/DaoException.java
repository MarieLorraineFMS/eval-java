package fr.fms.exception;

/**
 * Exception thrown when a DAO operation fails.
 *
 * Wraps low-level SQL/connection errors into an exception,
 * so upper layers can handle it.
 */
public class DaoException extends RuntimeException {

    /**
     * @param message error message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * @param message error message
     * @param cause   original exception
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

package fr.fms.exception;

/**
 * Exception thrown when authentication or registration fails.
 *
 * Used for:
 * - missing credentials
 * - invalid login/password
 * - duplicate login during registration
 */
public class AuthenticationException extends RuntimeException {

    /**
     * @param message error message
     */
    public AuthenticationException(String message) {
        super(message);
    }
}

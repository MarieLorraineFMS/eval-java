package fr.fms.exception;

/**
 * Exception thrown when a training is not found.
 *
 * Raised when:
 * - an id does not exist in the db
 * - the UI references a non-existing training
 */
public class TrainingNotFoundException extends RuntimeException {

    /**
     * @param message error message
     */
    public TrainingNotFoundException(String message) {
        super(message);
    }
}

package org.thewhitemage13.exception;

/**
 * Represents an exception that is thrown when a notification is not found.
 * <p>
 * This exception is used to signal that a requested notification could not be
 * located in the system, typically when querying the database or external services.
 * </p>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is useful when attempting to retrieve a notification by its ID
 * or other unique identifier, and the notification does not exist in the system.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NotificationNotFoundException extends Exception {


    /**
     * Constructs a new {@code NotificationNotFoundException} with {@code null}
     * as its detail message.
     */
    public NotificationNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code NotificationNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NotificationNotFoundException} with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the underlying cause of the exception
     */
    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code NotificationNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public NotificationNotFoundException(Throwable cause) {
        super(cause);
    }
}

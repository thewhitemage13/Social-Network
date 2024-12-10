package org.thewhitemage13.exception;

/**
 * Exception thrown when a requested "like" action or entity is not found in the system.
 * <p>
 * This custom runtime exception is used to indicate that an operation involving a "like"
 * has failed because the corresponding entity or action does not exist.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides constructors to specify error messages and causes.</li>
 *     <li>Facilitates exception chaining by accepting a {@code Throwable} cause.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class LikeNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code LikeNotFoundException} with no detail message or cause.
     */
    public LikeNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public LikeNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the underlying cause of the exception
     */
    public LikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public LikeNotFoundException(Throwable cause) {
        super(cause);
    }
}

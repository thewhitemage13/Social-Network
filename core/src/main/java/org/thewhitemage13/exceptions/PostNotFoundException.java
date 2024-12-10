package org.thewhitemage13.exceptions;

/**
 * Exception thrown when a requested post is not found in the system.
 * <p>
 * This custom runtime exception is used to indicate that an operation involving a post
 * has failed because the specified post does not exist or is unavailable.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports detailed error messages for debugging purposes.</li>
 *     <li>Allows chaining of exceptions by accepting a {@code Throwable} cause.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class PostNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code PostNotFoundException} with no detail message or cause.
     */
    public PostNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public PostNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the underlying cause of the exception
     */
    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public PostNotFoundException(Throwable cause) {
        super(cause);
    }
}

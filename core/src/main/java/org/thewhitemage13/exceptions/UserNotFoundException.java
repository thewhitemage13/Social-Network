package org.thewhitemage13.exceptions;

/**
 * Exception thrown when a requested user is not found in the system.
 * <p>
 * This custom runtime exception is used to indicate that an operation involving a user
 * has failed because the specified user does not exist or is unavailable.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports detailed error messages for better debugging.</li>
 *     <li>Allows exception chaining by accepting a {@code Throwable} cause.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UserNotFoundException} with no detail message or cause.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UserNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the underlying cause of the exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code UserNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}

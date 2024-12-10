package org.thewhitemage13.exception;

/**
 * Exception thrown when a requested media resource is not found in the system.
 * <p>
 * This custom runtime exception is used to signal that an operation involving media
 * has failed because the specified media resource does not exist or is unavailable.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Allows specification of detailed error messages for better debugging.</li>
 *     <li>Supports exception chaining by accepting a {@code Throwable} cause.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class MediaNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code MediaNotFoundException} with no detail message or cause.
     */
    public MediaNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public MediaNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the underlying cause of the exception
     */
    public MediaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public MediaNotFoundException(Throwable cause) {
        super(cause);
    }
}

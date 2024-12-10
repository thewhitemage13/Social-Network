package org.thewhitemage13.exception;

/**
 * Exception thrown when a media file cannot be found in the system.
 * <p>
 * This exception is typically thrown when an operation attempts to access a media
 * that does not exist or cannot be located based on the provided identifier.
 * It extends the {@link Exception} class, allowing for custom error messages and
 * the chaining of underlying causes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Custom exception for handling media not found scenarios.</li>
 *     <li>Supports multiple constructors for flexible exception handling.</li>
 *     <li>Allows for message and cause chaining to provide detailed error information.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class MediaNotFoundException extends Exception {

    /**
     * Default constructor for {@code MediaNotFoundException}.
     * <p>
     * This constructor creates a new exception instance without any specific message or cause.
     * </p>
     */
    public MediaNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified detail message.
     * <p>
     * This constructor allows for a custom error message to be passed that can explain
     * the reason for the exception.
     * </p>
     *
     * @param message the detail message to be used for this exception
     */
    public MediaNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified detail message
     * and cause.
     * <p>
     * This constructor allows both a custom message and the underlying cause of the exception
     * to be captured, making it easier to debug and trace the root issue.
     * </p>
     *
     * @param message the detail message to be used for this exception
     * @param cause the cause of the exception (may be {@code null})
     */
    public MediaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code MediaNotFoundException} with the specified cause.
     * <p>
     * This constructor allows for an exception to be thrown with only a cause and no message.
     * </p>
     *
     * @param cause the cause of the exception (may be {@code null})
     */
    public MediaNotFoundException(Throwable cause) {
        super(cause);
    }

}

package org.thewhitemage13.exception;

/**
 * Custom exception to indicate that a "like" entity was not found in the system.
 * <p>
 * This exception is used to handle scenarios where an operation fails because the specified
 * "like" could not be located in the database or another data source.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link Exception} to provide checked exception behavior.</li>
 *     <li>Includes multiple constructors to support different exception handling scenarios.</li>
 *     <li>Facilitates meaningful error messages and chaining of exceptions for debugging purposes.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * throw new LikeNotFoundException("Like with ID 123 not found.");
 * }</pre>
 *
 * @see Exception
 * @see RuntimeException
 * @see Throwable
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class LikeNotFoundException extends Exception{

    /**
     * Constructs a new {@code LikeNotFoundException} with {@code null} as its detail message.
     * <p>
     * This constructor is useful when no specific message is required for the exception.
     * </p>
     */
    public LikeNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     */
    public LikeNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified detail message
     * and cause.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     * @param cause the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *              (A {@code null} value is permitted and indicates that the cause is nonexistent or unknown.)
     */
    public LikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code LikeNotFoundException} with the specified cause.
     *
     * @param cause the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *              (A {@code null} value is permitted and indicates that the cause is nonexistent or unknown.)
     */
    public LikeNotFoundException(Throwable cause) {
        super(cause);
    }
}

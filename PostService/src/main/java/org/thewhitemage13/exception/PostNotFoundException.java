package org.thewhitemage13.exception;

/**
 * Exception thrown when a requested post is not found.
 * <p>
 * This exception is typically used in scenarios where an operation, such as retrieving or updating a post,
 * fails due to the absence of the post in the system.
 * </p>
 * <p>
 * It extends from {@link Exception} and provides constructors to initialize the exception with a message,
 * a cause, or both.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class PostNotFoundException extends Exception {

    /**
     * Constructs a new {@code PostNotFoundException} with no detail message.
     * <p>
     * The cause is not initialized, and the detail message is set to {@code null}.
     * </p>
     */
    public PostNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified detail message.
     * <p>
     * The cause is not initialized, and the detail message is set to the provided string.
     * </p>
     *
     * @param message the detail message that explains the reason for the exception
     */
    public PostNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified detail message and cause.
     * <p>
     * This constructor allows chaining of exceptions, providing both the reason for the exception
     * (message) and the original exception (cause).
     * </p>
     *
     * @param message the detail message that explains the reason for the exception
     * @param cause the cause of the exception (a {@code null} value is allowed)
     */
    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code PostNotFoundException} with the specified cause.
     * <p>
     * The detail message is initialized to {@code null}, and the cause is set to the provided value.
     * </p>
     *
     * @param cause the cause of the exception (a {@code null} value is allowed)
     */
    public PostNotFoundException(Throwable cause) {
        super(cause);
    }
}

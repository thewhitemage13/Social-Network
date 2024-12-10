package org.thewhitemage13.exception;

/**
 * Exception thrown when a comment is not found in the system.
 * <p>
 * This custom exception is used to indicate that a requested comment could not be located,
 * either because it does not exist or has been deleted. It provides constructors for
 * various scenarios, including detailed error messages and root causes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link Exception}, allowing for checked exception handling.</li>
 *     <li>Supports multiple constructors for flexibility in error reporting.</li>
 *     <li>Used to enhance the clarity and specificity of error handling in the application.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class CommentNotFoundException extends Exception{

    /**
     * Constructs a new {@code CommentNotFoundException} with no detail message or cause.
     */
    public CommentNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified detail message.
     *
     * @param message the detail message describing the exception
     */
    public CommentNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified detail message
     * and cause.
     *
     * @param message the detail message describing the exception
     * @param cause   the cause of the exception
     */
    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }
}

package org.thewhitemage13.exception;

/**
 * Exception thrown when a requested comment is not found in the system.
 * <p>
 * This custom runtime exception is used to indicate that an operation
 * involving a comment has failed because the comment does not exist.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports detailed error messages for better debugging.</li>
 *     <li>Allows chaining of exceptions by accepting a {@code Throwable} cause.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class CommentNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code CommentNotFoundException} with no detail message or cause.
     */
    public CommentNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public CommentNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the underlying cause of the exception
     */
    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CommentNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }
}

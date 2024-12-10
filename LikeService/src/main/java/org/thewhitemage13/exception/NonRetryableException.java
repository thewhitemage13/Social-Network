package org.thewhitemage13.exception;

/**
 * Custom exception to indicate a non-retryable error condition.
 * <p>
 * This exception is thrown when an operation fails due to an issue that should not
 * be retried, such as invalid input, configuration errors, or other permanent issues.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException} to provide unchecked exception behavior.</li>
 *     <li>Designed for use in cases where retrying the operation would be futile.</li>
 *     <li>Includes constructors for detailed messages or exception chaining.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * throw new NonRetryableException("Invalid configuration detected.");
 * }</pre>
 *
 * @see RuntimeException
 * @see Throwable
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@code NonRetryableException} with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NonRetryableException} with the specified cause.
     *
     * @param cause the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *              (A {@code null} value is permitted and indicates that the cause is nonexistent or unknown.)
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}

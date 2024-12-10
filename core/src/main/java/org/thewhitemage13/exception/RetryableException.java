package org.thewhitemage13.exception;

/**
 * Exception indicating a retryable error condition.
 * <p>
 * This custom runtime exception is used to signal that an operation has failed
 * but may be retried, as the error condition is potentially transient or recoverable.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Specifies that the error may be retried for successful completion.</li>
 *     <li>Provides constructors to define error messages and causes for debugging purposes.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@code RetryableException} with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RetryableException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}

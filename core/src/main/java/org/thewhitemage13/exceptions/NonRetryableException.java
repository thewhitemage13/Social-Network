package org.thewhitemage13.exceptions;

/**
 * Exception indicating a non-retryable error condition.
 * <p>
 * This custom runtime exception is used to signal that an operation has failed
 * in a way that should not be retried. Typically, this is used in cases where
 * retrying the operation would result in the same failure or is deemed unnecessary.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Designed to distinguish non-retryable errors from other exceptions.</li>
 *     <li>Provides constructors to specify error messages and causes for debugging.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {


    /**
     * Constructs a new {@code NonRetryableException} with the specified detail message.
     *
     * @param message the detail message explaining why the exception occurred
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NonRetryableException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}

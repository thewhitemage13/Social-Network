package org.thewhitemage13.exception;

/**
 * Represents an exception that is non-retryable.
 * <p>
 * This exception is thrown to indicate that an operation has failed
 * and should not be retried. It extends {@link RuntimeException}, allowing
 * it to be used in scenarios where immediate handling of the exception is required.
 * </p>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception can be used in cases where retries are not feasible or meaningful,
 * such as validation errors or permanent issues with external systems.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@code NonRetryableException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
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

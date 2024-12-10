package org.thewhitemage13.exception;

/**
 * Represents an exception that is retryable.
 * <p>
 * This exception is used to indicate that an operation has failed, but the failure
 * is transient, and the operation may succeed if retried. It extends {@link RuntimeException},
 * allowing it to be used in scenarios where automatic retries are appropriate.
 * </p>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception can be used in cases where an operation might temporarily fail
 * due to network issues, resource unavailability, or other transient conditions,
 * and it is worth retrying the operation.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@code RetryableException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
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

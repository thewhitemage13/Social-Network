package org.thewhitemage13.exception;

/**
 * Custom exception to indicate a retryable error condition.
 * <p>
 * This exception is thrown when an operation fails due to an issue that can potentially
 * be resolved by retrying the operation, such as temporary network failures or service unavailability.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException} to provide unchecked exception behavior.</li>
 *     <li>Designed for cases where the failure is transient, and retrying the operation may succeed.</li>
 *     <li>Includes constructors for both a detailed message and exception chaining.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * throw new RetryableException("Temporary service unavailability, please try again.");
 * }</pre>
 *
 * @see RuntimeException
 * @see Throwable
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@code RetryableException} with the specified detail message.
     *
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RetryableException} with the specified cause.
     *
     * @param cause the cause of the exception, saved for later retrieval by the {@link #getCause()} method.
     *              (A {@code null} value is permitted and indicates that the cause is nonexistent or unknown.)
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}

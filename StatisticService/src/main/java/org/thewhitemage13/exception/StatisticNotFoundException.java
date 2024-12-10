package org.thewhitemage13.exception;

/**
 * Exception thrown when a specific statistic cannot be found.
 * <p>
 * This runtime exception is used to indicate that a requested statistic,
 * which is expected to exist, is missing or unavailable. It provides
 * multiple constructors for flexibility in exception handling.
 * </p>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception can be thrown when querying for a statistic in a system
 * and the requested data is not available. It extends {@link RuntimeException},
 * meaning it is an unchecked exception.
 * </p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Supports default, message-only, message and cause, and cause-only constructors.</li>
 *     <li>Encapsulates detailed information about the error scenario.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class StatisticNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code StatisticNotFoundException} with no detail message or cause.
     */
    public StatisticNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code StatisticNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public StatisticNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code StatisticNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the underlying cause of the exception, if any
     */
    public StatisticNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code StatisticNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception, if any
     */
    public StatisticNotFoundException(Throwable cause) {
        super(cause);
    }
}

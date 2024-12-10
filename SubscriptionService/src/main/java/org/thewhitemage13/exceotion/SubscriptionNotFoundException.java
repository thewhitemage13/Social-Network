package org.thewhitemage13.exceotion;

/**
 * Exception thrown when a subscription cannot be found in the system.
 * <p>
 * This runtime exception is used to indicate that an operation requiring
 * a specific subscription failed due to its absence.
 * </p>
 *
 * <h2>Constructors:</h2>
 * <ul>
 *     <li>{@link #SubscriptionNotFoundException()}: Creates an exception without any additional context.</li>
 *     <li>{@link #SubscriptionNotFoundException(String)}: Creates an exception with a detailed error message.</li>
 *     <li>{@link #SubscriptionNotFoundException(String, Throwable)}: Creates an exception with a detailed message and the root cause.</li>
 *     <li>{@link #SubscriptionNotFoundException(Throwable)}: Creates an exception with the root cause.</li>
 * </ul>
 *
 * <p>
 * This exception can be used in service or controller layers to signal issues
 * related to missing subscriptions.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class SubscriptionNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code SubscriptionNotFoundException} without a detail message.
     */
    public SubscriptionNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code SubscriptionNotFoundException} with a specified detail message.
     *
     * @param message the detail message that describes the reason for the exception
     */
    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code SubscriptionNotFoundException} with a specified detail message and cause.
     *
     * @param message the detail message that describes the reason for the exception
     * @param cause   the underlying cause of the exception
     */
    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code SubscriptionNotFoundException} with the specified cause.
     *
     * @param cause the underlying cause of the exception
     */
    public SubscriptionNotFoundException(Throwable cause) {
        super(cause);
    }
}

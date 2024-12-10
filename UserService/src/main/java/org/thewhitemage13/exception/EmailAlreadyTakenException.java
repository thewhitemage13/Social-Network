package org.thewhitemage13.exception;


/**
 * Exception thrown when attempting to register or update a user
 * with an email address that is already in use.
 * <p>
 * This exception extends {@link RuntimeException} and can be used to signal
 * a conflict in user registration or update operations.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports multiple constructors for flexibility in exception handling.</li>
 *     <li>Can include detailed messages and root causes for better debugging.</li>
 * </ul>
 *
 * @see RuntimeException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class EmailAlreadyTakenException extends RuntimeException {

    /**
     * Constructs a new {@code EmailAlreadyTakenException} with no detail message or cause.
     */
    public EmailAlreadyTakenException() {
        super();
    }

    /**
     * Constructs a new {@code EmailAlreadyTakenException} with the specified detail message.
     *
     * @param message the detail message
     */
    public EmailAlreadyTakenException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EmailAlreadyTakenException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code EmailAlreadyTakenException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public EmailAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}

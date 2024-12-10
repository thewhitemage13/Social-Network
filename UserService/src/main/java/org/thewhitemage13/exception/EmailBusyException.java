package org.thewhitemage13.exception;


/**
 * Exception thrown when an email address is already associated with an active or pending process,
 * and cannot be used at the moment.
 * <p>
 * This exception extends {@link RuntimeException} and is typically used in situations
 * where the email address cannot be utilized for a certain operation, such as during user registration,
 * password reset, or account verification processes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports multiple constructors for flexibility in exception handling.</li>
 *     <li>Can be used to provide detailed messages and capture the root cause of the issue.</li>
 * </ul>
 *
 * @see RuntimeException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class EmailBusyException extends RuntimeException {

    /**
     * Constructs a new {@code EmailBusyException} with no detail message or cause.
     */
    public EmailBusyException() {
        super();
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified detail message.
     *
     * @param message the detail message
     */
    public EmailBusyException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public EmailBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public EmailBusyException(Throwable cause) {
        super(cause);
    }
}

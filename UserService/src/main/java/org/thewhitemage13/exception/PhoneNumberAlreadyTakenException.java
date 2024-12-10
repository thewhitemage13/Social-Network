package org.thewhitemage13.exception;

/**
 * Exception thrown when a phone number is already taken by another user.
 * <p>
 * This exception extends {@link RuntimeException} and is typically used to signal
 * that a provided phone number is already associated with an existing account in the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports multiple constructors to provide flexibility in handling the exception.</li>
 *     <li>Can be used to provide a detailed message or capture the underlying cause.</li>
 * </ul>
 *
 * @see RuntimeException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class PhoneNumberAlreadyTakenException extends RuntimeException {

    /**
     * Constructs a new {@code PhoneNumberAlreadyTakenException} with no detail message or cause.
     */

    public PhoneNumberAlreadyTakenException() {
        super();
    }

    /**
     * Constructs a new {@code PhoneNumberAlreadyTakenException} with the specified detail message.
     *
     * @param message the detail message
     */
    public PhoneNumberAlreadyTakenException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code PhoneNumberAlreadyTakenException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public PhoneNumberAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code PhoneNumberAlreadyTakenException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public PhoneNumberAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}

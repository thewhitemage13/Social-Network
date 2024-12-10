package org.thewhitemage13.exception;

/**
 * Exception thrown when a phone number does not adhere to the expected format.
 * <p>
 * This exception extends {@link RuntimeException} and is typically used to signal
 * when a phone number provided by a user or system is not in a valid format, such as
 * incorrect length or containing invalid characters.
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
public class IncorrectPhoneNumberException extends RuntimeException {

    /**
     * Constructs a new {@code IncorrectPhoneNumberException} with no detail message or cause.
     */
    public IncorrectPhoneNumberException() {
        super();
    }

    /**
     * Constructs a new {@code IncorrectPhoneNumberException} with the specified detail message.
     *
     * @param message the detail message
     */
    public IncorrectPhoneNumberException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IncorrectPhoneNumberException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public IncorrectPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code IncorrectPhoneNumberException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public IncorrectPhoneNumberException(Throwable cause) {
        super(cause);
    }
}

package org.thewhitemage13.exception;

/**
 * Exception thrown when an email address does not adhere to the expected format.
 * <p>
 * This exception extends {@link RuntimeException} and is typically used to signal
 * when the email address provided by a user or system is not in a valid format,
 * such as missing the "@" symbol or domain part.
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
public class IncorrectEmailFormatException extends RuntimeException{

    /**
     * Constructs a new {@code IncorrectEmailFormatException} with no detail message or cause.
     */
    public IncorrectEmailFormatException() {
        super();
    }

    /**
     * Constructs a new {@code IncorrectEmailFormatException} with the specified detail message.
     *
     * @param message the detail message
     */
    public IncorrectEmailFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IncorrectEmailFormatException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public IncorrectEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code IncorrectEmailFormatException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public IncorrectEmailFormatException(Throwable cause) {
        super(cause);
    }
}

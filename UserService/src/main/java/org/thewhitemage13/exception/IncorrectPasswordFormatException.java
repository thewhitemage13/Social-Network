package org.thewhitemage13.exception;

/**
 * Exception thrown when a password does not adhere to the expected format.
 * <p>
 * This exception extends {@link RuntimeException} and is typically used to signal
 * when a password does not meet the required format, such as length or complexity constraints.
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
public class IncorrectPasswordFormatException extends RuntimeException{

    /**
     * Constructs a new {@code IncorrectPasswordFormatException} with no detail message or cause.
     */
    public IncorrectPasswordFormatException() {
        super();
    }

    /**
     * Constructs a new {@code IncorrectPasswordFormatException} with the specified detail message.
     *
     * @param message the detail message
     */
    public IncorrectPasswordFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IncorrectPasswordFormatException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public IncorrectPasswordFormatException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs a new {@code IncorrectPasswordFormatException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public IncorrectPasswordFormatException(Throwable cause) {
        super(cause);
    }
}

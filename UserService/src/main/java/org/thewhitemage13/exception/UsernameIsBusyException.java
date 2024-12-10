package org.thewhitemage13.exception;


/**
 * Exception thrown when a username is already taken by another user.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate that
 * the specified username is already in use, preventing the creation or update
 * of a user account with the same username.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports multiple constructors for providing flexibility in exception handling.</li>
 *     <li>Allows for passing a custom error message or an underlying cause for the exception.</li>
 * </ul>
 *
 * @see RuntimeException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UsernameIsBusyException extends RuntimeException {

    /**
     * Constructs a new {@code UsernameIsBusyException} with no detail message or cause.
     */
    public UsernameIsBusyException() {
        super();
    }

    /**
     * Constructs a new {@code UsernameIsBusyException} with the specified detail message.
     *
     * @param message the detail message
     */
    public UsernameIsBusyException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UsernameIsBusyException} with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public UsernameIsBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code UsernameIsBusyException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public UsernameIsBusyException(Throwable cause) {
        super(cause);
    }
}

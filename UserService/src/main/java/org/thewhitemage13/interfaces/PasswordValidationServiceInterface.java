package org.thewhitemage13.interfaces;

/**
 * Interface for validating user passwords.
 * <p>
 * This interface defines the method for validating the password during user registration or update.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #validatePassword(String)} validates the format and strength of the provided password.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PasswordValidationServiceInterface {

    /**
     * Validates the provided password.
     * <p>
     * This method ensures that the password meets the required format and strength criteria,
     * such as minimum length and complexity.
     * </p>
     *
     * @param password the password to be validated
     */
    void validatePassword(String password);
}

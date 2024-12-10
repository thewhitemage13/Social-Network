package org.thewhitemage13.interfaces;

import org.thewhitemage13.exception.EmailBusyException;

/**
 * Interface defining methods for email validation services.
 * <p>
 * This interface provides methods for validating email addresses during user registration and
 * email update operations. It ensures that email addresses are unique and not already in use.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #validateUpdateEmail(String)} validates an email during the update process.</li>
 *     <li>{@link #validateEmail(String)} validates an email during the registration process.</li>
 *     <li>Both methods throw an {@link EmailBusyException} if the email is already in use.</li>
 * </ul>
 *
 * @see EmailBusyException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface EmailValidationServiceInterface {

    /**
     * Validates the email address during an update process.
     * <p>
     * This method checks if the provided email is already in use by another user.
     * If the email is taken, an {@link EmailBusyException} is thrown.
     * </p>
     *
     * @param checkEmail the email address to be validated
     * @throws EmailBusyException if the email is already in use by another user
     */
    void validateUpdateEmail(String checkEmail) throws EmailBusyException;

    /**
     * Validates the email address during the registration process.
     * <p>
     * This method checks if the provided email is already in use by another user.
     * If the email is taken, an {@link EmailBusyException} is thrown.
     * </p>
     *
     * @param checkEmail the email address to be validated
     * @throws EmailBusyException if the email is already in use by another user
     */
    void validateEmail(String checkEmail) throws EmailBusyException;
}

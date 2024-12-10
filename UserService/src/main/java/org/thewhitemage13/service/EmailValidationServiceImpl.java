package org.thewhitemage13.service;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.IncorrectEmailFormatException;
import org.thewhitemage13.interfaces.EmailValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

/**
 * Service implementation for validating email addresses.
 * <p>
 * This service provides methods to validate the format and availability of email addresses
 * within the application. It ensures that emails follow the correct syntax and are not already
 * associated with an existing user.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Check the format of email addresses using {@link EmailValidator}.</li>
 *     <li>Verify if an email address is already taken in the system.</li>
 * </ul>
 *
 * <h2>Exceptions:</h2>
 * <ul>
 *     <li>{@link IncorrectEmailFormatException} - Thrown if the email format is invalid.</li>
 *     <li>{@link EmailBusyException} - Thrown if the email is already in use.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class EmailValidationServiceImpl implements EmailValidationServiceInterface {
    private final UserRepository userRepository;
    private final EmailValidator validator = EmailValidator.getInstance();

    /**
     * Constructs an instance of {@code EmailValidationServiceImpl}.
     *
     * @param userRepository the user repository for accessing user data
     */
    @Autowired
    public EmailValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates the format of an email during an update operation.
     * <p>
     * Ensures that the provided email is syntactically correct.
     * </p>
     *
     * @param checkEmail the email to validate
     * @throws IncorrectEmailFormatException if the email format is invalid
     */
    @Override
    public void validateUpdateEmail(String checkEmail) throws EmailBusyException {
        validator.isValid(checkEmail);

        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }

    /**
     * Validates the format and availability of an email.
     * <p>
     * Ensures that the provided email is syntactically correct and is not already
     * taken by another user in the system.
     * </p>
     *
     * @param checkEmail the email to validate
     * @throws EmailBusyException if the email is already taken
     * @throws IncorrectEmailFormatException if the email format is invalid
     */
    @Override
    public void validateEmail(String checkEmail) throws EmailBusyException {
        if(userRepository.existsUserByEmail(checkEmail)) {
            throw new EmailBusyException("Email = %s is already taken".formatted(checkEmail));
        }

        validator.isValid(checkEmail);

        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }
}

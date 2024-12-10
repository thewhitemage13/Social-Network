package org.thewhitemage13.service;

import org.passay.*;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.IncorrectPasswordFormatException;
import org.thewhitemage13.interfaces.PasswordValidationServiceInterface;

/**
 * Service implementation for validating passwords based on specified rules.
 * <p>
 * This service ensures that passwords adhere to a set of predefined rules for security
 * and robustness. It uses the Passay library to enforce these rules.
 * </p>
 *
 * <h2>Password Rules:</h2>
 * <ul>
 *     <li>At least one uppercase letter.</li>
 *     <li>At least one digit.</li>
 *     <li>At least one special character.</li>
 *     <li>No whitespace characters.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * Throws {@link IncorrectPasswordFormatException} if the password does not comply with the rules.
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class PasswordValidationServiceImpl implements PasswordValidationServiceInterface {

    /**
     * Validates the provided password against predefined security rules.
     * <p>
     * Uses the {@link PasswordValidator} from the Passay library to enforce the following rules:
     * <ul>
     *     <li>At least one uppercase letter.</li>
     *     <li>At least one digit.</li>
     *     <li>At least one special character.</li>
     *     <li>No whitespace characters.</li>
     * </ul>
     *
     * @param password the password to validate
     * @throws IncorrectPasswordFormatException if the password does not meet the security rules
     */
    @Override
    public void validatePassword(String password) {

        PasswordValidator validator = new PasswordValidator(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            throw new IncorrectPasswordFormatException("Incorrect password format");
        }
    }

}

package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.UsernameIsBusyException;
import org.thewhitemage13.interfaces.UserActivityValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

/**
 * Service implementation for validating user activity-related information during user updates.
 * <p>
 * This service validates changes made to user details like email, phone number, and username.
 * It ensures that the new values do not conflict with existing records in the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates email, phone number, and username during user profile updates.</li>
 *     <li>Ensures that changes in email and phone number follow the required format and do not conflict with existing records.</li>
 *     <li>Checks whether a new username is available.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link EmailValidationServiceImpl} - Service for email validation.</li>
 *     <li>{@link PhoneValidationServiceImpl} - Service for phone number validation.</li>
 *     <li>{@link UserRepository} - Repository to interact with the database for user data.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 *     <li>{@link EmailBusyException} - Thrown if the email is already taken.</li>
 *     <li>{@link UsernameIsBusyException} - Thrown if the username is already in use.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class UserActivityValidationServiceImpl implements UserActivityValidationServiceInterface {
    private final UserRepository userRepository;
    private final EmailValidationServiceImpl emailValidationServiceImpl;
    private final PhoneValidationServiceImpl phoneValidationServiceImpl;

    /**
     * Constructor for dependency injection.
     *
     * @param userRepository the repository for user data
     * @param emailValidationServiceImpl the service for email validation
     * @param phoneValidationServiceImpl the service for phone number validation
     */
    @Autowired
    public UserActivityValidationServiceImpl
    (
            UserRepository userRepository,
            EmailValidationServiceImpl emailValidationServiceImpl,
            PhoneValidationServiceImpl phoneValidationServiceImpl
    ) {
        this.userRepository = userRepository;
        this.emailValidationServiceImpl = emailValidationServiceImpl;
        this.phoneValidationServiceImpl = phoneValidationServiceImpl;
    }

    /**
     * Validates the user details during an update.
     * <p>
     * This method checks if the email, phone number, or username provided in the update request
     * conflicts with existing user data. It throws the appropriate exceptions if any issues are found.
     * </p>
     *
     * @param createUserDTO the DTO containing the updated user details
     * @param updateUser the existing user entity being updated
     * @return the formatted phone number if valid
     * @throws EmailBusyException if the email is already in use by another user
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String updateUserValidationProcessor(CreateUserDTO createUserDTO, User updateUser) throws EmailBusyException, NumberParseException {

        if (!updateUser.getEmail().equals(createUserDTO.getEmail())) {
            emailValidationServiceImpl.validateEmail(createUserDTO.getEmail());
        } else {
            emailValidationServiceImpl.validateUpdateEmail(createUserDTO.getEmail());
        }

        String phoneNum;

        if (!updateUser.getPhoneNumber().equals(createUserDTO.getPhoneNumber())) {
            phoneNum = phoneValidationServiceImpl.validatePhoneNumber(createUserDTO.getPhoneNumber(), createUserDTO.getRegion());
        } else {
            phoneNum = phoneValidationServiceImpl.validateUpdatePhoneNumber(createUserDTO.getPhoneNumber(), createUserDTO.getRegion());
        }

        if (!updateUser.getUsername().equals(createUserDTO.getUsername())) {
            validateUsername(createUserDTO.getUsername());
        }

        return phoneNum;
    }

    /**
     * Validates if the given username is already in use.
     * <p>
     * If the username is already taken by another user, a {@link UsernameIsBusyException} is thrown.
     * </p>
     *
     * @param username the username to validate
     * @throws UsernameIsBusyException if the username is already in use
     */
    @Override
    public void validateUsername(String username) {
        boolean user = userRepository.existsUserByUsername(username);
        if (user) {
            throw new UsernameIsBusyException("User with username = %s is busy".formatted(username));
        }
    }
}

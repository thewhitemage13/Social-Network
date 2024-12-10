package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

/**
 * Implementation of the {@link ValidationServiceInterface} that validates the existence of a user.
 * <p>
 * This service is responsible for checking if a user exists by contacting an external user service
 * via the {@link UserClient}. It throws a {@link UserNotFoundException} if the user cannot be found.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates the existence of a user by ID using the {@link UserClient}.</li>
 *     <li>Throws a {@link UserNotFoundException} if the user does not exist.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final UserClient userClient;

    /**
     * Constructs a new {@code ValidationServiceImpl} with the specified {@link UserClient}.
     * <p>
     * This constructor is responsible for injecting the {@link UserClient}, which will be used to
     * verify the existence of users in an external service.
     * </p>
     *
     * @param userClient the {@link UserClient} used for verifying user existence
     */
    @Autowired
    public ValidationServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * Validates the existence of a user by their ID.
     * <p>
     * This method contacts the external user service through the {@link UserClient} to verify if
     * a user exists. If the user is not found, it throws a {@link UserNotFoundException}.
     * </p>
     *
     * @param userId the ID of the user to validate
     * @throws UserNotFoundException if the user cannot be found
     */
    @Override
    public void validateUser(Long userId) {
        ResponseEntity<Boolean> isCreated = userClient.verifyUserExistence(userId);
        Boolean status = isCreated.getBody();

        if(Boolean.FALSE.equals(status)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(userId));
        }
    }
}

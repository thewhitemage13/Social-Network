package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.interfaces.UserValidationServiceInterface;

/**
 * Implementation of the {@link UserValidationServiceInterface}, providing user validation services.
 * <p>
 * This service validates whether a user exists by calling an external service via the {@link UserClient}.
 * It is used to ensure that a post is associated with an existing user before performing further operations.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class UserValidationServiceImpl implements UserValidationServiceInterface {
    private final UserClient userClient;

    /**
     * Constructs a new {@code UserValidationServiceImpl} with the specified {@link UserClient} dependency.
     * <p>
     * The constructor injects the required {@link UserClient} for communicating with the external service
     * to verify the existence of a user by their ID.
     * </p>
     *
     * @param userClient the {@link UserClient} for verifying user existence
     */
    @Autowired
    public UserValidationServiceImpl(UserClient userClient) {
        this.userClient = userClient;
    }

    /**
     * Validates whether a user exists by calling an external service.
     * <p>
     * This method checks if a user with the specified ID exists. If the user does not exist,
     * a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param createPostDTO the {@link CreatePostDTO} object containing the user ID to validate
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    @Override
    public void validateUser(CreatePostDTO createPostDTO){
        Boolean status;
        ResponseEntity<Boolean> isCreateUser = userClient.verifyUserExistence(createPostDTO.getUserId());
        status = isCreateUser.getBody();

        if (Boolean.FALSE.equals(status)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(createPostDTO.getUserId()));
        }
    }
}

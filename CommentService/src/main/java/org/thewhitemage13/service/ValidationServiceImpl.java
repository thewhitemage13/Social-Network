package org.thewhitemage13.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.exception.PostNotFoundException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

/**
 * Implementation of the {@link ValidationServiceInterface} that provides validation services
 * for checking the existence of a user and a post before creating or updating a comment.
 * <p>
 * This service ensures that the user associated with a comment exists and that the post to which
 * the comment is being made is valid. If either the user or the post does not exist, appropriate
 * exceptions are thrown.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates the existence of a user before processing a comment.</li>
 *     <li>Validates the existence of a post before processing a comment.</li>
 *     <li>Throws custom exceptions if the user or post is not found.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final PostClient postClient;
    private final UserClient userClient;

    /**
     * Constructs a new {@code ValidationServiceImpl} with the specified {@code PostClient}
     * and {@code UserClient}.
     *
     * @param postClient the client used to verify the existence of posts
     * @param userClient the client used to verify the existence of users
     */
    public ValidationServiceImpl(PostClient postClient, UserClient userClient) {
        this.postClient = postClient;
        this.userClient = userClient;
    }

    /**
     * Validates if the user exists for a given comment creation request.
     * <p>
     * This method calls the {@link UserClient} to verify the existence of the user by their ID.
     * If the user is not found, a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param commentCreateDto the data transfer object containing the comment's user ID
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public void validateUser(CommentCreateDto commentCreateDto) {
        Boolean status;

        ResponseEntity<Boolean> userIsCreate = userClient.verifyUserExistence(commentCreateDto.getUserId());
        status = userIsCreate.getBody();

        if (Boolean.FALSE.equals(status)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(commentCreateDto.getUserId()));
        }
    }

    /**
     * Validates if the post exists for a given comment creation request.
     * <p>
     * This method calls the {@link PostClient} to verify the existence of the post by its ID.
     * If the post is not found, a {@link PostNotFoundException} is thrown.
     * </p>
     *
     * @param commentCreateDto the data transfer object containing the comment's post ID
     * @throws PostNotFoundException if the post does not exist
     */
    @Override
    public void validatePost(CommentCreateDto commentCreateDto) {
        Boolean status;

        ResponseEntity<Boolean> postIsCreate = postClient.postVerification(commentCreateDto.getPostId());
        status = postIsCreate.getBody();

        if (Boolean.FALSE.equals(status)) {
            throw new PostNotFoundException("Post with id = %s not found".formatted(commentCreateDto.getPostId()));
        }
    }

}

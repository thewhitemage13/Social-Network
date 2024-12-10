package org.thewhitemage13.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.CommentClient;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.exceptions.CommentNotFoundException;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

/**
 * Service implementation for validating like requests for posts and comments.
 * <p>
 * This service is responsible for validating that the post, comment, and user involved in a like operation exist
 * before proceeding. It interacts with external clients, such as {@link PostClient}, {@link CommentClient}, and {@link UserClient},
 * to verify the existence of the corresponding entities. If any entity does not exist, it throws the appropriate exception.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates whether a post exists before liking it.</li>
 *     <li>Validates whether a comment exists before liking it.</li>
 *     <li>Validates whether a user exists before allowing them to like a post or comment.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final CommentClient commentClient;
    private final PostClient postClient;
    private final UserClient userClient;

    /**
     * Constructs a new {@code ValidationServiceImpl} with the specified dependencies.
     *
     * @param commentClient the client for interacting with comments
     * @param postClient the client for interacting with posts
     * @param userClient the client for interacting with users
     */
    public ValidationServiceImpl(CommentClient commentClient, PostClient postClient, UserClient userClient) {
        this.commentClient = commentClient;
        this.postClient = postClient;
        this.userClient = userClient;
    }

    /**
     * Validates that the post and user involved in the like operation exist.
     * <p>
     * This method verifies that the specified post and user IDs exist by calling external services. If any of the entities
     * do not exist, it throws an appropriate exception.
     * </p>
     *
     * @param createLikePost the data required to like a post
     * @throws PostNotFoundException if the post with the specified ID does not exist
     * @throws UserNotFoundException if the user with the specified ID does not exist
     */
    @Override
    public void validatePostLike(CreateLikePost createLikePost) {
        Boolean status;

        ResponseEntity<Boolean> isCreatedPost = postClient.postVerification(createLikePost.getPostId());
        status = isCreatedPost.getBody();

        if(Boolean.FALSE.equals(status)) {
            throw new PostNotFoundException("Post with id = %s not found".formatted(createLikePost.getPostId()));
        }

        ResponseEntity<Boolean> isCreatedUser = userClient.verifyUserExistence(createLikePost.getUserId());
        status = isCreatedUser.getBody();

        if(Boolean.FALSE.equals(status)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(createLikePost.getUserId()));
        }
    }


    /**
     * Validates that the comment and user involved in the like operation exist.
     * <p>
     * This method verifies that the specified comment and user IDs exist by calling external services. If any of the entities
     * do not exist, it throws an appropriate exception.
     * </p>
     *
     * @param createLikeComment the data required to like a comment
     * @throws CommentNotFoundException if the comment with the specified ID does not exist
     * @throws UserNotFoundException if the user with the specified ID does not exist
     */
    @Override
    public void validateCommentLike(CreateLikeComment createLikeComment) {
        Boolean status;

        ResponseEntity<Boolean> isCreatedComment = commentClient.commentVerification(createLikeComment.getCommentId());
        status = isCreatedComment.getBody();

        if(Boolean.FALSE.equals(status)) {
            throw new CommentNotFoundException("Comment with id = %s not found".formatted(createLikeComment.getCommentId()));
        }

        ResponseEntity<Boolean> isCreatedUser = userClient.verifyUserExistence(createLikeComment.getUserId());
        status = isCreatedUser.getBody();

        if(Boolean.FALSE.equals(status)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(createLikeComment.getUserId()));
        }
    }
}

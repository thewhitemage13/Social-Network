package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;

/**
 * Interface that defines the contract for validation services related to post and comment likes.
 * <p>
 * This interface provides methods for validating the "like" operations for posts and comments,
 * ensuring that the input data is correct and meets the necessary business logic requirements.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validation for liking a post, ensuring the user and post data is valid.</li>
 *     <li>Validation for liking a comment, ensuring the user and comment data is valid.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * ValidationServiceInterface validationService = new ValidationServiceImpl();
 * validationService.validatePostLike(new CreateLikePost(userId, postId));
 * }</pre>
 *
 * @see CreateLikePost
 * @see CreateLikeComment
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the data required to like a post.
     * <p>
     * This method checks the validity of the user and post information in the given
     * {@link CreateLikePost} DTO. It ensures that the user exists and that the post is valid
     * for liking.
     * </p>
     *
     * @param createLikePost the {@link CreateLikePost} object containing the user and post information
     * @throws IllegalArgumentException if the post or user is invalid
     */
    void validatePostLike(CreateLikePost createLikePost);

    /**
     * Validates the data required to like a comment.
     * <p>
     * This method checks the validity of the user and comment information in the given
     * {@link CreateLikeComment} DTO. It ensures that the user exists and that the comment is valid
     * for liking.
     * </p>
     *
     * @param createLikeComment the {@link CreateLikeComment} object containing the user and comment information
     * @throws IllegalArgumentException if the comment or user is invalid
     */
    void validateCommentLike(CreateLikeComment createLikeComment);
}

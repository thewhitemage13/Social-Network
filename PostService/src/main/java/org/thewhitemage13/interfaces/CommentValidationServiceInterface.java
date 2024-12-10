package org.thewhitemage13.interfaces;

/**
 * Interface for validating comments associated with posts.
 * <p>
 * This interface defines methods related to the validation of comments in the system.
 * It includes operations to check or count validations related to comments,
 * such as the number of valid comments for a specific post.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface CommentValidationServiceInterface {

    /**
     * Counts the number of validated comments for a specific post.
     * <p>
     * This method is designed to return the total number of validated comments
     * associated with the given {@code postId}. It can be used to assess
     * the engagement or approval status of comments for a particular post.
     * </p>
     *
     * @param postId the ID of the post whose comments are being validated
     * @return the number of validated comments for the given post
     */
    Long countCommentValidation(Long postId);
}

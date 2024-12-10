package org.thewhitemage13.interfaces;

/**
 * Interface for validating likes associated with posts.
 * <p>
 * This interface defines methods related to the validation of likes in the system.
 * It includes operations to check or count the number of likes associated with a
 * particular post, providing insights into the engagement level of posts.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeValidationServiceInterface {

    /**
     * Counts the number of validated likes for a specific post.
     * <p>
     * This method is designed to return the total number of validated likes
     * associated with the given {@code postId}. It helps to assess the popularity
     * or approval of a post based on the number of likes.
     * </p>
     *
     * @param postId the ID of the post whose likes are being validated
     * @return the number of validated likes for the given post
     */
    Long countLikeValidation(Long postId);
}

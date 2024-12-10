package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreatePostDTO;

/**
 * Interface for validating various aspects of post-related data.
 * <p>
 * This interface defines methods for validating different components related to posts,
 * such as comments, likes, media, and user data. These methods ensure that the
 * provided data adheres to the necessary validation rules before allowing
 * operations like creating or interacting with posts.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the number of comments associated with a post.
     * <p>
     * This method checks the number of comments for a specific post identified
     * by its {@code postId}. It returns the count of comments that meet
     * the validation criteria.
     * </p>
     *
     * @param postId the ID of the post whose comments are to be validated
     * @return the count of validated comments for the post
     */
    Long validateComment(Long postId);

    /**
     * Validates the number of likes associated with a post.
     * <p>
     * This method checks the number of likes for a specific post identified
     * by its {@code postId}. It returns the count of likes that meet
     * the validation criteria.
     * </p>
     *
     * @param postId the ID of the post whose likes are to be validated
     * @return the count of validated likes for the post
     */
    Long validateLike(Long postId);

    /**
     * Validates the media data provided for post creation.
     * <p>
     * This method checks the {@link CreatePostDTO} for valid media data
     * before a post is created. It ensures that any media files associated
     * with the post meet the necessary requirements, such as size, format, etc.
     * </p>
     *
     * @param createPostDTO the data transfer object containing the media
     *                      details for post creation
     */
    void validateMedia(CreatePostDTO createPostDTO);

    /**
     * Validates the user data provided for post creation.
     * <p>
     * This method checks the user information in the {@link CreatePostDTO}
     * to ensure that it meets the validation criteria before the post is created.
     * It may include checks for valid user IDs, user permissions, or other business rules.
     * </p>
     *
     * @param createPostDTO the data transfer object containing the user
     *                      details for post creation
     * @throws IllegalArgumentException if the user data is invalid or does
     *                                  not meet the criteria
     */
    void validateUser(CreatePostDTO createPostDTO);
}

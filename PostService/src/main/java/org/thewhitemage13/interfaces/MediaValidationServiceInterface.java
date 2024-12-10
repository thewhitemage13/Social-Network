package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreatePostDTO;

/**
 * Interface for validating media associated with post creation.
 * <p>
 * This interface defines methods related to the validation of media
 * when creating a post. It ensures that the media provided in the post
 * creation request is valid before the post is created or processed.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaValidationServiceInterface {

    /**
     * Validates the media associated with the creation of a post.
     * <p>
     * This method checks the media data in the {@link CreatePostDTO} to
     * ensure it meets the necessary validation criteria before a post
     * is created. This might include checks for valid media formats,
     * required fields, or other business rules related to post media.
     * </p>
     *
     * @param createPostDTO the data transfer object containing the media
     *                      and details for creating a post
     * @throws IllegalArgumentException if the media in the provided DTO
     *                                  is invalid or does not meet the criteria
     */
    void isCreateMedia(CreatePostDTO createPostDTO);
}

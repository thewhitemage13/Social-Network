package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreatePostDTO;

/**
 * Interface for validating users when creating posts.
 * <p>
 * This interface defines the method for validating user-related data in the
 * context of post creation. It ensures that the provided user data in
 * the {@link CreatePostDTO} is valid before allowing a post to be created.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserValidationServiceInterface {

    /**
     * Validates the user data provided in the post creation request.
     * <p>
     * This method checks the data in the {@link CreatePostDTO} to ensure
     * that the user meets the necessary validation criteria before a post
     * is created. It may include checks for valid user IDs, user status,
     * or other business rules.
     * </p>
     *
     * @param createPostDTO the data transfer object containing the user
     *                      details for post creation
     * @throws IllegalArgumentException if the user data is invalid or does
     *                                  not meet the criteria
     */
    void validateUser(CreatePostDTO createPostDTO);
}

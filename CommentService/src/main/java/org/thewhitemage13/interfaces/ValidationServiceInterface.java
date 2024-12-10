package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CommentCreateDto;

/**
 * Defines the contract for validation services in the application.
 * <p>
 * This interface specifies methods for validating user and post data before
 * further processing. Implementations of this interface are responsible for
 * ensuring the integrity and correctness of the provided data.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Validate the existence and validity of users based on the provided {@link CommentCreateDto}.</li>
 *     <li>Validate the existence and validity of posts based on the provided {@link CommentCreateDto}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This interface is intended to be implemented by services that handle business logic
 * for data validation. Implementations ensure that invalid or non-existent entities
 * are identified before proceeding with operations such as comment creation.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the user specified in the provided {@link CommentCreateDto}.
     * <p>
     * This method ensures that the user exists and is valid before allowing further
     * processing of the comment data. It may throw exceptions if the user is invalid
     * or does not exist.
     * </p>
     *
     * @param commentCreateDto the data transfer object containing the user ID to validate
     */
    void validateUser(CommentCreateDto commentCreateDto);

    /**
     * Validates the post specified in the provided {@link CommentCreateDto}.
     * <p>
     * This method ensures that the post exists and is valid before allowing further
     * processing of the comment data. It may throw exceptions if the post is invalid
     * or does not exist.
     * </p>
     *
     * @param commentCreateDto the data transfer object containing the post ID to validate
     */
    void validatePost(CommentCreateDto commentCreateDto);
}

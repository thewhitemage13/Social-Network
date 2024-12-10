package org.thewhitemage13.service;

import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

/**
 * Implementation of the {@link ValidationServiceInterface}, providing various validation services.
 * <p>
 * This service orchestrates the validation of comments, likes, media, and users by delegating the validation logic
 * to respective service classes: {@link CommentValidationServiceImpl}, {@link LikeValidationServiceImpl},
 * {@link MediaValidationServiceImpl}, and {@link UserValidationServiceImpl}.
 * </p>
 * <p>
 * It is used to validate all aspects of a post before performing operations such as creation or update.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final CommentValidationServiceImpl commentValidationServiceImpl;
    private final LikeValidationServiceImpl likeValidationServiceImpl;
    private final MediaValidationServiceImpl mediaValidationServiceImpl;
    private final UserValidationServiceImpl userValidationServiceImpl;

    /**
     * Constructs a new {@code ValidationServiceImpl} with the specified validation service dependencies.
     * <p>
     * The constructor injects dependencies for comment, like, media, and user validation services, which
     * are used to validate different aspects of a post.
     * </p>
     *
     * @param commentValidationServiceImpl the {@link CommentValidationServiceImpl} for comment validation
     * @param likeValidationServiceImpl the {@link LikeValidationServiceImpl} for like validation
     * @param mediaValidationServiceImpl the {@link MediaValidationServiceImpl} for media validation
     * @param userValidationServiceImpl the {@link UserValidationServiceImpl} for user validation
     */
    public ValidationServiceImpl
            (
                    CommentValidationServiceImpl commentValidationServiceImpl,
                    LikeValidationServiceImpl likeValidationServiceImpl,
                    MediaValidationServiceImpl mediaValidationServiceImpl,
                    UserValidationServiceImpl userValidationServiceImpl
            ) {
        this.commentValidationServiceImpl = commentValidationServiceImpl;
        this.likeValidationServiceImpl = likeValidationServiceImpl;
        this.mediaValidationServiceImpl = mediaValidationServiceImpl;
        this.userValidationServiceImpl = userValidationServiceImpl;
    }

    /**
     * Validates the comments associated with a given post.
     * <p>
     * This method delegates to {@link CommentValidationServiceImpl} to count the comments for the post
     * with the specified ID.
     * </p>
     *
     * @param postId the ID of the post to validate comments
     * @return the count of comments associated with the post
     */
    @Override
    public Long validateComment(Long postId) {
        return commentValidationServiceImpl.countCommentValidation(postId);
    }

    /**
     * Validates the likes associated with a given post.
     * <p>
     * This method delegates to {@link LikeValidationServiceImpl} to count the likes for the post
     * with the specified ID.
     * </p>
     *
     * @param postId the ID of the post to validate likes
     * @return the count of likes associated with the post
     */
    @Override
    public Long validateLike(Long postId) {
        return likeValidationServiceImpl.countLikeValidation(postId);
    }

    /**
     * Validates the media associated with a post.
     * <p>
     * This method delegates to {@link MediaValidationServiceImpl} to check whether the media for the post is valid.
     * </p>
     *
     * @param createPostDTO the {@link CreatePostDTO} object containing the media information to validate
     */
    @Override
    public void validateMedia(CreatePostDTO createPostDTO) {
        mediaValidationServiceImpl.isCreateMedia(createPostDTO);
    }

    /**
     * Validates the user associated with a post.
     * <p>
     * This method delegates to {@link UserValidationServiceImpl} to verify that the user exists.
     * </p>
     *
     * @param createPostDTO the {@link CreatePostDTO} object containing the user ID to validate
     */
    @Override
    public void validateUser(CreatePostDTO createPostDTO) {
        userValidationServiceImpl.validateUser(createPostDTO);
    }

}

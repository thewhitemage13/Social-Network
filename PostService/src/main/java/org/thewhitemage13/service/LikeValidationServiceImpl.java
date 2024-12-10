package org.thewhitemage13.service;

import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.LikeClient;
import org.thewhitemage13.interfaces.LikeValidationServiceInterface;

/**
 * Service implementation for validating likes related to posts.
 * <p>
 * This service interacts with an external service through the {@link LikeClient} to
 * retrieve the number of likes associated with a specific post. If the external
 * service call fails, it provides a fallback mechanism by returning a default value of 0.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class LikeValidationServiceImpl implements LikeValidationServiceInterface {
    private final LikeClient likeClient;

    /**
     * Constructs a new {@code LikeValidationServiceImpl} with the specified {@link LikeClient}.
     * <p>
     * The constructor injects the {@link LikeClient} instance, which is used to communicate
     * with the external like service to retrieve the count of likes for a given post.
     * </p>
     *
     * @param likeClient the {@link LikeClient} used to interact with the like service
     */
    public LikeValidationServiceImpl(LikeClient likeClient) {
        this.likeClient = likeClient;
    }


    /**
     * Counts the number of likes associated with a specific post.
     * <p>
     * This method calls the external like service via the {@link LikeClient} to retrieve
     * the number of likes for a given post identified by {@code postId}. If the external
     * service call fails, the method returns 0 as a fallback value.
     * </p>
     *
     * @param postId the ID of the post whose likes are to be counted
     * @return the number of likes for the post, or 0 if an error occurs during the service call
     */
    @Override
    public Long countLikeValidation(Long postId) {
        Long likes;
        try {
            likes = likeClient.getPostLikeCount(postId).getBody();
        } catch (Exception e) {
            likes = 0L;
        }
        return likes;
    }
}

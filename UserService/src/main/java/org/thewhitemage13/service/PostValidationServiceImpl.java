package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.interfaces.PostValidationServiceInterface;

/**
 * Service implementation for validating and retrieving post-related data for users.
 * <p>
 * This service interacts with a {@link PostClient} to fetch the count of posts associated with a user.
 * It ensures that the application can retrieve post data in a way that is fault-tolerant,
 * providing a fallback value in case of errors.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieves the count of posts made by a user.</li>
 *     <li>Handles communication with the Post service and provides error handling.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link PostClient} - Client for interacting with the external service to get post data.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * In case of failure to retrieve post data, the service will return a default value of 0 posts.
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class PostValidationServiceImpl implements PostValidationServiceInterface {
    private final PostClient postClient;

    /**
     * Constructor for dependency injection.
     *
     * @param postClient the client for accessing post-related data
     */
    @Autowired
    public PostValidationServiceImpl(PostClient postClient) {
        this.postClient = postClient;
    }

    /**
     * Validates and retrieves the number of posts for a given user.
     * <p>
     * If an error occurs while fetching the post data, the method returns a fallback value of 0.
     * </p>
     *
     * @param userId the ID of the user whose post count is being retrieved
     * @return the number of posts made by the user, or 0 if an error occurs
     */
    @Override
    public Long countPostValidation(Long userId) {
        Long countPosts;
        try {
            countPosts = postClient.getPostCountByUserId(userId).getBody();
        } catch (Exception e) {
            countPosts = 0L;
        }
        return countPosts;
    }
}

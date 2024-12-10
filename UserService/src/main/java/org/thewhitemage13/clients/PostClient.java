package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Represents a Feign client for interacting with the Post Service.
 * <p>
 * This interface facilitates communication with the "post-service" microservice,
 * allowing retrieval of user-related post information. It uses Spring Cloud OpenFeign
 * for declarative HTTP client support.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve the count of posts created by a specific user.</li>
 *     <li>Fetch a list of media URLs associated with a user's posts.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * PostClient postClient = ...; // Injected instance
 * ResponseEntity<Long> postCount = postClient.getPostCountByUserId(123L);
 * ResponseEntity<List<String>> mediaUrls = postClient.getMediaUrlByUserId(123L);
 * }</pre>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "post-service", path = "/posts")
public interface PostClient {

    /**
     * Retrieves the total number of posts created by a specific user.
     * <p>
     * Sends a GET request to the Post Service to count posts associated with the given user ID.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@code ResponseEntity<Long>} containing the count of posts created by the user
     */
    @GetMapping("/user/{userId}/count")
    ResponseEntity<Long> getPostCountByUserId(@PathVariable Long userId);

    /**
     * Retrieves a list of media URLs associated with a user's posts.
     * <p>
     * Sends a GET request to the Post Service to fetch all media URLs from posts created by the given user ID.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@code ResponseEntity<List<String>>} containing the list of media URLs
     */
    @GetMapping("/user/{userId}/media")
    ResponseEntity<List<String>> getMediaUrlByUserId(@PathVariable Long userId);
}

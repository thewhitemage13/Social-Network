package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Represents a Feign client for interacting with the Subscription Service.
 * <p>
 * This interface facilitates communication with the "subscription-service" microservice,
 * allowing retrieval of subscription-related information such as follower and following counts
 * for a specific user. It leverages Spring Cloud OpenFeign for declarative HTTP client functionality.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve the count of followers for a specific user.</li>
 *     <li>Retrieve the count of users that a specific user is following.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * SubscriptionClient subscriptionClient = ...; // Injected instance
 * ResponseEntity<Long> followerCount = subscriptionClient.countFollowers(123L);
 * ResponseEntity<Long> followingCount = subscriptionClient.countFollowing(123L);
 * }</pre>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "subscription-service", path = "/users")
public interface SubscriptionClient {

    /**
     * Retrieves the count of followers for a specific user.
     * <p>
     * Sends a GET request to the Subscription Service to count the number of users
     * following the specified user ID.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@code ResponseEntity<Long>} containing the count of followers
     */
    @GetMapping("/{userId}/followers/count")
    ResponseEntity<Long> countFollowers(@PathVariable Long userId);

    /**
     * Retrieves the count of users that a specific user is following.
     * <p>
     * Sends a GET request to the Subscription Service to count the number of users
     * the specified user ID is following.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@code ResponseEntity<Long>} containing the count of followed users
     */
    @GetMapping("/{userId}/following/count")
    ResponseEntity<Long> countFollowing(@PathVariable Long userId) ;
}

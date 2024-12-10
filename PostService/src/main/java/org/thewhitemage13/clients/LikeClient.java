package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Like Service.
 * <p>
 * This client provides methods to communicate with the "like-service",
 * enabling seamless integration between microservices.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieves the count of likes for a specific post by its ID.</li>
 *     <li>Uses Feign for declarative HTTP client creation.</li>
 *     <li>Incorporates Spring Cloud's integration for easy communication with other services.</li>
 * </ul>
 *
 * <p>
 * This interface is marked with {@code @FeignClient} annotation, enabling
 * it to act as a proxy for remote calls to the "like-service".
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "like-service", path = "/likes")
public interface LikeClient {

    /**
     * Retrieves the count of likes associated with a specific post.
     * <p>
     * Sends a GET request to the "like-service" to fetch the total number
     * of likes linked to the provided {@code postId}.
     * </p>
     *
     * @param postId the unique identifier of the post
     * @return a {@link ResponseEntity} containing the count of likes as a {@code Long}
     */
    @GetMapping("/posts/{postId}/count")
    ResponseEntity<Long> getPostLikeCount(@PathVariable("postId") Long postId);
}

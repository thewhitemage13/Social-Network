package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Post Service.
 * <p>
 * This client communicates with the {@code post-service} microservice to retrieve information
 * about posts and their associated users. The base URL path for this service is {@code /posts}.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve the user ID associated with a specific post.</li>
 *     <li>Uses Spring Cloud OpenFeign for declarative REST client functionality.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "post-service", path = "/posts")
public interface PostClient {


    /**
     * Retrieves the user ID associated with a specific post by its ID.
     * <p>
     * This method performs a {@code GET} request to the {@code /{postId}/user} endpoint of the
     * Post Service. The response contains the user ID in a {@link ResponseEntity}.
     * </p>
     *
     * @param postId the unique identifier of the post
     * @return a {@link ResponseEntity} containing the user ID associated with the post
     */
    @GetMapping("/{postId}/user")
    ResponseEntity<Long> getUserIdByPostId(@PathVariable Long postId) ;
}

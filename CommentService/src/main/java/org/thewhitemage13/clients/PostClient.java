package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the Post Service API.
 * <p>
 * This interface facilitates communication with the Post Service to perform operations
 * related to post verification. It uses Spring Cloud OpenFeign to provide declarative
 * REST client capabilities.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Defines the base path and service name for connecting to the Post Service.</li>
 *     <li>Includes a method for verifying posts by their unique identifier.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This client can be used by other services or components within the system
 * to verify the existence or validity of a post.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "post-service", path = "/posts")
public interface PostClient {

    /**
     * Verifies the existence or validity of a post by its unique identifier.
     * <p>
     * This method sends a GET request to the {@code /post-verification} endpoint
     * of the Post Service, passing the post ID as a request parameter.
     * The service responds with a {@link ResponseEntity} containing a Boolean value
     * indicating whether the post is valid.
     * </p>
     *
     * @param postId the unique identifier of the post to verify
     * @return a {@link ResponseEntity} containing {@code true} if the post is valid, {@code false} otherwise
     */
    @GetMapping("/post-verification")
    ResponseEntity<Boolean> postVerification(@RequestParam("postId") Long postId) ;
}

package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the Post Service.
 * <p>
 * This interface provides methods to facilitate communication between the application
 * and the external "post-service" microservice. It uses Spring Cloud OpenFeign
 * for declarative REST API calls, simplifying microservice integration.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Declarative communication with the Post Service using Feign Client.</li>
 *     <li>Endpoint for verifying the validity or existence of a specific post.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To use this client, include it in a Spring-managed bean and invoke the provided
 * methods to interact with the Post Service.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "post-service", path = "/posts")
public interface PostClient {

    /**
     * Verifies a specific post by its unique identifier.
     * <p>
     * This method sends a GET request to the "/posts/post-verification" endpoint
     * of the Post Service to determine the validity or existence of a post.
     * </p>
     *
     * @param postId the unique identifier of the post to be verified
     * @return a {@link ResponseEntity} containing a {@code Boolean} value:
     *         {@code true} if the post is valid, or {@code false} otherwise
     */
    @GetMapping("/post-verification")
    ResponseEntity<Boolean> postVerification(@RequestParam("postId") Long postId);
}

package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the Comment Service.
 * <p>
 * This interface provides methods for communication between the application
 * and the external "comment-service" microservice. It leverages Spring Cloud OpenFeign
 * for declarative REST API calls, simplifying the integration of microservices.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Declarative communication with the Comment Service via Feign Client.</li>
 *     <li>Endpoint for verifying the existence or validity of a specific comment.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To use this client, include it in a Spring-managed bean and call the provided
 * methods to interact with the Comment Service.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "comment-service", path = "/comments")
public interface CommentClient {

    /**
     * Verifies a specific comment by its unique identifier.
     * <p>
     * This method communicates with the Comment Service to check the validity
     * or existence of a comment. It sends a GET request to the "/comments/{commentId}/verify"
     * endpoint of the service.
     * </p>
     *
     * @param commentId the unique identifier of the comment to be verified
     * @return a {@link ResponseEntity} containing a {@code Boolean} value:
     *         {@code true} if the comment is valid, or {@code false} otherwise
     */
    @GetMapping("/{commentId}/verify")
    ResponseEntity<Boolean> commentVerification(@PathVariable("commentId") Long commentId) ;
}

package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Comment Service.
 * <p>
 * This client communicates with the {@code comment-service} microservice to retrieve information
 * about comments and their associated users. The base URL path for this service is {@code /comments}.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve the user ID associated with a specific comment.</li>
 *     <li>Uses Spring Cloud OpenFeign for declarative REST client functionality.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "comment-service", path = "/comments")
public interface CommentClient {

    /**
     * Retrieves the user ID associated with a specific comment by its ID.
     * <p>
     * This method performs a {@code GET} request to the {@code /{commentId}/user} endpoint of the
     * Comment Service. The response contains the user ID in a {@link ResponseEntity}.
     * </p>
     *
     * @param commentId the unique identifier of the comment
     * @return a {@link ResponseEntity} containing the user ID associated with the comment
     */
    @GetMapping("/{commentId}/user")
    ResponseEntity<Long> getCommentUserIdByCommentId(@PathVariable("commentId") Long commentId);
}

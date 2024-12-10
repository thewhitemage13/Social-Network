package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Comment Service.
 * <p>
 * This client provides methods to communicate with the "comment-service",
 * enabling seamless integration between microservices.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieves the count of comments for a specific post by its ID.</li>
 *     <li>Uses Feign for declarative HTTP client creation.</li>
 *     <li>Incorporates Spring Cloud's integration for easy communication with other services.</li>
 * </ul>
 *
 * <p>
 * This interface is marked with {@code @FeignClient} annotation, enabling
 * it to act as a proxy for remote calls to the "comment-service".
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "comment-service", path = "/comments")
public interface CommentClient {

    /**
     * Retrieves the count of comments associated with a specific post.
     * <p>
     * Sends a GET request to the "comment-service" to fetch the total number
     * of comments linked to the provided {@code postId}.
     * </p>
     *
     * @param postId the unique identifier of the post
     * @return a {@link ResponseEntity} containing the count of comments as a {@code Long}
     */
    @GetMapping("/posts/{postId}/count")
    ResponseEntity<Long> getCommentCountByPostId(@PathVariable("postId") Long postId);
}

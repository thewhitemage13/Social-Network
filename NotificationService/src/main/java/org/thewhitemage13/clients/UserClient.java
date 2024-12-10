package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the User Service.
 * <p>
 * This client communicates with the {@code user-service} microservice to perform operations
 * related to user management. The base URL path for this service is {@code /users}.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verify the existence of a user by their unique ID.</li>
 *     <li>Uses Spring Cloud OpenFeign for declarative REST client functionality.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Verifies the existence of a user by their unique identifier.
     * <p>
     * This method performs a {@code GET} request to the {@code /{userId}/verify} endpoint of the
     * User Service. The response indicates whether the user exists.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@link ResponseEntity} containing a {@code Boolean} value:
     *         {@code true} if the user exists, {@code false} otherwise
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);
}

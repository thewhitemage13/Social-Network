package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the User Service API.
 * <p>
 * This interface provides a method to verify the existence of a user in the system.
 * It leverages Spring Cloud OpenFeign to simplify REST communication with the User Service.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Connects to the User Service using Feign.</li>
 *     <li>Defines an endpoint for checking if a user exists by their unique ID.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This client can be used by other services or components within the system to confirm
 * whether a specific user is registered in the User Service.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Verifies the existence of a user by their unique identifier.
     * <p>
     * This method sends a GET request to the {@code /{userId}/verify} endpoint of the User Service.
     * The service responds with a {@link ResponseEntity} containing a Boolean value
     * indicating whether the user exists in the system.
     * </p>
     *
     * @param userId the unique identifier of the user to verify
     * @return a {@link ResponseEntity} containing {@code true} if the user exists, {@code false} otherwise
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);
}

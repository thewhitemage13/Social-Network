package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the User Service.
 * <p>
 * This interface defines methods for making HTTP requests to the User Service
 * to verify the existence of a user based on their ID. The client utilizes Feign
 * for declarative REST client functionality.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Integration with the User Service via Feign.</li>
 *     <li>Provides a method to verify if a user exists based on their unique identifier.</li>
 *     <li>Customizes the base path and name for the Feign client.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Verifies if a user exists in the User Service.
     *
     * <p>This method sends a GET request to the User Service to check the existence
     * of a user with the given {@code userId}. The User Service responds with a boolean
     * indicating the user's existence.</p>
     *
     * @param userId the unique identifier of the user to be verified.
     * @return a {@code ResponseEntity<Boolean>} containing {@code true} if the user exists,
     *         or {@code false} otherwise.
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);
}

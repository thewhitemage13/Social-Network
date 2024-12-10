package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with multiple user-related services.
 * <p>
 * This client provides methods to communicate with the "user-service",
 * enabling seamless integration between microservices for user verification
 * and username retrieval.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verifies the existence of a user by their ID.</li>
 *     <li>Retrieves the username associated with a specific user ID.</li>
 *     <li>Uses Feign for declarative HTTP client creation.</li>
 *     <li>Incorporates Spring Cloud's integration for easy communication with other services.</li>
 * </ul>
 *
 * <p>
 * This interface is marked with {@code @FeignClient} annotation, enabling
 * it to act as a proxy for remote calls to the "user-service".
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Verifies the existence of a user by their ID.
     * <p>
     * Sends a GET request to the "user-service" to check if the user
     * identified by {@code userId} exists.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@link ResponseEntity} containing {@code true} if the user exists, {@code false} otherwise
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);

    /**
     * Retrieves the username associated with a specific user ID.
     * <p>
     * Sends a GET request to the "user-service" to fetch the username of
     * the user identified by {@code userId}.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a {@link ResponseEntity} containing the username as a {@code String}
     */
    @GetMapping("/{userId}/username")
    ResponseEntity<String> getUserNameById(@PathVariable("userId") Long userId);
}

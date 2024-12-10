package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with the User Service.
 * <p>
 * This interface enables communication between the application and the external
 * "user-service" microservice. It utilizes Spring Cloud OpenFeign to simplify REST API
 * integrations and provides methods to verify user existence.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Declarative interaction with the User Service via Feign Client.</li>
 *     <li>Endpoint for checking if a user exists by their unique identifier.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This client should be injected as a Spring-managed bean and used to interact with
 * the User Service by calling the provided methods.
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
     * This method sends a GET request to the "/users/{userId}/verify" endpoint
     * of the User Service to determine if a user exists.
     * </p>
     *
     * @param userId the unique identifier of the user to verify
     * @return a {@link ResponseEntity} containing a {@code Boolean} value:
     *         {@code true} if the user exists, or {@code false} otherwise
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);
}

package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thewhitemage13.dto.UserSubscriptionDTO;

import java.util.List;

/**
 * Represents a client for interacting with the User Service via Feign.
 * <p>
 * This interface defines methods to communicate with the "user-service" microservice.
 * It allows verification of user existence and retrieval of user subscription details
 * based on user IDs.
 * <p>
 * The {@code @FeignClient} annotation enables Feign's dynamic proxy mechanism
 * to map HTTP endpoints to Java methods.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verification of user existence in the User Service.</li>
 *     <li>Fetching user subscription details for multiple users.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Verifies the existence of a user by their ID.
     * <p>
     * Sends a {@code GET} request to the {@code /users/{userId}/verify} endpoint
     * of the User Service to check if a user exists.
     * </p>
     *
     * @param userId the unique identifier of the user to verify
     * @return a {@link ResponseEntity} containing {@code true} if the user exists,
     *         or {@code false} otherwise
     */
    @GetMapping("/{userId}/verify")
    ResponseEntity<Boolean> verifyUserExistence(@PathVariable Long userId);

    /**
     * Retrieves user subscription details for a list of user IDs.
     * <p>
     * Sends a {@code GET} request to the User Service with a list of user IDs
     * to fetch their subscription information.
     * </p>
     *
     * @param ids a list of user IDs for which subscription details are requested
     * @return a list of {@link UserSubscriptionDTO} containing the subscription details
     *         of the specified users
     */
    @GetMapping
    List<UserSubscriptionDTO> getUsersByIds(@RequestParam List<Long> ids);
}

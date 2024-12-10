package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Media Service.
 * <p>
 * This client provides methods to communicate with the "media-service",
 * enabling seamless integration between microservices.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Verifies the validity of a media resource by its URL.</li>
 *     <li>Uses Feign for declarative HTTP client creation.</li>
 *     <li>Incorporates Spring Cloud's integration for easy communication with other services.</li>
 * </ul>
 *
 * <p>
 * This interface is marked with {@code @FeignClient} annotation, enabling
 * it to act as a proxy for remote calls to the "media-service".
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "media-service", path = "/media")
public interface MediaClient {

    /**
     * Verifies the validity of a media resource given its URL.
     * <p>
     * Sends a GET request to the "media-service" to check if the media
     * resource specified by the {@code url} is valid.
     * </p>
     *
     * @param url the URL of the media resource to be verified
     * @return a {@link ResponseEntity} containing {@code true} if the media is valid, {@code false} otherwise
     */
    @GetMapping("/verification")
    ResponseEntity<Boolean> mediaVerification(@RequestParam("url") String url);
}

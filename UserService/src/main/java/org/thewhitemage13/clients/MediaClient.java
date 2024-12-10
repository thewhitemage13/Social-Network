package org.thewhitemage13.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Represents a Feign client for interacting with the Media Service.
 * <p>
 * This interface defines communication with the "media-service" microservice,
 * facilitating operations related to media verification. It uses Feign, a declarative
 * web service client, to make HTTP requests seamlessly.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Integration with Spring Cloud OpenFeign for simplified HTTP client operations.</li>
 *     <li>Support for media URL verification via the Media Service API.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * MediaClient mediaClient = ...; // Injected instance
 * ResponseEntity<Boolean> response = mediaClient.mediaVerification("http://example.com/image.jpg");
 * boolean isValid = response.getBody();
 * }</pre>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "media-service", path = "/media")
public interface MediaClient {

    /**
     * Verifies the validity of a given media URL.
     * <p>
     * Sends a GET request to the Media Service to check whether the provided
     * URL points to a valid media resource.
     * </p>
     *
     * @param url the URL of the media resource to verify
     * @return a {@code ResponseEntity<Boolean>} indicating the verification result:
     *         {@code true} if the media is valid, {@code false} otherwise
     */
    @GetMapping("/verification")
    ResponseEntity<Boolean> mediaVerification(@RequestParam("url") String url);
}

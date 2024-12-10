package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main entry point for the User Service application.
 * <p>
 * This is the main class used to run the Spring Boot application. It includes annotations for enabling Spring Boot auto-configuration,
 * caching, and Feign clients for service communication.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link SpringBootApplication} - Enables Spring Boot auto-configuration, component scanning, and additional configuration.</li>
 *     <li>{@link EnableCaching} - Enables caching support for the application to improve performance.</li>
 *     <li>{@link EnableFeignClients} - Enables Feign clients to communicate with other services through HTTP.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To run the application, execute the `main` method. This will start the Spring Boot application and initialize the application context.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class UserServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments for the application
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

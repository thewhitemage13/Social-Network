package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point for the Notification Service Application.
 * <p>
 * This class is responsible for bootstrapping the Spring Boot application,
 * enabling caching, and setting up Feign clients for inter-service communication.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Bootstraps the Notification Service as a Spring Boot application.</li>
 *     <li>Enables caching to improve performance by reducing redundant database calls.</li>
 *     <li>Configures Feign clients for seamless integration with external services.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class NotificationServiceApplication {

	/**
	 * The main method that serves as the entry point for the Spring Boot application.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
}

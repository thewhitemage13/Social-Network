package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Entry point for the Subscription Service application.
 * <p>
 * This Spring Boot application provides functionalities for managing user subscriptions.
 * It enables the use of Feign clients for communication with external services.
 * </p>
 *
 * <h2>Main Features:</h2>
 * <ul>
 *     <li>Bootstraps the Subscription Service application.</li>
 *     <li>Enables Feign clients for external service integration.</li>
 * </ul>
 *
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.cloud.openfeign.EnableFeignClients
 * @version 1.0.0
 */
@SpringBootApplication
@EnableFeignClients
public class SubscriptionServiceApplication {

	/**
	 * Starts the Subscription Service application.
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
	}
}

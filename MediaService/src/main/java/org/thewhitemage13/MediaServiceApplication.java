package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The main entry point for the Media Service application.
 * <p>
 * This class serves as the bootstrap for the Media Service, initializing the Spring Boot application context
 * and enabling essential features such as caching and Feign clients.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Enables caching for the application using {@link EnableCaching}.</li>
 *     <li>Enables Feign clients for making HTTP requests to external services using {@link EnableFeignClients}.</li>
 *     <li>Bootstraps the application using the {@link SpringApplication#run} method.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class MediaServiceApplication {

	/**
	 * The main method that serves as the entry point for the Media Service application.
	 * <p>
	 * This method starts the Spring Boot application, triggering the auto-configuration
	 * and component scanning processes, and initializing the application context.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(MediaServiceApplication.class, args);
	}
}

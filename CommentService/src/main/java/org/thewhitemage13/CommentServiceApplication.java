package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The main entry point for the Comment Service application.
 * <p>
 * This class serves as the starting point for the Spring Boot application, enabling essential
 * configurations like caching and Feign clients.
 * </p>
 * <p>
 * The {@link EnableCaching} annotation enables caching support, while the {@link EnableFeignClients}
 * annotation enables the use of Feign clients for making HTTP requests to other services.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Enables caching for efficient data retrieval and performance optimization.</li>
 *     <li>Allows the use of Feign clients to interact with external services.</li>
 *     <li>Bootstraps the Spring Boot application by calling {@link SpringApplication#run}.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class CommentServiceApplication {

	/**
	 * The main method, which serves as the entry point to run the Comment Service application.
	 * <p>
	 * It triggers the {@link SpringApplication#run} method to start the application context and
	 * begin the execution of the Comment Service.
	 * </p>
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CommentServiceApplication.class, args);
	}

}

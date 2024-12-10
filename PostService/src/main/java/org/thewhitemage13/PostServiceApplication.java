package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The entry point of the Post Service application.
 * <p>
 * This class is responsible for launching the Spring Boot application. It includes necessary annotations for enabling
 * caching and Feign client functionality.
 * </p>
 *
 * <ul>
 *     <li>{@link SpringBootApplication} - Marks this class as a Spring Boot application, enabling auto-configuration, component scanning, and configuration properties.</li>
 *     <li>{@link EnableCaching} - Enables Spring's caching support, allowing the application to use caching mechanisms to improve performance.</li>
 *     <li>{@link EnableFeignClients} - Enables the use of Feign clients for making HTTP requests to other microservices or external systems.</li>
 * </ul>
 *
 * <p>
 * The main method runs the Spring Boot application using {@link SpringApplication#run(Class, String...)}.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class PostServiceApplication {

	/**
	 * The main method which serves as the entry point for the Post Service application.
	 * <p>
	 * This method initializes and runs the Spring Boot application.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application on startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}

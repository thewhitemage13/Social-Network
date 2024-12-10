package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The main entry point for the LikeService application.
 * <p>
 * This class is responsible for launching the Spring Boot application and initializing
 * the necessary configurations. It is annotated with {@link SpringBootApplication},
 * which includes essential annotations to set up a Spring Boot application with
 * default configurations.
 * </p>
 *
 * <p>
 * This application also enables caching via {@link EnableCaching} and support for
 * Feign clients with {@link EnableFeignClients}, allowing it to interact with external
 * services for various functionalities such as post, comment, and user validation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Launches the Spring Boot application.</li>
 *     <li>Enables caching for optimized data retrieval.</li>
 *     <li>Enables Feign clients for simplified HTTP communication with external services.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class LikeServiceApplication {

	/**
	 * The main method that serves as the entry point for the Spring Boot application.
	 * <p>
	 * This method runs the application by invoking {@link SpringApplication#run}.
	 * It sets up the Spring context and launches the web server.
	 * </p>
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(LikeServiceApplication.class, args);
	}

}

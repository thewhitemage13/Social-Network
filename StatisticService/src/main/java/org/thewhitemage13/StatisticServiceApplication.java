package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for starting the Statistic Service application.
 * <p>
 * This class serves as the entry point for the Spring Boot application, initializing the application context
 * and running the application.
 * </p>
 *
 * <h2>Usage:</h2>
 * To start the application, simply run the `main` method, which will bootstrap the Spring Boot application.
 *
 * <p>
 * The {@link SpringBootApplication} annotation indicates that this class is the primary configuration for
 * Spring Boot, enabling auto-configuration, component scanning, and configuration properties loading.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
public class StatisticServiceApplication {

	/**
	 * The main method to run the Spring Boot application.
	 * <p>
	 * This method bootstraps the application by calling {@link SpringApplication#run(Class, String[])} to
	 * start the embedded server and initialize the Spring context.
	 * </p>
	 *
	 * @param args command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(StatisticServiceApplication.class, args);
	}
}

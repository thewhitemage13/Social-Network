package org.thewhitemage13.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Swagger API documentation.
 * <p>
 * This class configures the OpenAPI documentation for the application using Swagger.
 * It provides metadata about the API, such as title, description, version, and contact information.
 * The Swagger UI will use this configuration to display the API documentation for developers.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>API title, description, version, and contact information configuration.</li>
 *     <li>Configuration of the server URL for API access during local development.</li>
 *     <li>Provides a clear and concise interface for API users to understand available endpoints.</li>
 * </ul>
 *
 * @see OpenAPI
 * @see Info
 * @see Server
 * @see Contact
 * @see Bean
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures and returns the OpenAPI bean for generating the Swagger documentation.
     * <p>
     * The method sets up the API metadata, such as:
     * <ul>
     *     <li>API title: "Banking API"</li>
     *     <li>Description: A detailed overview of the banking operations provided by the SpringCoreBankApp.</li>
     *     <li>Version: "1.0.0"</li>
     *     <li>Contact: Support team email</li>
     * </ul>
     * Additionally, it configures the server URL for local development (localhost:7074).
     *
     * @return a configured {@link OpenAPI} instance with the API metadata and server details
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server()
                                        .url("http://localhost:7074")
                                        .description("Local development server")
                        )
                )
                .info(
                        new Info()
                                .title("Statistic API")
                                .description("""
                                        API documentation for the Statistic Service. 
                                        This service provides operations for managing and analyzing:
                                        - User statistics
                                        - Post statistics
                                        - Media statistics
                                        - Like statistics
                                        - Comment statistics
                                        """)
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Support Team")
                                                .email("lmecomcompany@gmail.com")
                                )
                );
    }
}

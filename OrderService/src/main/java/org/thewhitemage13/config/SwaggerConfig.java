package org.thewhitemage13.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for Swagger API documentation.
 * <p>
 * This class sets up the OpenAPI documentation using Swagger for the User API.
 * It provides metadata about the API, such as the title, description, version, and contact information.
 * Developers can use the generated Swagger UI to explore and interact with the API endpoints.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>API metadata configuration, including title, description, and version.</li>
 *     <li>Contact information for support inquiries.</li>
 *     <li>Server configuration for local development access.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Add this class to the Spring context to enable automatic generation of Swagger documentation.
 * The Swagger UI will be available at the configured endpoint (e.g., `/swagger-ui.html` or `/api-docs`).
 * </p>
 *
 * @see OpenAPI
 * @see io.swagger.v3.oas.models.info.Info
 * @see io.swagger.v3.oas.models.servers.Server
 * @see io.swagger.v3.oas.models.info.Contact
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures and returns the OpenAPI bean for Swagger documentation.
     * <p>
     * This method sets up the following API metadata:
     * <ul>
     *     <li>API title: "User API".</li>
     *     <li>Description: "This API manages user operations, including creating, updating, retrieving, and deleting users."</li>
     *     <li>Version: "1.0.0".</li>
     *     <li>Contact information: Support Team email.</li>
     * </ul>
     * Additionally, it configures a server URL for local development purposes.
     *
     * @return a configured {@link OpenAPI} instance containing API metadata and server details
     */
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server()
                                        .url("http://localhost:8087")
                                        .description("Local development server"),
                                new Server()
                                        .description("Production server")
                        )
                )
                .info(
                        new Info()
                                .title("User API")
                                .description("""
                                        This API allows for comprehensive user management operations:
                                        <ul>
                                            <li>Create users</li>
                                            <li>Update user information</li>
                                            <li>Retrieve user details</li>
                                            <li>Delete users</li>
                                        </ul>
                                        """)
                                .version("1.1.0")
                                .contact(
                                        new Contact()
                                                .name("Support Team")
                                                .email("lmecomcompany@gamil.com")
                                                .url("https://thewhitemage13.com/contact")
                                )
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Full API Documentation")
                );
    }
}

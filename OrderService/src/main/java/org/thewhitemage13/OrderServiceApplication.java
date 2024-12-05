package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the Order Service.
 * <p>
 * This is the entry point for the Spring Boot application. It enables caching
 * and Feign clients for interaction with external services and starts the application context.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Enables caching support via {@code @EnableCaching}.</li>
 *     <li>Enables Feign clients for communication with external APIs via {@code @EnableFeignClients}.</li>
 *     <li>Initializes and runs the application context.</li>
 * </ul>
 *
 * @see SpringApplication
 * @see EnableCaching
 * @see EnableFeignClients
 * @see SpringBootApplication
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@EnableCaching
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

    /**
     * Main method to launch the application.
     * <p>
     * This method initializes the Spring application context and starts the application.
     * </p>
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

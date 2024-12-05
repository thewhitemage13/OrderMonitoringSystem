package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * The main entry point for the Inventory Management Service application.
 * <p>
 * This is a Spring Boot application class that sets up the application context
 * and launches the application. It also enables caching functionality across the
 * application to improve performance by caching the results of method calls.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Launches the application using Spring Boot's {@link SpringApplication}</li>
 *     <li>Enables caching across the application using {@link EnableCaching}</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To run this application, you can execute the main method. This will start the Spring Boot application
 * and initialize all the configured beans, allowing the application to handle incoming requests and manage
 * product inventory.
 * </p>
 *
 * <h2>Caching:</h2>
 * <p>
 * Caching is enabled globally through the {@link EnableCaching} annotation, which optimizes the performance
 * of certain service methods by storing results in a cache.
 * </p>
 *
 * @see SpringApplication
 * @see EnableCaching
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@EnableCaching
@SpringBootApplication
public class InventoryManagementServiceApplication {

	/**
	 * The main method that serves as the entry point for the application.
	 * <p>
	 * This method runs the Spring Boot application by invoking {@link SpringApplication#run} with the current
	 * class and command-line arguments.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementServiceApplication.class, args);
	}

}

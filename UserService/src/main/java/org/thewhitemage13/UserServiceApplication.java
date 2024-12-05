package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main class to start the UserService Spring Boot application.
 * <p>
 * This is the entry point for the Spring Boot application. The application starts by running this class.
 * The {@link SpringBootApplication} annotation is used to mark this class as the configuration class
 * that will be automatically used by Spring to set up the application context.
 * </p>
 * <p>
 * The {@link EnableCaching} annotation enables caching support, allowing the application to cache data
 * to improve performance and reduce the load on the underlying data sources.
 * </p>
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Starts the Spring Boot application.</li>
 *     <li>Enables caching to improve performance.</li>
 * </ul>
 *
 * <h3>Usage:</h3>
 * <p>
 * When you run the application, this class is executed, initializing the Spring Boot context and enabling
 * the necessary services.
 * </p>
 *
 * <h3>Annotations:</h3>
 * <ul>
 *     <li>{@link SpringBootApplication} - Marks the class as the main entry point for Spring Boot.</li>
 *     <li>{@link EnableCaching} - Activates Spring's caching support.</li>
 * </ul>
 *
 * @see SpringApplication
 * @see EnableCaching
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @version 1.0.0
 * @author Mukhammed Lolo
 */
@EnableCaching
@SpringBootApplication
public class UserServiceApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * <p>
	 * This method triggers the startup of the Spring Boot application by calling
	 * {@link SpringApplication#run(Class, String...)}.
	 * </p>
	 *
	 * @param args the command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}

package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point for the Statistics Service application.
 * <p>
 * This class is the starting point of the Spring Boot application. It enables caching
 * functionality with the {@link EnableCaching} annotation and launches the application
 * with the {@link SpringApplication} class.
 * </p>
 *
 * @see EnableCaching
 * @see SpringApplication
 * @see StatisticsServiceApplication
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@EnableCaching
@SpringBootApplication
public class StatisticsServiceApplication {

	/**
	 * The main method which serves as the entry point for the Statistics Service application.
	 * <p>
	 * It runs the Spring Boot application, initializing all components and configurations
	 * defined in the application context.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(StatisticsServiceApplication.class, args);
	}

}

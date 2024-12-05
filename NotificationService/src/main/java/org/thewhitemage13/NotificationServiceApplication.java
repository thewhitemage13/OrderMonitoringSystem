package org.thewhitemage13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Notification Service application.
 * <p>
 * This class contains the main method that launches the Spring Boot application. It is annotated with
 * {@link SpringBootApplication}, which includes several annotations to configure the application context,
 * including component scanning, auto-configuration, and property support.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Bootstrap the Spring Boot application.</li>
 *     <li>Run the application with {@link SpringApplication#run(Class, String...)}</li>
 * </ul>
 *
 * @see SpringApplication
 * @see SpringBootApplication
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@SpringBootApplication
public class NotificationServiceApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * <p>
	 * This method initializes and runs the {@link SpringBootApplication} by calling {@link SpringApplication#run(Class, String...)}.
	 * </p>
	 *
	 * @param args command line arguments passed during the application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
}

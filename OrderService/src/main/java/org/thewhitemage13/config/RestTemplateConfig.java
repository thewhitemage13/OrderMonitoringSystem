package org.thewhitemage13.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating a {@link RestTemplate} bean.
 * <p>
 * This class configures a {@link RestTemplate} bean that is automatically
 * load-balanced using Spring Cloud's {@link LoadBalanced} annotation.
 * The load balancing feature ensures that the {@link RestTemplate} will
 * be able to interact with multiple service instances, enabling efficient
 * and fault-tolerant HTTP communication in a microservices architecture.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates a {@link RestTemplate} bean for making HTTP requests.</li>
 *     <li>Enables client-side load balancing using {@link LoadBalanced}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This configuration allows the application to make HTTP requests to
 * multiple instances of a service, with load balancing automatically
 * handled by Spring Cloud. The {@link RestTemplate} can be injected
 * wherever HTTP communication is needed in the application.
 * </p>
 *
 * @see RestTemplate
 * @see LoadBalanced
 * @see org.springframework.web.client.RestTemplate
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a {@link RestTemplate} bean with client-side load balancing enabled.
     * <p>
     * This method configures a {@link RestTemplate} that will automatically
     * perform load balancing using Spring Cloud's {@link LoadBalanced}
     * annotation. This allows the application to make HTTP requests to
     * multiple instances of services, balancing the load among them.
     * </p>
     *
     * @return a {@link RestTemplate} bean with load balancing capabilities
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

package org.thewhitemage13.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for interacting with the User Service.
 * <p>
 * This client provides a method to check the existence or validity of a user in the system
 * based on their unique identifier. It simplifies communication with the User Service
 * using Feign, allowing seamless integration of external APIs.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Check if a user exists in the User Service.</li>
 *     <li>Ease of integration with external user management APIs using Feign.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To use this client, include it in your Spring application, ensure that Feign is properly configured,
 * and call the provided method to verify user information.
 * </p>
 *
 * @see FeignClient
 * @see ResponseEntity
 * @see org.springframework.web.bind.annotation.GetMapping
 * @see org.springframework.web.bind.annotation.PathVariable
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@FeignClient(name = "user-service", path = "/users")
public interface UserClient {

    /**
     * Checks if a user exists in the system by their unique identifier.
     *
     * @param userId the unique identifier of the user
     * @return a {@link ResponseEntity} containing a {@code Boolean} value:
     *         {@code true} if the user exists, {@code false} otherwise
     */
    @GetMapping("/check/{userId}")
    ResponseEntity<Boolean> checkUser(@PathVariable("userId") Long userId);
}

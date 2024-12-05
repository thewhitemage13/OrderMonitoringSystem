package org.thewhitemage13.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * Feign client interface for interacting with the Inventory Management Service.
 * <p>
 * This client provides methods to check product availability, retrieve product prices,
 * and fetch product names based on their unique identifiers. It uses Feign to simplify
 * communication with the external service.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Check if a specified product is available in the required quantity.</li>
 *     <li>Retrieve the price of a product based on its ID.</li>
 *     <li>Get the name of a product using its ID.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * To use this client, include it in your Spring application and ensure that Feign is properly configured.
 * The methods in this interface correspond to the endpoints exposed by the Inventory Management Service.
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
@FeignClient(name = "inventory-management-service", path = "/products")
public interface InventoryClient {

    /**
     * Checks if the specified product is available in the desired quantity.
     *
     * @param productId   the unique identifier of the product
     * @param countOfItems the quantity of the product to check
     * @return a {@link ResponseEntity} containing a {@code Boolean} value indicating availability
     */
    @GetMapping("/{productId}/availability/{countOfItems}")
    ResponseEntity<Boolean> checkProductAvailability(@PathVariable("productId") Long productId, @PathVariable("countOfItems")Long countOfItems);

    /**
     * Retrieves the price of a product by its ID.
     *
     * @param productId the unique identifier of the product
     * @return a {@link ResponseEntity} containing the product's price as a {@link BigDecimal}
     */
    @GetMapping("/{productId}/price")
    ResponseEntity<BigDecimal> getPrice(@PathVariable("productId") Long productId);

    /**
     * Retrieves the name of a product by its ID.
     *
     * @param productId the unique identifier of the product
     * @return a {@link ResponseEntity} containing the product's name as a {@link String}
     */
    @GetMapping("/{productId}/name")
    ResponseEntity<String> getProductNameById(@PathVariable("productId") Long productId);
}

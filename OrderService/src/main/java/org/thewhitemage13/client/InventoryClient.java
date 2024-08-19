package org.thewhitemage13.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "inventory-management-service", path = "/products")
public interface InventoryClient {
    @GetMapping("/check/{productId}/{countOfItems}")
    ResponseEntity<Boolean> checkProductAvailability(@PathVariable("productId") Long productId, @PathVariable("countOfItems")Long countOfItems);

    @GetMapping("/get-price/{productId}")
    ResponseEntity<BigDecimal> getPrice(@PathVariable("productId") Long productId);

    @GetMapping("/get-product-name/{productId}")
    ResponseEntity<String> getProductNameById(@PathVariable("productId") Long productId);
}

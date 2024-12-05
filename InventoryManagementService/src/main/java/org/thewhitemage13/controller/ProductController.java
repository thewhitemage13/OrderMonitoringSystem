package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Product Controller", description = "Operations related to product management")
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get product name by ID",
            description = "Retrieves the name of the product specified by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product name retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{productId}/name")
    public ResponseEntity<String> getProductNameById(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable("productId") Long productId) {
        try {
            return ResponseEntity.ok(productService.getProductNameById(productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found"
                    .formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(
            summary = "Get product price by ID",
            description = "Retrieves the price of the product specified by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product price retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{productId}/price")
    public ResponseEntity<BigDecimal> getPrice(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable("productId") Long productId) {
        try {
            BigDecimal price = productService.getPriceByProductId(productId);
            return ResponseEntity.ok(price);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BigDecimal.valueOf(-1));
        }
    }

    @Operation(
            summary = "Check product availability",
            description = "Checks if a specified number of items for a product is available in inventory.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availability check completed successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{productId}/availability/{countOfItems}")
    public ResponseEntity<Boolean> checkProductAvailability
            (
                    @Parameter(description = "Unique ID of the product", required = true)
                    @PathVariable("productId") Long productId,
                    @Parameter(description = "Number of items to check availability for", required = true)
                    @PathVariable("countOfItems")Long countOfItems) {
        try {
            boolean available = productService.checkProductInventory(productId, countOfItems);
            return ResponseEntity.ok(available);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @Operation(
            summary = "Create a new product",
            description = "Adds a new product to the inventory.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PostMapping
    public ResponseEntity<String> createProduct(
            @Parameter(description = "Product entity to create", required = true)
            @Valid @RequestBody Product product) {
        try{
            productService.addProduct(product);
            return ResponseEntity.ok("Product created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Delete a product by ID",
            description = "Removes a product from the inventory by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found"
                    .formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(
            summary = "Get product details by ID",
            description = "Retrieves detailed information about a product specified by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<String> getProduct(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable("productId") Long productId) {
        try {
            return ResponseEntity.ok(productService.getProduct(productId).toString());
        }catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found"
                    .formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products in the inventory.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(
            summary = "Update product quantity",
            description = "Updates the quantity of a product in the inventory specified by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product quantity updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PutMapping("/{productId}/quantity")
    public ResponseEntity<String> updateQuantity(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable Long productId,
            @Parameter(description = "New quantity to set", required = true)
            @RequestParam Long quantity) {
        try {
            productService.updateQuantity(productId, quantity);
            return ResponseEntity.ok("Product updated");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found"
                    .formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(
            summary = "Update product details",
            description = "Updates the details of a product specified by its unique ID.",
            tags = {"Product Controller"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product details updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(
            @Parameter(description = "Unique ID of the product", required = true)
            @PathVariable("productId") Long productId,
            @Parameter(description = "Updated product entity", required = true)
            @RequestBody Product product) {
        try {
            productService.updateProduct(productId, product);
            return ResponseEntity.ok("Product updated");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found".formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

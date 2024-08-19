package org.thewhitemage13.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get-product-name/{productId}")
    public ResponseEntity<String> getProductNameById(@PathVariable("productId") Long productId) {
        try {
            return ResponseEntity.ok(productService.getProductNameById(productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found".formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/get-price/{productId}")
    public ResponseEntity<BigDecimal> getPrice(@PathVariable("productId") Long productId) {
        try {
            BigDecimal price = productService.getPriceByProductId(productId);
            return ResponseEntity.ok(price);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BigDecimal.valueOf(-1));
        }
    }

    @GetMapping("/check/{productId}/{countOfItems}")
    public ResponseEntity<Boolean> checkProductAvailability(@PathVariable("productId") Long productId, @PathVariable("countOfItems")Long countOfItems) {
        try {
            boolean available = productService.checkProductInventory(productId, countOfItems);
            return ResponseEntity.ok(available);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try{
            productService.addProduct(product);
            return ResponseEntity.ok("Product created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found".formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/get-by-id/{productId}")
    public ResponseEntity<String> getProduct(@PathVariable("productId") Long productId) {
        try {
            return ResponseEntity.ok(productService.getProduct(productId).toString());
        }catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found".formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/get-all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/update-quantity/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable("quantity") Long productId, @RequestParam Long quantity) {
        try {
            productService.updateQuantity(productId, quantity);
            return ResponseEntity.ok("Product updated");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id = %s not found".formatted(productId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) {
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

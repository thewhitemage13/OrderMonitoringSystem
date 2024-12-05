package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Test
    void testGetProductNameById_Success() {
        Long productId = 1L;
        String productName = "Test Product";
        when(productService.getProductNameById(productId)).thenReturn(productName);

        ResponseEntity<String> response = productController.getProductNameById(productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productName, response.getBody());
        verify(productService, times(1)).getProductNameById(productId);
    }

    @Test
    void testGetProductNameById_NotFound() {
        Long productId = 1L;
        when(productService.getProductNameById(productId)).thenThrow(new ProductNotFoundException());

        ResponseEntity<String> response = productController.getProductNameById(productId);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("not found"));
        verify(productService, times(1)).getProductNameById(productId);
    }

    @Test
    void testGetPrice_Success() {
        Long productId = 1L;
        BigDecimal price = BigDecimal.valueOf(19.99);
        when(productService.getPriceByProductId(productId)).thenReturn(price);

        ResponseEntity<BigDecimal> response = productController.getPrice(productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(price, response.getBody());
        verify(productService, times(1)).getPriceByProductId(productId);
    }

    @Test
    void testGetPrice_NotFound() {
        Long productId = 1L;
        when(productService.getPriceByProductId(productId)).thenThrow(new ProductNotFoundException());

        ResponseEntity<BigDecimal> response = productController.getPrice(productId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).getPriceByProductId(productId);
    }

    @Test
    void testCreateProduct_Success() {
        Product product = new Product(1L, "Test Product", 100L, BigDecimal.valueOf(19.99));
        doNothing().when(productService).addProduct(product);

        ResponseEntity<String> response = productController.createProduct(product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product created", response.getBody());
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testDeleteProduct_Success() {
        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<String> response = productController.deleteProduct(productId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product deleted", response.getBody());
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testDeleteProduct_NotFound() {
        Long productId = 1L;
        doThrow(new ProductNotFoundException()).when(productService).deleteProduct(productId);

        ResponseEntity<String> response = productController.deleteProduct(productId);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("not found"));
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", 100L, BigDecimal.valueOf(10.0)),
                new Product(2L, "Product2", 200L, BigDecimal.valueOf(20.0))
        );
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(products, result);
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testUpdateQuantity_Success() {
        Long productId = 1L;
        Long quantity = 50L;
        doNothing().when(productService).updateQuantity(productId, quantity);

        ResponseEntity<String> response = productController.updateQuantity(productId, quantity);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product updated", response.getBody());
        verify(productService, times(1)).updateQuantity(productId, quantity);
    }

    @Test
    void testUpdateProduct_Success() {
        Long productId = 1L;
        Product updatedProduct = new Product(productId, "Updated Product", 150L, BigDecimal.valueOf(15.0));
        doNothing().when(productService).updateProduct(productId, updatedProduct);

        ResponseEntity<String> response = productController.updateProduct(productId, updatedProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product updated", response.getBody());
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }
}
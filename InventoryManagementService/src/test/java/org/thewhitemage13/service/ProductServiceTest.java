package org.thewhitemage13.service;

import com.google.common.util.concurrent.ListenableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.processor.ProductProcessor;
import org.thewhitemage13.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate;
    @Mock
    private ProductProcessor productProcessor;
    @InjectMocks
    private ProductService productService;

    @Test
    void testGetProductNameById_Success() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 100L, BigDecimal.valueOf(19.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        String productName = productService.getProductNameById(productId);

        assertEquals("Test Product", productName);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductNameById_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductNameById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetPriceByProductId_Success() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 100L, BigDecimal.valueOf(19.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        BigDecimal price = productService.getPriceByProductId(productId);

        assertEquals(BigDecimal.valueOf(19.99), price);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCheckProductInventory_Success() throws ProductNotFoundException {
        Long productId = 1L;
        Long countOfItems = 50L;
        Product product = new Product(productId, "Test Product", 100L, BigDecimal.valueOf(19.99));
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        boolean isAvailable = productService.checkProductInventory(productId, countOfItems);

        assertTrue(isAvailable);
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testDeleteProduct_Success() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product(productId, "Test Product", 100L, BigDecimal.valueOf(19.99));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", 100L, BigDecimal.valueOf(10.0)),
                new Product(2L, "Product2", 200L, BigDecimal.valueOf(20.0))
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(products, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct_Success() throws ProductNotFoundException {
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Old Product", 50L, BigDecimal.valueOf(10.0));
        Product updatedProduct = new Product(productId, "Updated Product", 100L, BigDecimal.valueOf(15.0));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, updatedProduct);

        assertEquals("Updated Product", existingProduct.getName());
        assertEquals(BigDecimal.valueOf(15.0), existingProduct.getPrice());
        assertEquals(100L, existingProduct.getQuantity());
        verify(productRepository, times(1)).save(existingProduct);
    }
}
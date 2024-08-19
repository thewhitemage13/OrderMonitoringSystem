package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductServiceInterface {
    String getProductNameById(Long productId) throws ProductNotFoundException;
    BigDecimal getPriceByProductId(Long productId) throws ProductNotFoundException;
    boolean checkProductInventory(Long productId, Long countOfItems) throws ProductNotFoundException;
    void addProduct(Product product);
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product getProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
    void updateQuantity(Long productId, Long quantity) throws ProductNotFoundException;
    void updateProduct(Long productId, Product product) throws ProductNotFoundException;
    Product getProductByName(String productName);
}

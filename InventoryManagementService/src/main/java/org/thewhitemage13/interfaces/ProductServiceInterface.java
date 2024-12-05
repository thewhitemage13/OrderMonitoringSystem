package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface for managing product-related operations.
 * <p>
 * This interface defines the contract for interacting with product data, such as retrieving product details,
 * checking inventory, updating quantities, and adding or deleting products.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve product details by product ID or name.</li>
 *     <li>Check and manage product inventory.</li>
 *     <li>Add, update, and delete products from the inventory.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implementing classes should provide the actual business logic for managing products.
 * The methods in this interface are intended to handle all core operations for managing products in the system.
 * </p>
 *
 * @see Product
 * @see ProductNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ProductServiceInterface {

    /**
     * Retrieves the name of a product by its ID.
     *
     * @param productId the ID of the product
     * @return the name of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    String getProductNameById(Long productId) throws ProductNotFoundException;

    /**
     * Retrieves the price of a product by its ID.
     *
     * @param productId the ID of the product
     * @return the price of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    BigDecimal getPriceByProductId(Long productId) throws ProductNotFoundException;

    /**
     * Checks if there is enough inventory for the product to fulfill the requested quantity.
     *
     * @param productId the ID of the product
     * @param countOfItems the number of items to check against the available inventory
     * @return {@code true} if there is enough inventory, otherwise {@code false}
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    boolean checkProductInventory(Long productId, Long countOfItems) throws ProductNotFoundException;

    /**
     * Adds a new product to the inventory.
     *
     * @param product the product to be added
     */
    void addProduct(Product product);

    /**
     * Deletes a product from the inventory by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    void deleteProduct(Long productId) throws ProductNotFoundException;

    /**
     * Retrieves a product by its ID.
     *
     * @param productId the ID of the product
     * @return the product object
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    Product getProduct(Long productId) throws ProductNotFoundException;

    /**
     * Retrieves all products from the inventory.
     *
     * @return a list of all products
     */
    List<Product> getAllProducts();

    /**
     * Updates the quantity of a specific product in the inventory.
     *
     * @param productId the ID of the product to update
     * @param quantity the new quantity of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    void updateQuantity(Long productId, Long quantity) throws ProductNotFoundException;

    /**
     * Updates the details of a product in the inventory.
     *
     * @param productId the ID of the product to update
     * @param product the updated product object
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    void updateProduct(Long productId, Product product) throws ProductNotFoundException;

    /**
     * Retrieves a product by its name.
     *
     * @param productName the name of the product
     * @return the product object
     */
    Product getProductByName(String productName);
}

package org.thewhitemage13.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.interfaces.ProductServiceInterface;
import org.thewhitemage13.processor.ProductProcessor;
import org.thewhitemage13.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for managing {@link Product} entities.
 * <p>
 * This service class provides methods for performing various operations on products,
 * such as retrieving product information, adding, updating, and deleting products,
 * as well as checking product inventory. It also integrates with Kafka to send events
 * related to product changes, such as when products are added, updated, or have low stock.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>CRUD operations for products (create, read, update, delete).</li>
 *     <li>Cache management to improve performance (using {@link Cacheable} and {@link CacheEvict}).</li>
 *     <li>Integration with Kafka for publishing product-related events.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This service can be used to manage product data, including adding new products, updating product quantities,
 * deleting products, and retrieving product details either by ID or by name. Kafka events are sent when products
 * are added or updated, and stock-related events (e.g., low stock) are also triggered.
 * </p>
 *
 * @see Product
 * @see ProductRepository
 * @see ProductCreateEvent
 * @see KafkaTemplate
 * @see ProductProcessor
 * @see ProductNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class ProductService implements ProductServiceInterface {
    private final ProductRepository productRepository;
    private final KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate;
    private final ProductProcessor productProcessor;

    /**
     * Constructs a new ProductService.
     *
     * @param productRepository the repository for managing product data
     * @param kafkaTemplate the Kafka template for sending product-related events
     * @param productProcessor the processor for creating product events
     */
    public ProductService(ProductRepository productRepository, KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate, ProductProcessor productProcessor) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.productProcessor = productProcessor;
    }

    /**
     * Retrieves the name of a product by its ID.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @param productId the ID of the product to retrieve
     * @return the name of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @Cacheable(cacheNames = "productById", key = "#productId")
    @Override
    public String getProductNameById(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
        return product.getName();
    }

    /**
     * Retrieves the price of a product by its ID.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @param productId the ID of the product to retrieve
     * @return the price of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @Cacheable(cacheNames = "productPriceById", key = "#productId")
    @Override
    public BigDecimal getPriceByProductId(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
        return product.getPrice();
    }

    /**
     * Checks if there is enough inventory for the specified product.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @param productId the ID of the product to check
     * @param countOfItems the number of items to check against the available inventory
     * @return {@code true} if there is enough inventory, {@code false} otherwise
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @Cacheable(cacheNames = "productInventory", key = "#productId")
    @Override
    public boolean checkProductInventory(Long productId, Long countOfItems) throws ProductNotFoundException {
        boolean check = productRepository.existsById(productId);
        if(check) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
            return product.getQuantity() > countOfItems;
        }else
            return false;
    }

    /**
     * Adds a new product to the system.
     * <p>
     * This operation evicts the cache for all products to ensure the cache is updated with the latest data.
     * A product create event is also published to Kafka.
     * </p>
     *
     * @param product the product to add
     */
    @CacheEvict(cacheNames = "allProducts", allEntries = true)
    @Override
    public void addProduct(Product product) {

        productRepository.save(product);

        System.out.println(product.getId());

        ProductCreateEvent productCreateEvent = productProcessor.getProductCreateEvent(product);

        kafkaTemplate.send("add.product", product.getId(), productCreateEvent);

    }

    /**
     * Deletes a product by its ID.
     * <p>
     * This operation evicts the cache for the deleted product.
     * </p>
     *
     * @param productId the ID of the product to delete
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @CacheEvict(cacheNames = "productById", key = "#productId")
    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product deleteProduct = getProduct(productId);
        productRepository.delete(deleteProduct);
    }

    /**
     * Retrieves a product by its ID.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @param productId the ID of the product to retrieve
     * @return the product with the given ID
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @Cacheable(cacheNames = "productById", key = "#productId")
    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
    }

    /**
     * Retrieves all products in the system.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @return a list of all products
     */
    @Cacheable(cacheNames = "allProducts")
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Updates the quantity of a product.
     * <p>
     * This operation evicts the cache for the updated product and publishes an update event to Kafka.
     * </p>
     *
     * @param productId the ID of the product to update
     * @param quantity the new quantity of the product
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @CacheEvict(cacheNames = "productById", key = "#productId")
    @Override
    public void updateQuantity(Long productId, Long quantity) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.setQuantity(quantity);

        productRepository.save(product);

        ProductCreateEvent productCreateEvent = productProcessor.getProductCreateEvent(product);


        kafkaTemplate.send("update.stock", product.getId(), productCreateEvent);
    }

    /**
     * Updates a product's details (name, quantity, price).
     * <p>
     * This operation evicts the cache for the updated product.
     * </p>
     *
     * @param productId the ID of the product to update
     * @param product the product with the updated details
     * @throws ProductNotFoundException if the product with the given ID is not found
     */
    @CacheEvict(cacheNames = "productById", key = "#productId")
    @Override
    public void updateProduct(Long productId, Product product) throws ProductNotFoundException {
        Product updateProduct = getProduct(productId);

        updateProduct.setName(product.getName());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setPrice(product.getPrice());

        productRepository.save(updateProduct);
    }

    /**
     * Retrieves a product by its name.
     * <p>
     * The result is cached for better performance.
     * </p>
     *
     * @param productName the name of the product to retrieve
     * @return the product with the given name
     */
    @Cacheable(cacheNames = "productByName", key = "#productName")
    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByName(productName);
    }
}

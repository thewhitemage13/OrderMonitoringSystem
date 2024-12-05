package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Product;

/**
 * Repository interface for managing {@link Product} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for the {@link Product} entity.
 * It includes a custom method for finding a product by its name.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides basic CRUD operations (create, read, update, delete) for {@link Product} entities.</li>
 *     <li>Includes a custom method to find a {@link Product} by its name.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This repository can be used to interact with the database to perform operations on the {@link Product} table.
 * For example, you can save a new product, retrieve an existing product by its ID, or find a product by its name.
 * </p>
 *
 * @see Product
 * @see JpaRepository
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by its name.
     * <p>
     * This method searches for a {@link Product} in the database by its name.
     * If no product is found with the given name, it returns {@code null}.
     * </p>
     *
     * @param name the name of the product to search for
     * @return the {@link Product} with the given name, or {@code null} if no product is found
     */
    Product findByName(String name);
}

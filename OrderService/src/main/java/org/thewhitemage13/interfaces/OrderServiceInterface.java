package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateOrderDTO;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.exception.OrderNotFoundException;

import java.util.List;

/**
 * Interface for managing and processing orders.
 * <p>
 * This interface defines methods for creating, updating, and retrieving orders. It includes functionality
 * for creating new orders, updating the status of existing orders, and retrieving orders by their ID or in bulk.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creating new orders with the provided {@link CreateOrderDTO}.</li>
 *     <li>Updating the status of an existing order.</li>
 *     <li>Retrieving all orders in the system.</li>
 *     <li>Retrieving an individual order by its ID.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This interface is typically implemented by a service responsible for handling order business logic.
 * It may be used in order processing systems, e-commerce platforms, or any application managing orders.
 * </p>
 *
 * @see CreateOrderDTO
 * @see ShowOrderDTO
 * @see Order
 * @see OrderNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderServiceInterface {

    /**
     * Creates a new order based on the provided order data.
     *
     * @param createOrderDTO the data transfer object containing the information for the new order
     */
    void createOrder(CreateOrderDTO createOrderDTO);

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the ID of the order to update
     * @param status the new status to assign to the order
     * @throws OrderNotFoundException if the order with the specified ID is not found
     */
    void updateOrderStatus(Long orderId, String status) throws OrderNotFoundException;

    /**
     * Retrieves all orders in the system.
     *
     * @return a list of all {@link Order} entities
     */
    List<Order> showAllOrders();

    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId the ID of the order to retrieve
     * @return a {@link ShowOrderDTO} containing the details of the requested order
     * @throws OrderNotFoundException if no order with the specified ID is found
     */
    ShowOrderDTO showOrderById(Long orderId) throws OrderNotFoundException;
}

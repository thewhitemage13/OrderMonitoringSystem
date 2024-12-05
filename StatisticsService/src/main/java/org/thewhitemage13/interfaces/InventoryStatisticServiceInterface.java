package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;

import java.util.List;

/**
 * Interface for managing {@link InventoryStatistic} objects.
 * <p>
 * This interface defines the core operations for creating, retrieving, and deleting inventory statistics.
 * </p>
 *
 * @see InventoryStatistic
 * @see ProductCreateEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface InventoryStatisticServiceInterface {

    /**
     * Creates a new inventory statistic based on the provided {@link InventoryStatistic} and {@link ProductCreateEvent}.
     * <p>
     * This method is used to create an inventory statistic when a product is added, updated, or a stock-related event occurs.
     * </p>
     *
     * @param inventoryStatistic the {@link InventoryStatistic} object to be created
     * @param productCreateEvent the {@link ProductCreateEvent} that triggers the creation of the inventory statistic
     */
    void createInventoryStatistic(InventoryStatistic inventoryStatistic, ProductCreateEvent productCreateEvent);

    /**
     * Retrieves all inventory statistics that match the specified message.
     * <p>
     * This method is used to fetch all inventory statistics filtered by a specific message.
     * </p>
     *
     * @param message the message to filter the inventory statistics by
     * @return a list of {@link InventoryStatistic} objects that match the specified message
     */
    List<InventoryStatistic> getAllInventoryStatisticsByMessage(String message);

    /**
     * Retrieves all inventory statistics without any filters.
     * <p>
     * This method fetches all inventory statistics stored in the system.
     * </p>
     *
     * @return a list of all {@link InventoryStatistic} objects
     */
    List<InventoryStatistic> getAllInventoryStatistics();

    /**
     * Deletes the inventory statistic with the specified ID.
     * <p>
     * This method is used to remove an inventory statistic based on its unique identifier.
     * </p>
     *
     * @param statisticId the ID of the {@link InventoryStatistic} to be deleted
     */
    void deleteInventoryStatisticById(Long statisticId);
}

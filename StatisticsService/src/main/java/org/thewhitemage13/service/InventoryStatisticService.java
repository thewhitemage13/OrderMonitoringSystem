package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.interfaces.InventoryStatisticServiceInterface;
import org.thewhitemage13.repository.InventoryStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing {@link InventoryStatistic} entities.
 * <p>
 * This class provides methods for creating, retrieving, and deleting inventory statistics. It also allows fetching
 * statistics filtered by message.
 * </p>
 *
 * @see InventoryStatistic
 * @see ProductCreateEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class InventoryStatisticService implements InventoryStatisticServiceInterface {
    private final InventoryStatisticRepository inventoryStatisticRepository;

    /**
     * Constructs an {@link InventoryStatisticService} with the given {@link InventoryStatisticRepository}.
     *
     * @param inventoryStatisticRepository the repository for accessing inventory statistics data
     */
    @Autowired
    public InventoryStatisticService(InventoryStatisticRepository inventoryStatisticRepository) {
        this.inventoryStatisticRepository = inventoryStatisticRepository;
    }

    /**
     * Creates a new inventory statistic using the information from the provided {@link ProductCreateEvent}.
     * <p>
     * This method sets the current date, product name, product ID, and quantity from the event, and then persists
     * the new statistic to the database.
     * </p>
     *
     * @param inventoryStatistic the {@link InventoryStatistic} entity to be created
     * @param productCreateEvent the {@link ProductCreateEvent} containing the details for the new statistic
     */
    @Override
    public void createInventoryStatistic(InventoryStatistic inventoryStatistic,ProductCreateEvent productCreateEvent) {
        inventoryStatistic.setDate(LocalDate.now());
        inventoryStatistic.setQuantity(productCreateEvent.getQuantity());
        inventoryStatistic.setItem(productCreateEvent.getName());
        inventoryStatistic.setProductId(productCreateEvent.getId());
        inventoryStatisticRepository.save(inventoryStatistic);
    }

    /**
     * Retrieves all {@link InventoryStatistic} entities that match the specified message.
     * <p>
     * If no statistics are found for the given message, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param message the message to filter the inventory statistics
     * @return a list of {@link InventoryStatistic} matching the specified message
     * @throws StatisticsNotFoundException if no statistics are found with the given message
     */
    @Override
    public List<InventoryStatistic> getAllInventoryStatisticsByMessage(String message) {
        return inventoryStatisticRepository
                .findAllByMessage(message)
                .orElseThrow(() -> new StatisticsNotFoundException("Statistic with massage = %s not found".formatted(message)));
    }

    /**
     * Retrieves all {@link InventoryStatistic} entities.
     *
     * @return a list of all {@link InventoryStatistic} entities
     */
    @Override
    public List<InventoryStatistic> getAllInventoryStatistics() {
        return inventoryStatisticRepository.findAll();
    }

    /**
     * Deletes the {@link InventoryStatistic} with the specified ID.
     * <p>
     * If the statistic with the given ID does not exist, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param statisticId the ID of the {@link InventoryStatistic} to be deleted
     * @throws StatisticsNotFoundException if no statistic is found with the given ID
     */
    @Override
    public void deleteInventoryStatisticById(Long statisticId) {
        InventoryStatistic deleteStatistic = inventoryStatisticRepository
                .findById(statisticId)
                .orElseThrow(() -> new StatisticsNotFoundException("Statistic with id = %s not found".formatted(statisticId)));
        inventoryStatisticRepository.delete(deleteStatistic);
    }
}

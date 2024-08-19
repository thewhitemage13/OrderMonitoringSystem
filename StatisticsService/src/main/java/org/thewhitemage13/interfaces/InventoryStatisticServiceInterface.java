package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;

import java.util.List;

public interface InventoryStatisticServiceInterface {
    void createInventoryStatistic(InventoryStatistic inventoryStatistic, ProductCreateEvent productCreateEvent);
    List<InventoryStatistic> getAllInventoryStatisticsByMessage(String message);
    List<InventoryStatistic> getAllInventoryStatistics();
    void deleteInventoryStatisticById(Long statisticId);
}

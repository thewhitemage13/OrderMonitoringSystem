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

@Transactional
@Service
public class InventoryStatisticService implements InventoryStatisticServiceInterface {
    private final InventoryStatisticRepository inventoryStatisticRepository;

    @Autowired
    public InventoryStatisticService(InventoryStatisticRepository inventoryStatisticRepository) {
        this.inventoryStatisticRepository = inventoryStatisticRepository;
    }

    @Override
    public void createInventoryStatistic(InventoryStatistic inventoryStatistic,ProductCreateEvent productCreateEvent) {
        inventoryStatistic.setDate(LocalDate.now());
        inventoryStatistic.setQuantity(productCreateEvent.getQuantity());
        inventoryStatistic.setItem(productCreateEvent.getName());
        inventoryStatistic.setProductId(productCreateEvent.getId());
        inventoryStatisticRepository.save(inventoryStatistic);
    }

    @Override
    public List<InventoryStatistic> getAllInventoryStatisticsByMessage(String message) {
        return inventoryStatisticRepository.findAllByMessage(message).orElseThrow(() -> new StatisticsNotFoundException("Statistic with massage = %s not found".formatted(message)));
    }

    @Override
    public List<InventoryStatistic> getAllInventoryStatistics() {
        return inventoryStatisticRepository.findAll();
    }

    @Override
    public void deleteInventoryStatisticById(Long statisticId) {
        InventoryStatistic deleteStatistic = inventoryStatisticRepository.findById(statisticId).orElseThrow(() -> new StatisticsNotFoundException("Statistic with id = %s not found".formatted(statisticId)));
        inventoryStatisticRepository.delete(deleteStatistic);
    }
}

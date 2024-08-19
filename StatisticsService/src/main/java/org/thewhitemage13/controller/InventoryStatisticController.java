package org.thewhitemage13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.InventoryStatisticService;

import java.util.List;

@RestController
@RequestMapping("/inventory-statistic")
public class InventoryStatisticController {
    private final InventoryStatisticService inventoryStatisticService;

    @Autowired
    public InventoryStatisticController(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    @GetMapping("/get-by-message")
    public ResponseEntity<List<InventoryStatistic>> getAllInventoryStatisticsByMessage(@RequestParam("message") String message) {
        try {
            return ResponseEntity.ok(inventoryStatisticService.getAllInventoryStatisticsByMessage(message));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<InventoryStatistic>> getAllInventoryStatistics(){
        try {
            return ResponseEntity.ok(inventoryStatisticService.getAllInventoryStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteInventoryStatisticById(@RequestParam("statisticId") Long statisticId) {
        try {
            inventoryStatisticService.deleteInventoryStatisticById(statisticId);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted statistic");
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Statistics with id = %s not found".formatted(statisticId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

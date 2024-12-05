package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.InventoryStatisticService;

import java.util.List;

@Tag(name = "Inventory Statistic Controller", description = "Operations related to inventory statistic management")
@RestController
@RequestMapping("/inventory-statistics")
public class InventoryStatisticController {
    private final InventoryStatisticService inventoryStatisticService;

    @Autowired
    public InventoryStatisticController(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    @Operation(summary = "Retrieve Inventory Statistics by Message",
            description = "Fetches all inventory statistics that match the specified message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory statistics"),
            @ApiResponse(responseCode = "404", description = "Statistics not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{message}")
    public ResponseEntity<List<InventoryStatistic>> getAllInventoryStatisticsByMessage
            (@PathVariable String message) {
        try {
            return ResponseEntity.ok(inventoryStatisticService.getAllInventoryStatisticsByMessage(message));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Retrieve All Inventory Statistics",
            description = "Fetches all inventory statistics without filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all inventory statistics"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InventoryStatistic>> getAllInventoryStatistics(){
        try {
            return ResponseEntity.ok(inventoryStatisticService.getAllInventoryStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete Inventory Statistic by ID",
            description = "Deletes the inventory statistic with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted inventory statistic"),
            @ApiResponse(responseCode = "404", description = "Statistic not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{statisticId}")
    public ResponseEntity<String> deleteInventoryStatisticById(@PathVariable("statisticId") Long statisticId) {
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

package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.OrderStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.OrderStatisticService;
import org.thewhitemage13.service.UserStatisticService;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Order Statistic Controller", description = "Operations related to order statistic management")
@RestController
@RequestMapping("/order-statistics")
public class OrderStatisticController {
    private final OrderStatisticService orderStatisticService;

    @Autowired
    public OrderStatisticController(OrderStatisticService orderStatisticService, UserStatisticService userStatisticService) {
        this.orderStatisticService = orderStatisticService;
    }

    @Operation(summary = "Delete Order Statistic by Date",
            description = "Deletes the order statistic for the specified date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted order statistic"),
            @ApiResponse(responseCode = "404", description = "Order statistic not found for the specified date"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrderStatistic
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            orderStatisticService.deleteOrderStatisticByDate(date);
            return ResponseEntity.ok("Deleted order statistic");
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get Order Statistic by Date",
            description = "Fetches the order statistic for the specified date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order statistic"),
            @ApiResponse(responseCode = "404", description = "Order statistic not found for the specified date"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<OrderStatistic> getByDate
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(orderStatisticService.getOrderStatisticByDate(date));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get All Order Statistics",
            description = "Fetches all available order statistics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all order statistics"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<OrderStatistic>> getAll() {
        try {
            return ResponseEntity.ok(orderStatisticService.getAllOrderStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

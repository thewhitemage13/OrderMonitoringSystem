package org.thewhitemage13.controller;

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

@RestController
@RequestMapping("/order-statistic")
public class OrderStatisticController {
    private final OrderStatisticService orderStatisticService;

    @Autowired
    public OrderStatisticController(OrderStatisticService orderStatisticService, UserStatisticService userStatisticService) {
        this.orderStatisticService = orderStatisticService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrderStatistic(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            orderStatisticService.deleteOrderStatisticByDate(date);
            return ResponseEntity.ok("Deleted order statistic");
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<OrderStatistic> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(orderStatisticService.getOrderStatisticByDate(date));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderStatistic>> getAll() {
        try {
            return ResponseEntity.ok(orderStatisticService.getAllOrderStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

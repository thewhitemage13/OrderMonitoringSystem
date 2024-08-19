package org.thewhitemage13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.UserStatisticService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user-statistic")
public class UserStatisticController {
    private final UserStatisticService userStatisticService;

    @Autowired
    public UserStatisticController(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserStatistic>> getAll() {
        try {
            return ResponseEntity.ok(userStatisticService.getAllUserStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<UserStatistic> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(userStatisticService.getUserStatisticByDate(date));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserStatisticByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            userStatisticService.deleteUserStatisticByDate(date);
            return ResponseEntity.ok("Deleted user statistic");
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with date = %s not found".formatted(date));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

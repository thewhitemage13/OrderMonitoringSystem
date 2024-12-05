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
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.UserStatisticService;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "User Statistic Controller", description = "Operations related to user statistic management")
@RestController
@RequestMapping("/user-statistics")
public class UserStatisticController {
    private final UserStatisticService userStatisticService;

    @Autowired
    public UserStatisticController(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @Operation(summary = "Get All User Statistics",
            description = "Fetches all user statistics available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all user statistics"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<UserStatistic>> getAll() {
        try {
            return ResponseEntity.ok(userStatisticService.getAllUserStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Get User Statistic by Date",
            description = "Fetches the user statistic for a specific date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user statistic"),
            @ApiResponse(responseCode = "404", description = "User statistic not found for the specified date"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(params = "date")
    public ResponseEntity<UserStatistic> getByDate
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(userStatisticService.getUserStatisticByDate(date));
        } catch (StatisticsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Delete User Statistic by Date",
            description = "Deletes the user statistic for a specific date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted user statistic"),
            @ApiResponse(responseCode = "404", description = "User statistic not found for the specified date"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteUserStatisticByDate
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
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

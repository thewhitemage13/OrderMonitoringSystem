package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.service.NotificationService;

import java.util.List;

@Tag(name = "Notification Controller", description = "Operations related to notification management")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(
            summary = "Update notification status",
            description = "Updates the status of a notification by its ID. The status indicates whether the notification is active or not."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification updated successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{notificationId}")
    public ResponseEntity<String> updateNotification(
            @Parameter(description = "ID of the notification to update", example = "1")
            @PathVariable("notificationId") Long notificationId,
            @Parameter(description = "New status of the notification (true for active, false for inactive)", example = "true")
            @RequestParam boolean status) {
        try {
            notificationService.updateStatus(notificationId, status);
            return ResponseEntity.ok("Notification updated");
        }catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification with id = %s not found".formatted(notificationId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Get notifications by user ID",
            description = "Retrieves all notifications associated with a specific user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No notifications found for the user ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationByUserId(
            @Parameter(description = "ID of the user whose notifications are being retrieved", example = "123")
            @RequestParam("userId") Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
        }catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
package org.thewhitemage13.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PutMapping("/update/{notificationId}")
    public ResponseEntity<String> updateNotification(@PathVariable("notificationId") Long notificationId, @RequestParam boolean status) {
        try {
            notificationService.updateStatus(notificationId, status);
            return ResponseEntity.ok("Notification updated");
        }catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification with id = %s not found".formatted(notificationId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<NotificationDTO>> getNotificationByUserId(@RequestParam("userId") Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
        }catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
package org.thewhitemage13.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.dto.GetNotificationDTO;
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

    @Operation(summary = "Create a new notification", description = "Creates a new notification for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody CreateNotificationDTO createNotificationDTO) {
        try {
            notificationService.createNotification(createNotificationDTO);
            return ResponseEntity.ok("Notification created");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Update notification status", description = "Updates the read status of a specific notification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification updated successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{notificationId}/status")
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

    @Operation(summary = "Get notification by user ID", description = "Retrieves a single notification for a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetNotificationDTO> getNotificationByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationById(userId));
        } catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all notifications by user ID", description = "Retrieves all notifications for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notifications not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<GetNotificationDTO>> getNotificationsByUserId(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
        }catch (NotificationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

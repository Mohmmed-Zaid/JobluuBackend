package com.Cubix.Jobluu.controller;

import com.Cubix.Jobluu.dto.NotificationDTO;
import com.Cubix.Jobluu.entities.Notification;
import com.Cubix.Jobluu.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notification")
@Validated
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        return new ResponseEntity<>(notificationService.getUnreadNotifications(userId), HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable Long userId) {
        return new ResponseEntity<>(notificationService.getAllNotifications(userId), HttpStatus.OK);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        return new ResponseEntity<>(notificationService.getUnreadCount(userId), HttpStatus.OK);
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO notificationDTO) {
        notificationService.sendNotification(notificationDTO);
        return new ResponseEntity<>("Notification sent successfully", HttpStatus.CREATED);
    }

    @PutMapping("/mark-read/{notificationId}")
    public ResponseEntity<String> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return new ResponseEntity<>("Notification marked as read", HttpStatus.OK);
    }

    @PutMapping("/mark-all-read/{userId}")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return new ResponseEntity<>("All notifications marked as read", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{notificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all/{userId}")
    public ResponseEntity<String> deleteAllNotifications(@PathVariable Long userId) {
        notificationService.deleteAllNotifications(userId);
        return new ResponseEntity<>("All notifications deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/type/{userId}/{action}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable Long userId, @PathVariable String action) {
        return new ResponseEntity<>(notificationService.getNotificationsByType(userId, action), HttpStatus.OK);
    }
}
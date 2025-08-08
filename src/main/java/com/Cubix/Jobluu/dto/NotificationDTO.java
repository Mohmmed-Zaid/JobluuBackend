package com.Cubix.Jobluu.dto;

import com.Cubix.Jobluu.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;
    private Long userId;
    private String message;
    private String action; // e.g., "JOB_APPLICATION", "PROFILE_UPDATE", "MESSAGE"
    private String route; // Frontend route to navigate
    private NotificationStatus status;
    private LocalDateTime timestamp;
    private String title; // Add title field
    private String icon; // Icon name/class for frontend

    public Notification toEntity() {
        return new Notification(this.id, this.userId, this.message, this.action, this.route, this.status, this.timestamp);
    }
}
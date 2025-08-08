package com.Cubix.Jobluu.service;

import com.Cubix.Jobluu.dto.NotificationDTO;
import com.Cubix.Jobluu.entities.Notification;

import java.util.List;

public interface NotificationService {

    void sendNotification(NotificationDTO notificationDTO);

    List<Notification> getUnreadNotifications(Long userId);

    List<Notification> getAllNotifications(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    void deleteNotification(Long notificationId);

    void deleteAllNotifications(Long userId);

    Long getUnreadCount(Long userId);

    List<Notification> getNotificationsByType(Long userId, String action);
}
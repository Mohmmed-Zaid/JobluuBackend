package com.Cubix.Jobluu.repositories;

import com.Cubix.Jobluu.entities.Notification;
import com.Cubix.Jobluu.dto.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, Long> {
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);

    List<Notification> findByUserIdOrderByTimestampDesc(Long userId);

    List<Notification> findByUserId(Long userId);

    Long countByUserIdAndStatus(Long userId, NotificationStatus status);

    List<Notification> findByUserIdAndActionOrderByTimestampDesc(Long userId, String action);
}

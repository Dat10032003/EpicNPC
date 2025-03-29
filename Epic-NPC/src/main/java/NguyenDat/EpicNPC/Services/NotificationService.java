package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Notification;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndIsReadFalse(user);
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(User user) {
        List<Notification> notifications = notificationRepository.findByUserAndIsReadFalse(user);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void createNotification(User sender, User receiver, String content) {
        System.out.println("Creating notification for " + receiver.getUsername() + " from " + sender.getUsername());
        Notification notification = Notification.builder()
                .user(receiver)
                .content(content)
                .isRead(false)
                .timestamp(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);  // Đảm bảo đoạn này được thực thi
    }


}

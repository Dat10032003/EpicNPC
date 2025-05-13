package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Notification;
import NguyenDat.EpicNPC.Entities.NotificationType;
import NguyenDat.EpicNPC.Entities.Trade;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    public void createNotification(User sender, User receiver, String message) {
        Notification noti = Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(NotificationType.CHAT)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationRepo.save(noti);
    }

    public void sendTradeRequest(User receiver, Trade trade, String message) {
        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setTrade(trade);
        notification.setType(NotificationType.TRADE_REQUEST);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setMessage(message); // <-- sử dụng custom message
        notification.setContent("Trade between " + trade.getBuyer().getUsername() + " and " + trade.getSeller().getUsername() + " has been created.");
        notification.setRead(false);
        notificationRepo.save(notification);
    }



    public void sendSystem(User receiver, String message) {
        Notification noti = Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(NotificationType.SYSTEM)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationRepo.save(noti);
    }

    public void sendChat(User receiver, Trade trade, String message) {
        Notification noti = Notification.builder()
                .receiver(receiver)
                .message(message)
                .type(NotificationType.CHAT)
                .trade(trade)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationRepo.save(noti);
    }

    public void markAsRead(Notification noti) {
        noti.setRead(true);
        notificationRepo.save(noti);
    }

    public void markAllAsRead(List<Notification> notifications) {
        for (Notification noti : notifications) {
            noti.setRead(true);
        }
        notificationRepo.saveAll(notifications);
    }
}
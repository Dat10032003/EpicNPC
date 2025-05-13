package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.Notification;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Services.NotificationService;
import NguyenDat.EpicNPC.Services.UserService;
import NguyenDat.EpicNPC.Repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;
    private final NotificationRepository notificationRepo;

    @PostMapping("/markAsRead/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationRepo.findById(id).ifPresent(notificationService::markAsRead);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/markAllAsRead")
    public ResponseEntity<?> markAllAsRead() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        List<Notification> unread = notificationRepo.findByReceiverIdAndIsReadFalse(user.getId());
        notificationService.markAllAsRead(unread);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unreadCount")
    public ResponseEntity<?> getUnreadNotificationsCount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        int count = notificationRepo.findByReceiverIdAndIsReadFalse(user.getId()).size();
        return ResponseEntity.ok(Collections.singletonMap("unreadCount", count));
    }
}

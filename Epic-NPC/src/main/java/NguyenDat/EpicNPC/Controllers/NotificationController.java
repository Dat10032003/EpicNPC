package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Services.NotificationService;
import NguyenDat.EpicNPC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping("/markAsRead/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/markAllAsRead")
    public ResponseEntity<?> markAllAsRead() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unreadCount")
    public ResponseEntity<?> getUnreadNotificationsCount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        int count = notificationService.getUnreadNotifications(user).size();
        return ResponseEntity.ok(Collections.singletonMap("unreadCount", count));
    }
}

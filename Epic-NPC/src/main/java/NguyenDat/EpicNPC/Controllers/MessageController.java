package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.Chat;
import NguyenDat.EpicNPC.Entities.Message;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Services.ChatService;
import NguyenDat.EpicNPC.Services.MessageService;
import NguyenDat.EpicNPC.Services.NotificationService;
import NguyenDat.EpicNPC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;
    private final NotificationService notificationService;

    // Inject SimpMessagingTemplate
    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping
    public String viewMessages(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);

        List<Chat> chatList = chatService.getChatsByUser(currentUser);
        model.addAttribute("chats", chatList);
        model.addAttribute("currentUser", currentUser);

        if (!chatList.isEmpty()) {
            Chat latestChat = chatList.get(0);
            User otherUser = latestChat.getOtherUser(currentUser);
            List<Message> messages = messageService.getMessagesByChatId(latestChat.getId());

            model.addAttribute("messages", messages);
            model.addAttribute("otherUser", otherUser);
            model.addAttribute("currentChat", latestChat);
        } else {
            model.addAttribute("messages", null);
            model.addAttribute("otherUser", null);
            model.addAttribute("currentChat", null);
        }

        return "message/viewChat";
    }


    @GetMapping("/chat/{chatId}")
    public String viewChat(@PathVariable Long chatId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUsername(username);
        Chat chat = chatService.getChatById(chatId);

        if (chat == null || (!chat.getUser1().equals(currentUser) && !chat.getUser2().equals(currentUser))) {
            return "redirect:/error";
        }

        User otherUser = chat.getOtherUser(currentUser);
        List<Message> messages = messageService.getMessagesByChatId(chatId);
        List<Chat> chatList = chatService.getChatsByUser(currentUser);

        model.addAttribute("messages", messages);
        model.addAttribute("otherUser", otherUser);
        model.addAttribute("chats", chatList);
        model.addAttribute("currentChat", chat);
        model.addAttribute("currentUser", currentUser);

        return "message/viewChat";
    }

    @PostMapping("/send")
    public String sendMessage(
            @RequestParam(required = false) Long chatId,
            @RequestParam Long receiverId,
            @RequestParam String content) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User sender = userService.findByUsername(username);
        User receiver = userService.getUserById(receiverId);

        Chat chat = (chatId != null) ? chatService.getChatById(chatId) : chatService.getOrCreateChat(sender, receiver);

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .chat(chat)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();

        messageService.sendMessage(message);

        // Log để kiểm tra quá trình tạo thông báo
        System.out.println("Sending notification to " + receiver.getUsername());

        // Tạo thông báo cho người nhận
        notificationService.createNotification(sender, receiver, "New message from " + sender.getUsername());

        // Gửi thông báo qua WebSocket
        messagingTemplate.convertAndSend("/topic/notifications/" + receiver.getId(), "You have a new message from " + sender.getUsername());

        return "redirect:/messages/chat/" + chat.getId();
    }

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public Message sendMessageToChat(@DestinationVariable Long chatId, Message message) {
        Chat chat = chatService.getChatById(chatId);
        if (chat == null) {
            throw new IllegalArgumentException("Chat with ID " + chatId + " does not exist.");
        }
        message.setChat(chat); // Gán chat cho tin nhắn trước khi lưu
        messageService.sendMessage(message);
        return message;
    }
}
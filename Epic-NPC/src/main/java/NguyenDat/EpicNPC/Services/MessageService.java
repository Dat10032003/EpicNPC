package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Chat;
import NguyenDat.EpicNPC.Entities.Message;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public void sendMessage(Message message) {
        if (message.getChat() == null) {
            throw new IllegalArgumentException("Chat cannot be null");
        }
        messageRepository.save(message);
    }

    @Transactional(readOnly = true) // Đảm bảo lazy loading hoạt động khi truy vấn dữ liệu
    public List<Message> getMessagesByChatId(Long chatId) {
        Chat chat = new Chat();
        chat.setId(chatId);
        return messageRepository.findByChat(chat);
    }
}

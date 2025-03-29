package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Chat;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public Chat getOrCreateChat(User user1, User user2) {
        Optional<Chat> existingChat = chatRepository.findByUser1AndUser2(user1, user2)
                .or(() -> chatRepository.findByUser1AndUser2(user2, user1));
        return existingChat.orElseGet(() -> chatRepository.save(new Chat(user1, user2)));
    }

    public List<Chat> getChatsByUser(User user) {
        return chatRepository.findByUser1OrUser2(user, user);
    }

    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }
}

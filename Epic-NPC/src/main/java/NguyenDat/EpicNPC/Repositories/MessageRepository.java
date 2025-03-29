package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Message;
import NguyenDat.EpicNPC.Entities.Chat;
import NguyenDat.EpicNPC.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Find all messages in a specific chat
    List<Message> findByChat(Chat chat);

    // Additional method to find messages between two users in the same chat
    List<Message> findByChatAndSenderAndReceiver(Chat chat, User sender, User receiver);

    // Method to get all messages between two users, regardless of sender and receiver order
    List<Message> findByChatAndSenderAndReceiverOrChatAndSenderAndReceiver(
            Chat chat1, User sender1, User receiver1, Chat chat2, User sender2, User receiver2);
}

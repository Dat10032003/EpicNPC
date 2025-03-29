package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Entities.Thread;
import NguyenDat.EpicNPC.Repositories.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;

    public List<Thread> getThreadsByGame(Game game) {
        return threadRepository.findByGame(game);
    }

    public void saveThread(Thread thread) {
        threadRepository.save(thread);
    }

    public Thread getThreadById(Long threadId) {
        return threadRepository.findById(threadId).orElse(null);
    }

    public void deleteThread(Long threadId) {
        threadRepository.deleteById(threadId);
    }
}

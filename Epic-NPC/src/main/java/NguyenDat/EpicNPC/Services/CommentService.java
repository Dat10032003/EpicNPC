package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Comment;
import NguyenDat.EpicNPC.Entities.Message;
import NguyenDat.EpicNPC.Repositories.CommentRepository;
import NguyenDat.EpicNPC.Repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(String username, String content) {
        Comment comment = new Comment(username, content, LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}


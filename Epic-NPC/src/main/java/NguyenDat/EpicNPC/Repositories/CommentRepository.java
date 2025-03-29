package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Comment;
import NguyenDat.EpicNPC.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}


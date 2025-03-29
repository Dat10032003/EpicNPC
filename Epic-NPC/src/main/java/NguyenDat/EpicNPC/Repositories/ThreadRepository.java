package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Thread;
import NguyenDat.EpicNPC.Entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    List<Thread> findByGame(Game game);
}

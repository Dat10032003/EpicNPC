package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAllByOrderByIdDesc(Pageable pageable);
    List<Game> findByNameContainingIgnoreCase(String name);
}

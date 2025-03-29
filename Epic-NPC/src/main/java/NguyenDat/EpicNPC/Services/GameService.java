package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> getRecentlyAddedGames(int limit) {
        return gameRepository.findAllByOrderByIdDesc(PageRequest.of(0, limit));
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public void updateGame(Long id, Game updatedGame) {
        Game existingGame = gameRepository.findById(id).orElse(null);
        if (existingGame != null) {
            existingGame.setName(updatedGame.getName());
            existingGame.setCreatedDate(updatedGame.getCreatedDate());
            existingGame.setNote(updatedGame.getNote());
            gameRepository.save(existingGame);
        }
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public List<Game> searchGamesByName(String name) {
        return gameRepository.findByNameContainingIgnoreCase(name);
    }

}

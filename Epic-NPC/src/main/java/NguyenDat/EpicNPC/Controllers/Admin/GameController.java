package NguyenDat.EpicNPC.Controllers.Admin;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin/games")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public String getGames(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "admin/games/list-games"; // Trang list-games.html để hiển thị danh sách game
    }

    @GetMapping("/add")
    public String addGameForm(Model model) {
        model.addAttribute("game", new Game());
        return "admin/games/addGame"; // Trang addGame.html để thêm game mới
    }

    @PostMapping("/add")
    public String addGame(@Valid @ModelAttribute Game game, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/games/addGame";
        }
        game.setCreatedDate(LocalDate.now()); // Đặt ngày tạo cho game mới
        gameService.saveGame(game);
        return "redirect:/admin/games";
    }

    @GetMapping("/edit/{id}")
    public String editGameForm(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            model.addAttribute("game", game);
            return "admin/games/editGame"; // Trang editGame.html để chỉnh sửa game
        }
        return "redirect:/admin/games"; // Điều hướng về danh sách nếu game không tồn tại
    }

    @PostMapping("/edit/{id}")
    public String editGame(@PathVariable Long id, @Valid @ModelAttribute Game game, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/games/editGame";
        }
        gameService.updateGame(id, game);
        return "redirect:/admin/games";
    }
    @PostMapping("/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return "redirect:/admin/games";
    }




    @GetMapping("/details/{id}")
    public String gameDetails(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            model.addAttribute("game", game);
            return "admin/games/gameDetails"; // Trang gameDetails.html để xem chi tiết game
        }
        return "redirect:/admin/games"; // Điều hướng về danh sách nếu game không tồn tại
    }
}

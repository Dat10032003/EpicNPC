package NguyenDat.EpicNPC.Controllers.Admin;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Services.GameService;
import NguyenDat.EpicNPC.Services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/threads")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Chỉ cho phép admin truy cập vào controller này
public class AdminThreadController {

    private final GameService gameService;
    private final ThreadService threadService;

    // Hiển thị danh sách games để quản lý threads
    @GetMapping
    public String showGameList(Model model) {
        model.addAttribute("games", gameService.getAllGames()); // Lấy danh sách game
        return "admin/threads/gameList"; // Trang hiển thị danh sách game
    }

    // Hiển thị danh sách threads cho một game cụ thể
    @GetMapping("/{gameId}")
    public String viewThreads(@PathVariable Long gameId, Model model) {
        Game game = gameService.getGameById(gameId); // Lấy game theo ID
        model.addAttribute("game", game);
        model.addAttribute("threads", threadService.getThreadsByGame(game)); // Lấy danh sách threads theo game
        return "admin/threads/threadManagement"; // Trang quản lý threads
    }

    // Xóa một thread
    @PostMapping("/{gameId}/{threadId}/delete")
    public String deleteThread(@PathVariable Long gameId, @PathVariable Long threadId) {
        threadService.deleteThread(threadId); // Xóa thread
        return "redirect:/admin/threads/" + gameId; // Quay lại danh sách threads
    }
}

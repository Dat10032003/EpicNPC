package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GameService gameService;

    // Trang chủ hiển thị danh sách "Recently Added"
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Epic NPC Marketplace");

        // Lấy danh sách các game mới được thêm (giới hạn 9 game)
        List<Game> recentlyAddedGames = gameService.getRecentlyAddedGames(9);
        model.addAttribute("recentlyAddedGames", recentlyAddedGames);

        return "homepage"; // Sử dụng file templates/homepage.html
    }

    // Trang danh sách game
    @GetMapping("/games")
    public String games(Model model) {
        model.addAttribute("title", "Game Listings");

        // Lấy danh sách tất cả game (nếu cần)
        List<Game> allGames = gameService.getAllGames();
        model.addAttribute("games", allGames);

        return "games/index"; // Sử dụng file templates/games/index.html
    }

    // Trang chi tiết game
    @GetMapping("/games/details")
    public String gameDetails(Model model) {
        model.addAttribute("title", "Game Details");
        return "games/details"; // Sử dụng file templates/games/details.html
    }

    // Trang đăng bài mới
    @GetMapping("/games/post-thread")
    public String postThread(Model model) {
        model.addAttribute("title", "Post New Thread");
        return "games/post-thread"; // Sử dụng file templates/games/post-thread.html
    }

    // Trang hồ sơ người dùng
    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        model.addAttribute("title", "User Profile");
        return "Users/userProfile"; // Sử dụng file templates/Users/userProfile.html
    }

    // Endpoint tìm kiếm cho AJAX
    @GetMapping("/api/search")
    @ResponseBody
    public List<Game> searchGames(@RequestParam("query") String query) {
        return gameService.searchGamesByName(query);
    }
}

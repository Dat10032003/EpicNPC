package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.Game;
import NguyenDat.EpicNPC.Entities.Thread;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Services.GameService;
import NguyenDat.EpicNPC.Services.ThreadService;
import NguyenDat.EpicNPC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
@RequestMapping("/games/{gameId}/threads")
public class ThreadController {

    private final GameService gameService;
    private final ThreadService threadService;
    private final UserService userService;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public String viewThreads(@PathVariable Long gameId, Model model) {
        Game game = gameService.getGameById(gameId);
        model.addAttribute("game", game);
        model.addAttribute("threads", threadService.getThreadsByGame(game));
        return "thread/viewThreads";
    }

    @GetMapping("/post")
    public String postThreadForm(@PathVariable Long gameId, Model model) {
        model.addAttribute("gameId", gameId);
        model.addAttribute("thread", new Thread());
        return "thread/postThread";
    }

    @PostMapping("/post")
    public String postThread(
            @PathVariable Long gameId,
            @ModelAttribute Thread thread,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("price") Double price) throws IOException {

        Game game = gameService.getGameById(gameId);
        thread.setGame(game);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        thread.setUser(user);

        // Thiết lập giá cho thread từ giá người dùng nhập vào
        thread.setPrice(price);

        if (!imageFile.isEmpty()) {
            String uploadDir = "src/main/resources/static/images/threaduploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, imageFile.getBytes());

            thread.setImageUrl("/images/threaduploads/" + fileName);
        }

        threadService.saveThread(thread);
        return "redirect:/games/" + gameId + "/threads";
    }

    @GetMapping("/{threadId}")
    public String viewThread(@PathVariable Long gameId, @PathVariable Long threadId, Model model, HttpServletRequest request) {
        Thread thread = threadService.getThreadById(threadId);
        String requestUrl = request.getRequestURL().toString(); // Lấy URL hiện tại
        model.addAttribute("thread", thread);
        model.addAttribute("requestUrl", requestUrl); // Thêm URL vào model
        return "thread/threadDetails";
    }
}

package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.Comment;
import NguyenDat.EpicNPC.Entities.Message;
import NguyenDat.EpicNPC.Services.CommentService;
import NguyenDat.EpicNPC.Services.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam String content, Principal principal, Model model) {
        String username = principal.getName(); // Lấy tên người dùng hiện tại (có thể là khách hoặc người dùng đã đăng ký)
        commentService.addComment(username, content);
        model.addAttribute("message", "Comment added successfully!");
        return "redirect:/games/details";
    }
}


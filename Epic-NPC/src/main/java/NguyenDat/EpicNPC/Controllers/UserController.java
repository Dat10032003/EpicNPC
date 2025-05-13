package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.UserRepository;
import NguyenDat.EpicNPC.Services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/login")
    public String login() {
        return "/login";  // Trả về trang đăng nhập
    }

    @PostMapping("/login")
    public String homepage(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("avatar", user.getAvatar());
        }
        return "homepage";  // Trả về trang chủ với thông tin người dùng (username, avatar)
    }

    @GetMapping("/register")
    public String register(@NotNull Model model) {
        model.addAttribute("user", new User());
        return "users/register";  // Đảm bảo rằng users/register.html tồn tại
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register";  // Đảm bảo rằng users/register.html tồn tại
        }
        userService.save(user);
        return "redirect:/login";  // Chuyển hướng đến trang login sau khi đăng ký thành công
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("user", user);  // Đảm bảo thêm thông tin user vào model
        }
        return "users/userProfile";
    }



    @PostMapping("/profile/updateAvatar")
    public String updateAvatar(Authentication authentication, @RequestParam("avatarPath") String avatarPath) {
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            userService.updateAvatar(authentication.getName(), avatarPath);
        }
        return "redirect:/profile";  // Chuyển hướng lại trang thông tin cá nhân sau khi cập nhật avatar
    }

    @GetMapping("/search")
    public List<String> searchUsers(@RequestParam("query") String query) {
        return userRepository.findByUsernameStartingWithIgnoreCase(query)
                .stream()
                .map(User::getUsername)
                .toList();
    }

}

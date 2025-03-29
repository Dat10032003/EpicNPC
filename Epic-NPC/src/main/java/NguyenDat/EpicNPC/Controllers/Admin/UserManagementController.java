package NguyenDat.EpicNPC.Controllers;

import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Chỉ cho phép ADMIN truy cập
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;

    // Hiển thị danh sách người dùng
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Lấy danh sách người dùng
        model.addAttribute("users", users); // Thêm danh sách vào model
        return "admin/users/userList"; // Trả về trang hiển thị danh sách người dùng
    }

    // Hiển thị thông tin chi tiết của người dùng
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id); // Lấy thông tin người dùng theo ID
        model.addAttribute("user", user); // Thêm thông tin người dùng vào model
        return "admin/users/userDetail"; // Trả về trang hiển thị thông tin người dùng
    }

    // Vô hiệu hóa tài khoản người dùng
    @PostMapping("/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        user.setEnabled(false); // Vô hiệu hóa tài khoản
        userService.save(user); // Lưu thay đổi
        return "redirect:/admin/users"; // Chuyển hướng về danh sách người dùng
    }

    // Xóa tài khoản người dùng
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Xóa người dùng
        return "redirect:/admin/users"; // Chuyển hướng về danh sách người dùng
    }
}

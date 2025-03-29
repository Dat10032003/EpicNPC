package NguyenDat.EpicNPC.Controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Chỉ cho phép người dùng có quyền ADMIN truy cập
public class AdminController {

    @GetMapping
    public String adminDashboard(Model model) {
        // Thêm dữ liệu cần thiết cho dashboard, ví dụ thống kê hoặc danh sách game
        return "admin/dashboard";  // Trả về trang admin/dashboard.html
    }
}

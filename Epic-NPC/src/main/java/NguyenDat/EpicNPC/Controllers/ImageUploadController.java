package NguyenDat.EpicNPC.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class ImageUploadController {

    private static final String UPLOAD_DIR = "static/images/threaduploads/";

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/profile?error";
        }

        try {
            String fileName = file.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/profile?error";
        }

        return "redirect:/profile?success";
    }
}

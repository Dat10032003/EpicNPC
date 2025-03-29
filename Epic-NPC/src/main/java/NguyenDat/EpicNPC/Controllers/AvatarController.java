package NguyenDat.EpicNPC.Controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AvatarController {

    @GetMapping("/avatars")
    @ResponseBody
    public List<String> getAvatars() {
        try {
            Resource resource = new ClassPathResource("static/images/avatar");
            Path resourcePath = resource.getFile().toPath();
            return Files.walk(resourcePath, 1)
                    .filter(path -> !path.equals(resourcePath))
                    .map(path -> "/images/avatar/" + path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

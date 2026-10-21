package be.iccbxl.pid.reservation2627.ArtistController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "home";
    }
}
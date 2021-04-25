package interview.testing.csg.attendancetracker.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomePageController {

    @GetMapping("/")
    public String welcomeMessage() {
        return "welcome";
    }
}

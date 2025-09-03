package betterpedia.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class F101UserManagementController {

//    // Default home → goes to login page for R0
//    @GetMapping("/")
//    public String home() {
//        return "login";
//    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @GetMapping("/account")
    public String accountSettings(Model model) {
        model.addAttribute("pageTitle", "Account Settings");
        return "settings";
    }

    @GetMapping("/editor")
    public String editor(Model model) {
        model.addAttribute("pageTitle", "Article Editor");
        return "editor";
    }
}

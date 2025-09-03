package org.example.asd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class F101UserManagementController {

    // Login page (UI only)
    @GetMapping({"/login"})
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    // Account settings page (UI only)
    @GetMapping("/account")
    public String accountSettings(Model model) {
        model.addAttribute("pageTitle", "Account Settings");
        return "settings";
    }

    // Create/Edit article page (UI only)
    @GetMapping("/editor")
    public String editor(Model model) {
        model.addAttribute("pageTitle", "Article Editor");
        return "editor";
    }
}

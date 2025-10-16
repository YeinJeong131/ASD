package betterpedia.controller;

import betterpedia.services.UserSettingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // returns html page
public class UserSettingPageController {

    @Autowired
    private UserSettingService userSettingService;

    // GET /settings
    @GetMapping("/settings")
    public String settingsPage(HttpSession session) {
        // check - logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
            // templates/settings_page.html
            return "settings_page";
    }
}
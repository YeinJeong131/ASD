package betterpedia.controller;

import betterpedia.model.Badge;
import betterpedia.services.BadgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/badges")
    public String showBadges(Model model) {
        List<Badge> badges = badgeService.getAllBadges();
        model.addAttribute("badges", badges);
        return "badges"; // matches templates/badges.html
    }
}
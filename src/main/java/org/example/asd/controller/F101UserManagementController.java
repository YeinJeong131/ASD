package org.example.asd.controller;

import org.example.asd.model.User;
import org.example.asd.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class F101UserManagementController {

    private final UserService userService;

    public F101UserManagementController(UserService userService) {
        this.userService = userService;
    }

    // Root: redirect to account for the first available user
    @GetMapping("/")
    public String home() {
        Long uid = userService.listAll().stream()
                .findFirst()
                .map(User::getId)
                .orElse(null);

        // If no users exist yet, send to login page (still works without security)
        if (uid == null) {
            return "redirect:/login";
        }
        return "redirect:/account?uid=" + uid;
    }

    // Account settings page (needs uid for now; security later)
    @GetMapping("/account")
    public String accountSettings(@RequestParam("uid") Long uid, Model model) {
        User u = userService.getById(uid);
        model.addAttribute("pageTitle", "Account Settings");
        model.addAttribute("user", u);
        return "account-settings";
    }

    // Save profile (email only)
    @PostMapping("/account/profile")
    public String saveProfile(@RequestParam Long uid,
                              @RequestParam String email,
                              RedirectAttributes ra) {
        userService.updateProfile(uid, email);
        ra.addFlashAttribute("msg", "Profile updated");
        return "redirect:/account?uid=" + uid;
    }

    // Change password
    @PostMapping("/account/password")
    public String changePassword(@RequestParam Long uid,
                                 @RequestParam String newPassword,
                                 RedirectAttributes ra) {
        userService.changePassword(uid, newPassword);
        ra.addFlashAttribute("msg", "Password changed");
        return "redirect:/account?uid=" + uid;
    }

    // Render templates (security later)
    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/admin")
    public String adminPage() { return "admin"; }
}

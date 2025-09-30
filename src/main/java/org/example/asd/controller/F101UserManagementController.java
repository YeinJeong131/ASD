package org.example.asd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.asd.services.UserService;
import org.example.asd.model.User;



@Controller
public class F101UserManagementController {

    // Default home → goes to login page for R0
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @GetMapping("/read")
    public String readOnly(Model model) {
        model.addAttribute("pageTitle", "Betterpedia • Read");
        return "read";
    }

    @GetMapping("/account")
    public String accountSettings(@RequestParam("uid") Long uid, Model model) {
        User u = userService.getById(uid);
        model.addAttribute("pageTitle", "Account Settings");
        model.addAttribute("user", u);
        return "account-settings";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pageTitle", "Admin • User Management");
        return "admin";
    }

    @PostMapping("/account/profile")
    public String saveProfile(@RequestParam Long uid,
                              @RequestParam String email,
                              RedirectAttributes ra) {
        userService.updateProfile(uid, email);
        ra.addFlashAttribute("msg", "Profile updated");
        return "redirect:/account?uid=" + uid;
    }


    @PostMapping("/account/password")
    public String changePassword(@RequestParam Long uid,
                                 @RequestParam String newPassword,
                                 RedirectAttributes ra) {
        userService.changePassword(uid, newPassword);
        ra.addFlashAttribute("msg", "Password changed");
        return "redirect:/account?uid=" + uid;
    }

    private final UserService userService;
    public F101UserManagementController(UserService userService) {
        this.userService = userService;
    }

}

package org.example.asd.controller;

import org.example.asd.model.User;
import org.example.asd.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Show login page (Spring Security will handle POST /login)
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password.");
        if (logout != null) model.addAttribute("msg", "You’ve been signed out.");
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    // After successful login, route by role
    @GetMapping("/post-login")
    public String postLogin(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        return "redirect:" + (isAdmin ? "/admin" : "/account");
    }

    // Account page for the CURRENT signed-in user
    @GetMapping("/account")
    public String accountPage(Authentication auth, Model model) {
        String email = auth.getName(); // username = email
        User u = userRepository.findByEmail(email).orElseThrow();
        model.addAttribute("pageTitle", "Account Settings");
        model.addAttribute("user", u);
        return "account-settings";
    }

    // Simple landing page (optional)
    @GetMapping("/articles")
    public String articles(Model model) {
        model.addAttribute("pageTitle", "Articles");
        return "articles";
    }
}

package org.example.asd.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.asd.model.User;
import org.example.asd.repository.UserRepository;
import org.example.asd.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // ===== Login =====
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password.");
        if (logout != null) model.addAttribute("msg", "You’ve been signed out.");
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam(value = "remember", required = false) String remember,
                          HttpSession session,
                          HttpServletResponse response,
                          RedirectAttributes ra) {

        User u = userRepository.findByEmail(username).orElse(null);
        boolean ok = u != null && Objects.equals(u.getPassword(), password) && u.isEnabled();

        if (!ok) {
            ra.addAttribute("error", "1");
            return "redirect:/login";
        }

        session.setAttribute("uid", u.getId());
        session.setAttribute("email", u.getEmail());

        if (remember != null) {
            Cookie c = new Cookie("remember_uid", u.getId().toString());
            c.setPath("/");
            c.setMaxAge(60 * 60 * 24 * 14);
            response.addCookie(c);
        }

        // If the user has ROLE_ADMIN, send to admin; otherwise to articles
        boolean isAdmin = u.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return "redirect:" + (isAdmin ? "/admin" : "/articles");
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response, RedirectAttributes ra) {
        session.invalidate();
        Cookie c = new Cookie("remember_uid", "");
        c.setPath("/");
        c.setMaxAge(0);
        response.addCookie(c);
        ra.addAttribute("logout", "1");
        return "redirect:/login";
    }

    // ===== Signup =====
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("pageTitle", "Create account");
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirm,
                               HttpSession session,
                               RedirectAttributes ra) {
        if (!Objects.equals(password, confirm)) {
            ra.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/signup";
        }

        try {
            // Create enabled USER
            User u = userService.createUser(email, password, List.of("ROLE_USER"), true);
            // Auto-login (dev-style)
            session.setAttribute("uid", u.getId());
            session.setAttribute("email", u.getEmail());
            return "redirect:/articles";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage() != null ? e.getMessage() : "Could not create user.");
            return "redirect:/signup";
        }
    }

    // ===== Simple pages =====
    @GetMapping("/articles")
    public String articles(Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Articles");
        model.addAttribute("uid", session.getAttribute("uid"));
        model.addAttribute("email", session.getAttribute("email"));
        return "articles";
    }

    @GetMapping("/account")
    public String accountPage(Model model) {
        model.addAttribute("pageTitle", "Account Settings");
        return "account-settings";
    }
}

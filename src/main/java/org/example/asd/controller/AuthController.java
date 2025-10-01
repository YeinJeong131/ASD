package org.example.asd.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.asd.model.User;
import org.example.asd.repository.UserRepository;
import org.example.asd.services.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authManager;

    public AuthController(UserRepository userRepository,
                          UserService userService,
                          AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authManager = authManager;
    }

    // ----- Home -----
    @GetMapping("/")
    public String home() { return "redirect:/login"; }

    // ----- Login (Spring Security handles POST /login) -----
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password.");
        if (logout != null) model.addAttribute("msg", "You’ve been signed out.");
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

    // ----- Post-login landing (used by SecurityConfig.defaultSuccessUrl) -----
    @GetMapping("/post-login")
    public String postLogin(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        if (auth != null && auth.getName() != null) {
            userRepository.findByEmail(auth.getName()).ifPresent(u -> {
                session.setAttribute("uid", u.getId());
                session.setAttribute("email", u.getEmail());
            });
        }
        return "redirect:" + (isAdmin ? "/admin" : "/articles");
    }

    // ----- Articles -----
    @GetMapping("/articles")
    public String articles(Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Articles");
        model.addAttribute("uid", session.getAttribute("uid"));
        model.addAttribute("email", session.getAttribute("email"));
        return "articles";
    }

    // ----- Account (requires session.uid) -----
    @GetMapping("/account")
    public String accountPage(Model model, HttpSession session, RedirectAttributes ra) {
        Object uidObj = session.getAttribute("uid");
        if (uidObj == null) {
            ra.addFlashAttribute("error", "Please sign in to access your account.");
            return "redirect:/login";
        }
        Long uid = (uidObj instanceof Long) ? (Long) uidObj : Long.valueOf(uidObj.toString());
        User u = userRepository.findById(uid).orElse(null);
        if (u == null) {
            try { session.invalidate(); } catch (Exception ignored) {}
            ra.addFlashAttribute("error", "Session expired. Please sign in again.");
            return "redirect:/login";
        }
        model.addAttribute("pageTitle", "Account Settings");
        model.addAttribute("user", u);
        return "account-settings";
    }

    // ----- Sign up -----
    @GetMapping("/signup")
    public String signupPage(@RequestParam(value = "error", required = false) String error,
                             Model model) {
        model.addAttribute("pageTitle", "Create an account");
        if (error != null) {
            model.addAttribute("error", "That email is already in use.");
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String email,
                           @RequestParam String password,
                           HttpServletRequest request,
                           RedirectAttributes ra) {
        try {
            // 1) Create enabled ROLE_USER
            User created = userService.createUser(email, password, List.of("ROLE_USER"), true);

            // 2) Authenticate with Spring Security
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(created.getEmail(), password)
            );

            // 3) Persist security context to session (prevents losing it on redirect)
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
            request.getSession(true)
                    .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            // 4) Put light-weight info for your Thymeleaf navbar
            HttpSession session = request.getSession();
            session.setAttribute("uid", created.getId());
            session.setAttribute("email", created.getEmail());

            // 5) Go to articles (Account button will now show)
            return "redirect:/articles";
        } catch (IllegalArgumentException dup) {
            ra.addAttribute("error", "1");
            return "redirect:/signup";
        }
    }

    // We use Spring Security’s POST /logout. No controller method needed.
}

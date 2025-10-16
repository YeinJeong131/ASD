//controller for all logins, signups, article

package betterpedia.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.asd.User;
import org.example.asd.UserRepository;
import org.example.asd.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
//Login
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password.");
        if (logout != null) model.addAttribute("msg", "You’ve been signed out.");
        model.addAttribute("pageTitle", "Login");
        return "login";
    }

  //After login
    @GetMapping("/post-login")
    public String postLogin(Authentication auth, HttpSession session) {
        if (auth != null && auth.isAuthenticated()) {
            User dbUser = userRepository.findByEmail(auth.getName()).orElse(null);
            if (dbUser != null) {
                session.setAttribute("uid", dbUser.getId());
                session.setAttribute("email", dbUser.getEmail());
                boolean isAdmin = auth.getAuthorities().stream()
                        .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
                return "redirect:" + (isAdmin ? "/admin" : "/articles");
            }
        }
        return "redirect:/login?error";
    }

    // Temporary articles page
    @GetMapping("/articles")
    public String articles(Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Articles");
        model.addAttribute("uid", session.getAttribute("uid"));
        model.addAttribute("email", session.getAttribute("email"));
        return "articles";
    }

    //Account
    @GetMapping("/account")
    public String accountPage(Model model, HttpSession session, Authentication auth, RedirectAttributes ra) {
        Object uidObj = session.getAttribute("uid");

        // If the session header vars are gone but user is authenticated, rebuild them
        if (uidObj == null && auth != null && auth.isAuthenticated()) {
            User u = userRepository.findByEmail(auth.getName()).orElse(null);
            if (u != null) {
                session.setAttribute("uid", u.getId());
                session.setAttribute("email", u.getEmail());
                uidObj = u.getId();
            }
        }

        if (uidObj == null) {
            ra.addFlashAttribute("error", "Please sign in to access your account.");
            return "redirect:/login";
        }

        Long uid;
        try {
            uid = (uidObj instanceof Long) ? (Long) uidObj : Long.valueOf(uidObj.toString());
        } catch (NumberFormatException ex) {
            try { session.invalidate(); } catch (Exception ignored) {}
            ra.addFlashAttribute("error", "Session expired. Please sign in again.");
            return "redirect:/login";
        }

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

    // Sign up
    @GetMapping("/signup")
    public String signupPage(@RequestParam(value = "error", required = false) String error,
                             @RequestParam(value = "mismatch", required = false) String mismatch,
                             Model model) {
        model.addAttribute("pageTitle", "Create an account");
        if (mismatch != null) {
            model.addAttribute("error", "Passwords do not match.");
        } else if (error != null) {
            model.addAttribute("error", "That email is already in use.");
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirm,
                           HttpSession session,
                           HttpServletResponse response,
                           RedirectAttributes ra) {

        if (!Objects.equals(password, confirm)) {
            ra.addAttribute("mismatch", "1");
            return "redirect:/signup";
        }

        try {
            User created = userService.createUser(email, password, List.of("ROLE_USER"), true);

            var authorities = created.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority(r.getName()))
                    .toList();
            var authToken = new UsernamePasswordAuthenticationToken(created.getEmail(), null, authorities);
            SecurityContext context = new SecurityContextImpl(authToken);
            SecurityContextHolder.setContext(context);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

            session.setAttribute("uid", created.getId());
            session.setAttribute("email", created.getEmail());

            Cookie c = new Cookie("remember_uid", created.getId().toString());
            c.setPath("/");
            c.setMaxAge(60 * 60 * 24 * 14);
            response.addCookie(c);

            return "redirect:/articles";
        } catch (IllegalArgumentException dup) {
            ra.addAttribute("error", "1");
            return "redirect:/signup";
        }
    }
}

package autoservice.controller;
import autoservice.repository.UserRepository;
import autoservice.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login?logout";
    }
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String login,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String role, Model model) {
        try {
            boolean isLoginExist = userRepository.existsByU01Login(login);

            boolean isEmailExist = userRepository.existsByU01Email(email);

            boolean isPhoneExist = userRepository.existsByU01Phone(phone);

            if (isLoginExist) {
                model.addAttribute("error", "login_exists");

                return "login";
            }

            if (isEmailExist) {
                model.addAttribute("error", "email_exists");
                return "login";
            }

            if (isPhoneExist) {
                model.addAttribute("error", "phone_exists");
                return "login";
            }


            userService.registerUser(name, login, password, email,phone, role);


            return "redirect:/login?success=true";
        } catch (Exception e) {

            return "redirect:/register?error=registration_failed";
        }
    }
}

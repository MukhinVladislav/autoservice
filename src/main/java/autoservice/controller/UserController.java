package autoservice.controller;

import autoservice.entity.*;
import autoservice.repository.RoleRepository;
import autoservice.repository.UserRepository;
import autoservice.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("")
    public String viewAdminPanel(@RequestParam(required = false) String keyword, Model model) {
        List<User> users = userService.getAllUsers(keyword);
        List<Role> roles = roleService.getAllRoles(keyword);

        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, @RequestParam Long roleId) {
        Optional<User> existingUser = userRepository.findById(user.getU01_ID());
        Optional<Role> existingRole = roleRepository.findById(roleId);
        if (existingUser.isPresent() && existingRole.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setU01_NAME(user.getU01_NAME());
            updatedUser.setU01_LOGIN(user.getU01_LOGIN());
            updatedUser.setU01_EMAIL(user.getU01_EMAIL());
            updatedUser.setRole(existingRole.get());
            userRepository.save(updatedUser);
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "admin";
    }
    @GetMapping("/search")
    public String searchUser(Model model, @RequestParam(required = false) String keyword) {
        return getString(model, keyword);
    }

    private String getString(Model model, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = "";
        }

        List<User> user = userService.getAllUser(keyword);
        model.addAttribute("user", user);
        return "index";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        System.out.println("Attempting to delete user with ID: " + id);
        userService.delete(id);
        return "redirect:/admin";
    }
}

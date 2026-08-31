
package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService,
                           RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "allRoles",
                roleService.getAllRoles()
        );

        return "admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {

        model.addAttribute(
                "user",
                new User()
        );

        model.addAttribute(
                "allRoles",
                roleService.getAllRoles()
        );

        return "new";
    }

    @PostMapping
    public String createUser(
            @ModelAttribute("user") User user) {

        userService.saveUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(
            @ModelAttribute("user") User user) {

        userService.updateUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable("id") Long id) {

        userService.deleteUser(id);

        return "redirect:/admin";
    }
}

package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @PatchMapping("/newUser")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping("admin/newUser")
    public String createUser(User user, Role role) {
        role = new Role(user.getId(), "ROLE_USER");
        userService.saveUser(user, role);
        return "redirect:/admin";
    }

    @PatchMapping("updateUser/{id}")
    public String updateUser(Model model, @PathVariable Integer id) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateUser";
    }

    @PatchMapping("updateUser/updateUser/{id}")
    public String updateUser(Long id, @ModelAttribute("user") User user) {
        userService.updateUser(Math.toIntExact(id), user);
        return "redirect:/admin";
    }

    @DeleteMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


}

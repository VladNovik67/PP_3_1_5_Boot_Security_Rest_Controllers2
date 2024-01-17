package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersServiceImp usersServiceImp;
    private final RoleServiceImp roleServiceImp;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminController(UsersServiceImp usersServiceImp, RoleServiceImp roleServiceImp, PasswordEncoder passwordEncoder) {
        this.usersServiceImp = usersServiceImp;
        this.roleServiceImp = roleServiceImp;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String show(@RequestParam(value = "id", required = false) long id, Model model) {
        model.addAttribute("getUserId", usersServiceImp.findUserById(id));
        return "admin/show";
    }

    @GetMapping()
    public String showAll(ModelMap model) {
        model.addAttribute("userss", usersServiceImp.getAllUsers());
        return "admin/showAll";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleServiceImp.findAllRoles());
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", usersServiceImp.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        usersServiceImp.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        usersServiceImp.deleteUser(usersServiceImp.findUserById(id));
        return "redirect:/admin";
    }
}

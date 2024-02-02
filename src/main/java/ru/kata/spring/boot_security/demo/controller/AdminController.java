package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersServiceImp usersServiceImp;
    private final RoleServiceImp roleServiceImp;


    @Autowired
    public AdminController(UsersServiceImp usersServiceImp, RoleServiceImp roleServiceImp) {
        this.usersServiceImp = usersServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    //    @GetMapping("/")
//    public String show(@RequestParam(value = "id", required = false) long id, Model model) {
//        model.addAttribute("getUserId", usersServiceImp.findUserById(id));
//        return "admin/show";
//    }
    @GetMapping()
    public String showAll(ModelMap model, Principal principal) {
        model.addAttribute("userss", usersServiceImp.getAllUsers());
        model.addAttribute("loginUser", usersServiceImp.findByUsername(principal.getName()));
        model.addAttribute("roles", roleServiceImp.findAllRoles());
        model.addAttribute("userNew", new User());
        return "admin/showAll";
    }


    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        usersServiceImp.saveUser(user);
        return "redirect:/admin";
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

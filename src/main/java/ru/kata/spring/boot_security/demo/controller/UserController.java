package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UsersServiceImp usersServiceImp;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UsersServiceImp usersServiceImp, RoleRepository roleRepository) {
        this.usersServiceImp = usersServiceImp;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String show(Model model, Principal principal) {
        model.addAttribute("getUserId", usersServiceImp.ffindByUserName(principal.getName()));
        return "user/show";
    }






//
//    @GetMapping()
//    public String showAll(ModelMap model) {
//        model.addAttribute("userss", userService.getAllUsers());
//        return "users/showAll";
//    }
//
//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "users/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") User user, Model model) {
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("user", userService.findUserById(id));
//        return "users/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
//        userService.updateUser(user);
//        return "redirect:/users";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userService.deleteUser(userService.findUserById(id));
//        return "redirect:/users";
//    }

}

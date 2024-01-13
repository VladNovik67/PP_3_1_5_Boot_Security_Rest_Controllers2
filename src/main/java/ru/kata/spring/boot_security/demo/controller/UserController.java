package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
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


}

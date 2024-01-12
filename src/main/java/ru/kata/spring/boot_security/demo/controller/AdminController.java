package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UsersServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    final UsersServiceImp usersServiceImp;

    @Autowired
    public AdminController(UsersServiceImp usersServiceImp) {
        this.usersServiceImp = usersServiceImp;
    }

    @GetMapping("/")
    public String pageForAdmin(Principal principal) {
        return "/";
    }

    @GetMapping("/read_profile")
    public String pageReadProfile(Principal principal) {
        return "Profile is " + principal.getName();
    }

    @GetMapping("/authenticated")
    public String pageForAuthenticatedUsers(Principal principal) {
        User user = usersServiceImp.ffindByUserName(principal.getName());
        return "Secured page for web service "+ user.getUsername()+ " " +user.getPassword();
    }
}

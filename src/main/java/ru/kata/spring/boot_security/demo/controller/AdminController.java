package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping("/")
    public String pageForAdmin(Principal principal) {
        return "Admin name is: " + principal.getName();
    }

    @GetMapping("/read_profile")
    public String pagereadProfile(Principal principal) {
        return "Profile is " + principal.getName();
    }
}

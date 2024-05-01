package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

    @GetMapping("/user")
    public String show1() {
        return "user/show";
    }

    @GetMapping("/admin")
    public String show2() {
        return "admin/showAll";
    }

}
